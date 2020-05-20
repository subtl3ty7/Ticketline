package at.ac.tuwien.sepm.groupphase.backend.util.validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

public interface EventValidator {
    Constraints validateEventCode(String eventCode);
    Constraints validate(EventLocation eventLocation);
    Constraints validateExists(String eventCode);
    Constraints validate(Event event);
}
