package at.ac.tuwien.sepm.groupphase.backend.util.Validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

public interface UserValidator {
    Constraints validateRegistration(AbstractUser user);
    Constraints validateUserCode(String userCode);
    Constraints validate(AbstractUser user);
}
