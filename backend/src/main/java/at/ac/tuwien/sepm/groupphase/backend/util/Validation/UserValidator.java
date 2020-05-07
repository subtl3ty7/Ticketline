package at.ac.tuwien.sepm.groupphase.backend.util.Validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

public interface UserValidator {
    Constraints validateRegistration(AbstractUser user);
    Constraints validateDelete(String userCode);
    Constraints validateUpdate(Customer customer);
    Constraints validateUserCode(String userCode);
    Constraints validateBlock(String userCode);
    Constraints validateUnblock(String userCode);
    Constraints validate(AbstractUser user);
    boolean validateUserIdentityWithGivenEmail(String email);

}
