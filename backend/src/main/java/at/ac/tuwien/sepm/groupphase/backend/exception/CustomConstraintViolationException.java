package at.ac.tuwien.sepm.groupphase.backend.exception;

import java.util.List;

public class CustomConstraintViolationException extends BaseException {

    private List<String> violatedConstraints;

    public CustomConstraintViolationException(List<String> violatedConstraints, String message) {
        super(message, null);
        this.violatedConstraints = violatedConstraints;
    }

    public List<String> getViolatedConstraints() {
        return violatedConstraints;
    }
}
