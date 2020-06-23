package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface MerchandiseService {

    /**
     * Create a single new merchandise product.
     *
     * @param product - product to be created
     * @return - a single Merchandise product that has just been created
     */
    Merchandise createNewProduct(Merchandise product);

    /**
     * Find all merchandise products.
     *
     * @return List of all merchandise products.
     */
    List<Merchandise> findAllMerchandiseProducts();

    /**
     * Find all merchandise premium products.
     *
     * @return a list of all merchandise products that are also premium
     */
    List<Merchandise> findAllMerchandisePremiumProducts();

    /**
     * Find merchandise product by its product code
     *
     * @param merchandiseProductCode - merchandise product code to look for
     * @return a single merchandise product with the given product code
     */
    Merchandise findMerchandiseByMerchandiseProductCode(String merchandiseProductCode);

    /**
     * Purchase merchandise product with premium points.
     *
     * @param merchandise - merchandise product to be purchased
     * @param userCode - code of the user that wants to by this product
     * @return a single, bought merchandise product
     */
    Merchandise purchaseWithPremiumPoints(Merchandise merchandise, String userCode);

    /**
     * Purchase merchandise product with money
     *
     * @param merchandise - merchandise product to be purchased
     * @param userCode - code of the user that wants to by this product
     * @return a single, bought merchandise product
     */
    Merchandise purchaseWithMoney(Merchandise merchandise, String userCode);
}
