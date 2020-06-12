package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.AccessoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.lang.invoke.MethodHandles;
import java.util.Set;

@Component
public class AccesoryValidator implements AccessoryValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Validator validator;

    @Autowired
    public AccesoryValidator(Validator validator) {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public Constraints validateJavaxConstraints(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        Constraints constraints = new Constraints();
        constraints.add(violations);
        constraints.addNamePrefix(object.getClass().getSimpleName().toLowerCase() + "_");

        return constraints;
    }
}
