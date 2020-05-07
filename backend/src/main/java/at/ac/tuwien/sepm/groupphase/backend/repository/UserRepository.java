package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//TODO: replace this class with a correct ApplicationUser JPARepository implementation
@Repository
public interface UserRepository extends JpaRepository<AbstractUser, Long> {

    AbstractUser findAbstractUserByUserCode(String usercode);
    AbstractUser findAbstractUserByEmail(String email);
    AbstractUser deleteByUserCode(String userCode);
}
