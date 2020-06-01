package at.ac.tuwien.sepm.groupphase.backend.repository;


import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {

    List<Merchandise> findAllByPremiumIsTrue();





}
