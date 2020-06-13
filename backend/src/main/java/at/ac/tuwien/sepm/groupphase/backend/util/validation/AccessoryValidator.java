package at.ac.tuwien.sepm.groupphase.backend.util.validation;

import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

public interface AccessoryValidator {
    Constraints validateJavaxConstraints(Object object);
}
