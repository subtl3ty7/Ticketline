package at.ac.tuwien.sepm.groupphase.backend.util.Validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface EventValidator {
    Constraints validateEventCode(String eventCode);
    Constraints validate(EventLocation eventLocation);
    Constraints validate(Event event);
}
