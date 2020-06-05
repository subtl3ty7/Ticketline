package at.ac.tuwien.sepm.groupphase.backend.util.validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

public interface MerchandiseValidator {
    Constraints validateMerchandisePurchaseWithPremiumPoints(Merchandise merchandise, String userCode);
    Constraints validateMerchandisePurchaseWithMoney(Merchandise merchandise);
}
