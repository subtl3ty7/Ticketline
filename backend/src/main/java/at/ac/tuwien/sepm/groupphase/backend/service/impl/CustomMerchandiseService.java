package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.MerchandiseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.MerchandiseService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.MerchandiseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomMerchandiseService implements MerchandiseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandiseValidator merchandiseValidator;
    private final UserRepository userRepository;

    @Autowired
    public CustomMerchandiseService(MerchandiseRepository merchandiseRepository, MerchandiseValidator merchandiseValidator, UserRepository userRepository) {
        this.merchandiseRepository = merchandiseRepository;
        this.merchandiseValidator = merchandiseValidator;
        this.userRepository = userRepository;
    }


    private String getNewMerchandiseProductCode() {
        final int maxAttempts = 1000;
        String merchandiseProductCode = "";
        int i;
        for (i = 0; i < maxAttempts; i++) {
            merchandiseProductCode = CodeGenerator.generateMerchandiseProductCode();
        }

        return merchandiseProductCode;
    }

    @Override
    public Merchandise createNewProduct(Merchandise product) {
        LOGGER.debug("Moving Merchandise Entity through Service Layer " + product);
        product.setMerchandiseProductCode(getNewMerchandiseProductCode());

        return merchandiseRepository.save(product);
    }


    @Override
    public List<Merchandise> findAllMerchandiseProducts() {
        List<Merchandise> allMerchandiseProducts = merchandiseRepository.findAll();
        return allMerchandiseProducts;
    }

    @Override
    public List<Merchandise> findAllMerchandisePremiumProducts() {
        List<Merchandise> allPremiumProducts = merchandiseRepository.findAllByPremiumIsTrue();
        return allPremiumProducts;
    }

    @Override
    public Merchandise purchaseWithPremiumPoints(Merchandise merchandise, String userCode) {
        LOGGER.info("Validating merchandise " + merchandise);

        merchandiseValidator.validateMerchandisePurchaseWithPremiumPoints(merchandise, userCode);
        merchandise.setStockCount(merchandise.getStockCount() - 1);

        AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
        long currentPoints = ((Customer) user).getPoints();
        ((Customer) user).setPoints(currentPoints - merchandise.getPremiumPrice());

        merchandiseRepository.save(merchandise);
        userRepository.save(user);

        return merchandise;
    }
}
