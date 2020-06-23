package at.ac.tuwien.sepm.groupphase.backend.repository;


import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {

    /**
     * Find all Merchandise Premium Products
     *
     * @return a list of Merchandise Premium Products
     */
    List<Merchandise> findAllByPremiumIsTrue();

    /**
     * Find a Merchandise Product by its merchandiseProductCode
     *
     * @param merchandiseProductCode - a product code to look for
     * @return a single Merchandise Product which has the corresponding product code
     */
    Merchandise findMerchandiseByMerchandiseProductCode(String merchandiseProductCode);

    /**
     * Find a Merchandise Product by its id
     *
     * @param id - id to look for
     * @return a  single Merchandise Product which has the corresponding id
     */
    Merchandise findMerchandiseById(Long id);




}
