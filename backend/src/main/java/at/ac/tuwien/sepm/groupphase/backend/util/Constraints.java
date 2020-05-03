package at.ac.tuwien.sepm.groupphase.backend.util;


import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class Constraints {
    List<String> violatedConstraints;

    public Constraints() {
        this.violatedConstraints = new ArrayList<>();
    }

    public void add(String constraintName, boolean constraintValue) {
        if(!constraintValue) {
            violatedConstraints.add(constraintName);
        }
    }

    public void addViolated(String constraintName) {
        violatedConstraints.add(constraintName);
    }

    public void addViolated(Constraints constraints) {
        for(String c: constraints.getViolated()) {
            this.addViolated(c);
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