package at.ac.tuwien.sepm.groupphase.backend.util;


import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Constraints {
    List<String> violatedConstraints;

    public Constraints() {
        this.violatedConstraints = new ArrayList<>();
    }

    /**
     * Add a constraint
     * @param constraintName is the name that this constraint should have
     * @param constraintValue is the boolean value of the constraint. If it's false, the constraint counts as violated, otherwise the constraint counts as successfully validated
     */
    public void add(String constraintName, boolean constraintValue) {
        if(!constraintValue) {
            violatedConstraints.add(constraintName);
        }
    }

    public void add(Constraints constraints) {
        for(String c: constraints.getViolated()) {
            violatedConstraints.add(c);
        }
    }

    public void add(Set<ConstraintViolation<Object>> violations) {
        for(ConstraintViolation<Object> v: violations) {
            violatedConstraints.add(v.getPropertyPath().toString());
        }
    }

    public List<String> getViolated() {
        return violatedConstraints;
    }

    public void throwIfViolated() throws ValidationException {
        if(!violatedConstraints.isEmpty()) {
            throw new ValidationException(this);
        }
    }

    public boolean isViolated() {
        return !this.violatedConstraints.isEmpty();
    }
}