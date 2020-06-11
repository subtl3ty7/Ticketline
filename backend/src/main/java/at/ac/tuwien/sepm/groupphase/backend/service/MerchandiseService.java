package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface MerchandiseService {

    Merchandise createNewProduct(Merchandise product);

    List<Merchandise> findAllMerchandiseProducts();

    List<Merchandise> findAllMerchandisePremiumProducts();

    Merchandise findMerchandiseByMerchandiseProductCode(String merchandiseProductCode);

    Merchandise purchaseWithPremiumPoints(Merchandise merchandise, String userCode);

    Merchandise purchaseWithMoney(Merchandise merchandise, String userCode);
}
