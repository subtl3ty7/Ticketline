package at.ac.tuwien.sepm.groupphase.backend.util.Validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.Validation.UserValidator;
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

    public Constraints validateRegistration(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.add(validate(user));
        constraints.add("isLogged_false", !user.isLogged());
        if(user instanceof Customer) {
            constraints.add("isBlocked_false", !((Customer) user).isBlocked());
            constraints.add("points_zero", ((Customer) user).getPoints() == 0);
        }
        if(user.getBirthday() != null) {
            constraints.add("birthday_16yo", ChronoUnit.YEARS.between(user.getBirthday(), LocalDateTime.now()) > 16);
        }
        constraints.add(validatePasswordEncoded(user.getPassword()));
        return constraints;
    }

    public Constraints validate(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.add(validateUnique(user));
        constraints.add(AccesoryValidator.validateJavaxConstraints(user));
        return constraints;
    }

    public Constraints validateUserCode(String userCode) {
        Constraints constraints = new Constraints();
        constraints.add("userCode_unique", userRepository.findAbstractUserByUserCode(userCode) == null);
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
}
