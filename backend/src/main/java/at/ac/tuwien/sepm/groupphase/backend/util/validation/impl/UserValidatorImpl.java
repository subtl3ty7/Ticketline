package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.AccessoryValidator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class UserValidatorImpl implements UserValidator {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    AccessoryValidator accessoryValidator;

    public UserValidatorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Constraints validateRegistration(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.add("user_NotNull", user != null);
        if(!constraints.isViolated()) {
            constraints.add(accessoryValidator.validateJavaxConstraints(user));
            constraints.add("userCode_unique", userRepository.findAbstractUserByUserCode(user.getUserCode()) == null);
            constraints.add("isLogged_false", !user.isLogged());
            constraints.add(validateRegistrationEmail(user.getEmail()));
            constraints.add(validatePasswordEncoded(user.getPassword()));
            if (user instanceof Customer) {
                constraints.add("isBlocked_false", !((Customer) user).isBlocked());
                constraints.add("points_zero", ((Customer) user).getPoints() == 0);
            }
            if (user.getBirthday() != null) {
                constraints.add("birthday_16yo", ChronoUnit.YEARS.between(user.getBirthday(), LocalDateTime.now()) >= 16);

            }
            if(constraints.isViolated()) {
                int debug=0;
            }
        }
        return constraints;
    }

    private Constraints validateRegistrationEmail(String email) {
        Pattern eMailPattern = Pattern.compile("[a-zA-Z0-9!#$%&'*+/=?^_‘{|}~\\-:.]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+");
        Constraints constraints = new Constraints();
        constraints.add("email_notNull", email != null);
        if(!constraints.isViolated()) {
            constraints.add("email_valid", eMailPattern.matcher(email).matches());
            constraints.add("email_unique", userRepository.findAbstractUserByEmail(email) == null && userRepository.findSoftDeletedAbstractUserByEmail(email) == null);
        }
        return constraints;
    }

    @Override
    public Constraints validateEmail(String email) {
        Constraints constraints = new Constraints();
        constraints.add("email_exists", userRepository.findAbstractUserByEmail(email) != null);
        return constraints;
    }

    @Override
    public Constraints validateUserCode(String userCode) {
        Constraints constraints = new Constraints();
        constraints.add("userCode_unique", userRepository.findAbstractUserByUserCode(userCode) == null && userRepository.findSoftDeletedAbstractUserByUserCode(userCode) == null);
        return constraints;
    }

    @Override
    public Constraints validateDelete(String userCode) {
        Constraints constraints = new Constraints();
        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
        constraints.add("user_exists", user != null);
        if (user != null) {
            //constraints.add("user_isSelf", validateUserIdentityWithGivenEmail(user.getEmail()));
            constraints.add("user_notAdmin", !(user instanceof Administrator));
            //constraints.add("user_isLogged", user.isLogged());
        }
        return constraints;
    }

    @Override
    public Constraints validateUpdate(Customer customer) {
        Constraints constraints = new Constraints();
        constraints.add(accessoryValidator.validateJavaxConstraints(customer));
        AbstractUser userFromDataBase = userRepository.findAbstractUserByUserCode(customer.getUserCode());
        constraints.add("user_exists", userFromDataBase != null);
        constraints.add("user_isLogged", userFromDataBase != null && userFromDataBase.isLogged());
        constraints.add("user_isSelf", userFromDataBase != null && validateUserIdentityWithGivenEmail(userFromDataBase.getEmail()));
        return constraints;
    }

    @Override
    public Constraints validateBlock(String userCode) {
        Constraints constraints = new Constraints();
        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
        constraints.add("user_isCustomer", user instanceof Customer);
        constraints.add("user_isUnblocked", user instanceof Customer && !((Customer) user).isBlocked());
        return constraints;
    }

    @Override
    public Constraints validateUnblock(String userCode) {
        Constraints constraints = new Constraints();
        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
        constraints.add("user_isCustomer", user instanceof Customer);
        constraints.add("user_isBlocked", user instanceof Customer && ((Customer) user).isBlocked());
        return constraints;
    }

    private Constraints validatePasswordEncoded(String password) {
        Pattern bCryptPattern = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        Constraints constraints = new Constraints();
        constraints.add("password_encoded", bCryptPattern.matcher(password).matches());
        return constraints;
    }

    @Override
    public Constraints validateChangePasswordCustomer(String email, String newPassword) {
        Constraints constraints = new Constraints();
        AbstractUser user = userRepository.findAbstractUserByEmail(email);
        constraints.add("user_exists", user != null);
        constraints.add("user_isCustomer", user instanceof Customer);
        constraints.add("user_isSelf_or_auth_isAdmin", (user != null && validateUserIdentityWithGivenEmail(user.getEmail())) || validateIdentityIsAdmin());
        constraints.add(validatePasswordEncoded(newPassword));
        return constraints;
    }

    @Override
    public Constraints validateUserIdentityWithGivenUserCode(String userCode) {
        Constraints constraints = new Constraints();
        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
        constraints.add("user_isSelf", this.validateUserIdentityWithGivenEmail(user.getEmail()));
        return constraints;
    }

    @Override
    public boolean validateUserIdentityWithGivenEmail(String email) {
        String emailOfAuthenticatedUser = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal != null) {
            if (principal instanceof User) {
                User user = (User) principal;
                emailOfAuthenticatedUser = user.getUsername();
            }
            if (principal instanceof String) {
                emailOfAuthenticatedUser = (String) principal;
            }
        }
        return email.compareTo(emailOfAuthenticatedUser) == 0;
    }

    private boolean validateIdentityIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority authority : grantedAuthorities) {
            if (authority.getAuthority().compareTo("ROLE_ADMIN") == 0) {
                return true;
            }
        }
        return false;
    }
}
