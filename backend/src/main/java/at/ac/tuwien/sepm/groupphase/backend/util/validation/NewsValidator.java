package at.ac.tuwien.sepm.groupphase.backend.util.validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

public interface NewsValidator {
    Constraints validateNewsCode(String newsCode);
}
