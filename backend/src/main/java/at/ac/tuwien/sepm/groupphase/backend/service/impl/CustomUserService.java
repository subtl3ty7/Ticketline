package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserAttempts;
import at.ac.tuwien.sepm.groupphase.backend.exception.CustomServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.Validation.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final UserAttemptsRepository userAttemptsRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;
    private final EntityManagerFactory entityManagerFactory;


    @Autowired
    public CustomUserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                             UserAttemptsRepository userAttemptsRepository, Validator validator, EntityManagerFactory entityManagerFactory) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAttemptsRepository = userAttemptsRepository;
        this.validator = validator;
        this.entityManagerFactory = entityManagerFactory;
    }

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Load all user by email");
        try {
            AbstractUser user = findUserByEmail(email);
            List<GrantedAuthority> grantedAuthorities;
            if (user instanceof Administrator) {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
                return new User(user.getEmail(),  user.getPassword(), grantedAuthorities);
            } else {
                //If user is a basic user
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
                //check for login attempts
                UserAttempts userAttempts = userAttemptsRepository.findUserAttemptsByEmail(email);

                if (userAttempts.getAttempts() == 5) {
                    ((Customer) user).setBlocked(true);
                    userRepository.save(user);
                }
                return new User(user.getEmail(), user.getPassword(), true, true, true, !((Customer) user).isBlocked(), grantedAuthorities);
            }
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public AbstractUser findUserByEmail(String email) {
        LOGGER.debug("Find application user by email");
        AbstractUser user = userRepository.findAbstractUserByEmail(email);
        if (user != null) return user;
        throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
    }

    @Override
    public String unblockUser(String userCode) {
        LOGGER.debug("Unblocking user with user code " + userCode);
        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);

        if (user instanceof Customer) {
            try {
                if (((Customer) user).isBlocked()) {
                    ((Customer) user).setBlocked(false);
                    userRepository.save(user);
                }
            } catch (CustomServiceException e) {
                LOGGER.trace("Error while unblocking user " + user.getEmail());
                throw new CustomServiceException("Error while unblocking user " + user.getEmail());
            }
        }
        return "";
    }
    @Override
    public Customer registerNewCustomer(Customer customer) throws ValidationException, DataAccessException {
        LOGGER.info("Validating Customer Entity: " + customer);
        customer.setUserCode(getNewUserCode());
        LocalDateTime now = LocalDateTime.now();
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        validator.validateRegistration(customer).throwIfViolated();

        UserAttempts userAttempts = new UserAttempts(customer);

        Session session = getSession();
        session.beginTransaction();
        customer = userRepository.save(customer);
        userAttempts = userAttemptsRepository.save(userAttempts);
        session.getTransaction().commit();

        LOGGER.info("Saved Customer Entity in Database: " + customer);
        LOGGER.info("Saved UserAttempts Entity in Database: " + userAttempts);
        return customer;
    }

    private String getNewUserCode() {
        final int maxAttempts = 1000;
        String userCode = "";
        int i;
        for(i=0; i<maxAttempts; i++) {
            userCode = CodeGenerator.generateUserCode();
            if(!validator.validateUserCode(userCode).isViolated()) {
                break;
            }
        }
        if(i==1000) {
            throw new ServiceException("Something went wrong while generating UserCode", null);
        }
        return userCode;
    }

    @Override
    public List<AbstractUser> loadAllUsers(){
        return userRepository.findAll();
    }
}
