package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ResetPasswordRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EmailService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.UserValidator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class CustomUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final UserAttemptsRepository userAttemptsRepository;
    private final UserValidator validator;
    private final EntityManagerFactory entityManagerFactory;


    @Autowired
    public CustomUserService(UserRepository userRepository,
                             UserAttemptsRepository userAttemptsRepository,
                             UserValidator validator,
                             EntityManagerFactory entityManagerFactory) {

        this.userRepository = userRepository;
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
                //If user is a Customer
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
    public AbstractUser findUserByEmail(String email) throws DataAccessException{
        LOGGER.debug("Find application user by email");
        AbstractUser user = userRepository.findAbstractUserByEmail(email);
        if (user != null) return user;
        throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
    }

    @Override
    public AbstractUser findUserByUserCode(String userCode) throws DataAccessException {
        LOGGER.debug("Find application user by usercode");
        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
        if (user != null) return user;
        throw new NotFoundException(String.format("Could not find the user with the user code %s", userCode));
    }

    @Override
    public String unblockUser(String userCode) throws ValidationException, DataAccessException {
        LOGGER.debug("Unblocking user with user code " + userCode);
        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
        validator.validateUnblock(userCode).throwIfViolated();
        ((Customer) user).setBlocked(false);
        UserAttempts userAttempts = userAttemptsRepository.findUserAttemptsByEmail(user.getEmail());
        userAttempts.setAttempts(0);
        Session session = getSession();
        session.beginTransaction();
        userRepository.save(user);
        userAttemptsRepository.save(userAttempts);
        session.getTransaction().commit();
        return "Successfully unblocked user.";
    }

    @Override
    public String blockCustomer(String userCode) throws ValidationException, DataAccessException {
        LOGGER.debug("Blocking customer with user code " + userCode );
        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
        validator.validateBlock(userCode).throwIfViolated();

        ((Customer) user).setBlocked(true);
        userRepository.save(user);
        return "Successfully blocked user.";
    }
    @Override
    @Transactional
    public Customer registerNewCustomer(Customer customer) throws ValidationException, DataAccessException {
        LOGGER.debug("Validating Customer Entity: " + customer);
        customer.setUserCode(getNewUserCode());
        LocalDateTime now = LocalDateTime.now();
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        validator.validateRegistration(customer).throwIfViolated();

        UserAttempts userAttempts = new UserAttempts(customer);

        customer = userRepository.save(customer);
        userAttempts = userAttemptsRepository.save(userAttempts);

        LOGGER.debug("Saved Customer Entity in Database: " + customer);
        LOGGER.debug("Saved UserAttempts Entity in Database: " + userAttempts);
        return customer;
    }
    @Override
    public Administrator registerNewAdmin(Administrator admin) throws ValidationException, DataAccessException {
        LOGGER.debug("Validating Admin Entity: " + admin);
        admin.setUserCode(getNewUserCode());
        LocalDateTime now = LocalDateTime.now();
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        validator.validateRegistration(admin).throwIfViolated();

        admin = userRepository.save(admin);

        LOGGER.debug("Saved Admin Entity in Database: " + admin);
        return admin;
    }

    private String getNewUserCode() throws ValidationException {
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
    public List<AbstractUser> loadAllUsers() throws DataAccessException{
        return userRepository.findAll();
    }

    @Override
    public void deleteUserByUsercode(String userCode) throws ValidationException, DataAccessException{
        LOGGER.debug("Deleting Customer Entity in Service Layer");
        validator.validateDelete(userCode).throwIfViolated();
        AbstractUser user = findUserByUserCode(userCode);
        UserAttempts userAttempts = userAttemptsRepository.findUserAttemptsByEmail(user.getEmail());
        userRepository.delete(user);
        userAttemptsRepository.delete(userAttempts);
    }

    @Override
    public AbstractUser updateCustomer(Customer customer) throws ValidationException, DataAccessException{
        LOGGER.debug("Updating customer with the usercode " + customer.getUserCode());
        validator.validateUpdate(customer).throwIfViolated();
        AbstractUser userFromDatabase = userRepository.findAbstractUserByUserCode(customer.getUserCode());

        LocalDateTime now = LocalDateTime.now();
        userFromDatabase.setUpdatedAt(now);
        userFromDatabase.setBirthday(customer.getBirthday());
        userFromDatabase.setFirstName(customer.getFirstName());
        userFromDatabase.setLastName(customer.getLastName());
        userFromDatabase.setEmail(customer.getEmail());

        return userRepository.save(userFromDatabase);
    }

    @Override
    public AbstractUser getAuthenticatedUser(Authentication auth) {
        LOGGER.info("Getting authenticated user information");
        String email = "";
        Object principal = auth.getPrincipal();
        if (principal != null) {
            if (principal instanceof User) {
                User user = (User) principal;
                email = user.getUsername();
            }
            if (principal instanceof String) {
                email = (String) principal;
            }
            LOGGER.info("Getting user information for " + email);
            return findUserByEmail(email);
        }
        throw new NotFoundException("Authenticated user not found");
    }

    @Override
    public void changePasswordCustomer(String email, String newPassword) throws ValidationException, DataAccessException{
        LOGGER.info("Changing password of user " + email);
        validator.validateChangePasswordCustomer(email, newPassword).throwIfViolated();
        AbstractUser user = userRepository.findAbstractUserByEmail(email);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public List<AbstractUser> findUserByParams(String userCode, String firstName, String lastName, String email) {
        List<AbstractUser> users =  userRepository.findAllByUserCodeContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCase(userCode, firstName, lastName, email);
        return users;
    }
}
