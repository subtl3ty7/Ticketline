package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.CustomConstraintViolationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ServiceValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;

    public Constraints validateRegistration(Customer customer) {
        Constraints constraints = new Constraints();
        constraints.addViolated(validate(customer));
        constraints.add("isLogged_false", customer.isLogged()==false);
        return constraints;
    }

    public Constraints validate(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.addViolated(validateUnique(user));
        constraints.addViolated(validateJavaxConstraints(user));
        return constraints;
    }

    public Constraints validateUserCode(String userCode) {
        Constraints constraints = new Constraints();
        constraints.add("userCode_unique", userRepository.findAbstractUserByUserCode(userCode) == null);
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


    private Constraints validateJavaxConstraints(Object object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        Constraints constraints = new Constraints();
        for(ConstraintViolation<Object> v: violations) {
            constraints.addViolated(v.getPropertyPath().toString());
        }

        return constraints;
    }
}
