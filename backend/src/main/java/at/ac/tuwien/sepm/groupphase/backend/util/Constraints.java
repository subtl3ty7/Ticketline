package at.ac.tuwien.sepm.groupphase.backend.util;


import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;

import javax.validation.ConstraintViolation;
import java.util.*;

public class Constraints {
    //maps an added violated constraint to the amount of times it was added
    Map<String, Integer> violatedConstraints;

    public Constraints() {
        this.violatedConstraints = new HashMap<>();
    }

    private void add(String constraintName, Integer numberOfTimesUsed) {
        if(this.violatedConstraints.get(constraintName) != null) {
            numberOfTimesUsed += this.violatedConstraints.get(constraintName);
        }

        this.violatedConstraints.put(constraintName, numberOfTimesUsed);
    }

    /**
     * Add a constraint
     * @param constraintName is the name that this constraint should have
     * @param constraintValue is the boolean value of the constraint. If it's false, the constraint counts as violated, otherwise the constraint counts as successfully validated
     */
    public void add(String constraintName, boolean constraintValue) {
        if(!constraintValue) {
            add(constraintName, 1);
        }
    }

    public void add(Constraints constraints) {
        for(Map.Entry<String, Integer> entry: constraints.getViolated().entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    public void add(Set<ConstraintViolation<Object>> violations) {
        for(ConstraintViolation<Object> violation: violations) {
            add(violation.getPropertyPath().toString(), 1);
        }
    }

    public Map<String, Integer> getViolated() {
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

    /**
     * Adds a prefix to the names of all constraints
     * @param namePrefix is the prefix to add
     */
    public void addNamePrefix(String namePrefix) {
        Map<String, Integer> newMap = new HashMap<>();
        for(Map.Entry<String, Integer> entry: violatedConstraints.entrySet()) {
            newMap.put(namePrefix + entry.getKey(), entry.getValue());
        }
        violatedConstraints = newMap;
    }
}