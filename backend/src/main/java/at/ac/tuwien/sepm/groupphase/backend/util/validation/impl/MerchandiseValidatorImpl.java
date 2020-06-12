package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.repository.MerchandiseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.MerchandiseService;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.MerchandiseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MerchandiseValidatorImpl implements MerchandiseValidator {

    private final MerchandiseRepository merchandiseRepository;
    private final UserRepository userRepository;

    @Autowired
    public MerchandiseValidatorImpl(MerchandiseRepository merchandiseRepository, UserRepository userRepository){
        this.merchandiseRepository = merchandiseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Constraints validateMerchandisePurchaseWithPremiumPoints(Merchandise merchandise, String userCode) {
        Constraints constraints = new Constraints();
        constraints.add("merchandise_exists", merchandiseRepository.findMerchandiseById(merchandise.getId()) != null);
        constraints.add("user_exists", userRepository.findAbstractUserByUserCode(userCode) != null);
        if(!constraints.isViolated()) {
            Merchandise merchandise1 = merchandiseRepository.findMerchandiseById(merchandise.getId());
            AbstractUser user1 = userRepository.findAbstractUserByUserCode(userCode);
            constraints.add("merchandise_premium", merchandise1.isPremium());
            constraints.add("merchandise_outOfStock", merchandise1.getStockCount() > 0);
            constraints.add("user_noPoints", ((Customer) user1).getPoints() >= merchandise1.getPremiumPrice());
        }
        return constraints;
    }

    @Override
    public Constraints validateMerchandisePurchaseWithMoney(Merchandise merchandise, String userCode) {
        Constraints constraints = new Constraints();
        constraints.add("merchandise_exists", merchandiseRepository.findMerchandiseById(merchandise.getId()) != null);
        constraints.add("user_exists", userRepository.findAbstractUserByUserCode(userCode) != null);
        if(!constraints.isViolated()) {
            Merchandise merchandise1 = merchandiseRepository.findMerchandiseById(merchandise.getId());
            AbstractUser user1 = userRepository.findAbstractUserByUserCode(userCode);
            constraints.add("merchandise_outOfStock", merchandise1.getStockCount() > 0);
        }
        return constraints;
    }
}
