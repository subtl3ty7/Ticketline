package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.UserValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

@Component
public class UserValidatorImpl implements UserValidator {

    private final UserRepository userRepository;

    public UserValidatorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Constraints validateRegistration(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.add(validate(user));
        constraints.add("isLogged_false", !user.isLogged());
        if (user instanceof Customer) {
            constraints.add("isBlocked_false", !((Customer) user).isBlocked());
            constraints.add("points_zero", ((Customer) user).getPoints() == 0);
        }
        if (user.getBirthday() != null) {
            constraints.add("birthday_16yo", ChronoUnit.YEARS.between(user.getBirthday(), LocalDateTime.now()) > 16);
        }
        constraints.add(validatePasswordEncoded(user.getPassword()));
        return constraints;
    }

    @Override
    public Constraints validate(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.add(validateUnique(user));
        constraints.add(AccesoryValidator.validateJavaxConstraints(user));
        return constraints;
    }

    @Override
    public Constraints validateUserCode(String userCode) {
        Constraints constraints = new Constraints();
        constraints.add("userCode_unique", userRepository.findAbstractUserByUserCode(userCode) == null);
        return constraints;
    }

    @Override
    public Constraints validateDelete(String userCode) {
        Constraints constraints = new Constraints();
        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
        constraints.add("user_exists", user != null);
        if (user != null) {
            constraints.add("user_isSelf", validateUserIdentityWithGivenEmail(user.getEmail()));
            constraints.add("user_notAdmin", !(user instanceof Administrator));
            constraints.add("user_isLogged", user.isLogged());
        }
        return constraints;
    }

    @Override
    public Constraints validateUpdate(Customer customer) {
        Constraints constraints = new Constraints();
        constraints.add(AccesoryValidator.validateJavaxConstraints(customer));
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

    private Constraints validateUnique(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.add("userCode_unique", userRepository.findAbstractUserByUserCode(user.getUserCode()) == null);
        constraints.add("email_unique", userRepository.findAbstractUserByEmail(user.getEmail()) == null);
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
}
