package at.ac.tuwien.sepm.groupphase.backend.util.Validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.Validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class ValidatorImpl implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ValidatorImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
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

    private Constraints validate(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.add(validateUnique(user));
        constraints.add(validateJavaxConstraints(user));
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

    public Constraints validateEventCode(String eventCode) {
        Constraints constraints = new Constraints();
        constraints.add("eventCode_unique", eventRepository.findEventByEventCode(eventCode) == null);
        return constraints;
    }

    private Constraints validateUnique(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.add("userCode_unique", userRepository.findAbstractUserByUserCode(user.getUserCode()) == null);
        constraints.add("email_unique", userRepository.findAbstractUserByEmail(user.getEmail()) == null);
        return constraints;
    }


    private static Constraints validateJavaxConstraints(Object object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        Constraints constraints = new Constraints();
        constraints.add(violations);

        return constraints;
    }
}
