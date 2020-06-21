package at.ac.tuwien.sepm.groupphase.backend.util.validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

/**
 *  Implements all validation methods for validating all requests in news service before they are sent to the database.
 */
public interface NewsValidator {
    Constraints validateNewsCode(String newsCode);

    Constraints validate(News news);

    Constraints validateUser(AbstractUser user);
}
