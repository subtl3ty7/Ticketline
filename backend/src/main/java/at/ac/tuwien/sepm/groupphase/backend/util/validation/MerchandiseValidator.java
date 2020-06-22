package at.ac.tuwien.sepm.groupphase.backend.util.validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

/**
 *  Implements all validation methods for validating all requests in merchandise service before they are sent to the database.
 */
public interface MerchandiseValidator {
    Constraints validateMerchandisePurchaseWithPremiumPoints(Merchandise merchandise, String userCode);
    Constraints validateMerchandisePurchaseWithMoney(Merchandise merchandise, String userCode);
}
