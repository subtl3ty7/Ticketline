package at.ac.tuwien.sepm.groupphase.backend.util.Validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.Validation.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.*;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

public class AccesoryValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static Constraints validateJavaxConstraints(Object object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        Constraints constraints = new Constraints();
        constraints.add(violations);

        return constraints;
    }
}
