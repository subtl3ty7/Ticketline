package at.ac.tuwien.sepm.groupphase.backend.util.Validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface Validator {
    Constraints validateRegistration(AbstractUser user);
    Constraints validateUserCode(String userCode);
    Constraints validateEventCode(String eventCode);
}
