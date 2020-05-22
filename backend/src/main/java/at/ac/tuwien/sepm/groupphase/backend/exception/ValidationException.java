package at.ac.tuwien.sepm.groupphase.backend.exception;

import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.ValidationMessage;

public class ValidationException extends BaseException {

    private ValidationMessage validationMessage;

    public ValidationException(Constraints constraints) {
        super("Validation Exception thrown: " + (new ValidationMessage(constraints)).getConstraintMessages(), null);
        this.validationMessage = new ValidationMessage(constraints);
    }

    public ValidationMessage getValidationMessage() {
        return validationMessage;
    }
}
