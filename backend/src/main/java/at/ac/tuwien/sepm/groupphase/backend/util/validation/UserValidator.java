package at.ac.tuwien.sepm.groupphase.backend.util.validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

public interface UserValidator {
    Constraints validateRegistration(AbstractUser user);
    Constraints validateDelete(String userCode);
    Constraints validateUpdate(Customer customer);
    Constraints validateEmail(String email);
    Constraints validateUserCode(String userCode);
    Constraints validateBlock(String userCode);
    Constraints validateUnblock(String userCode);
    boolean validateUserIdentityWithGivenEmail(String email);
    Constraints validateChangePasswordCustomer(String email, String newPassword);
    Constraints validateUserIdentityWithGivenUserCode(String userCode);

}
