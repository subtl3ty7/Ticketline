package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserAttempts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//TODO: replace this class with a correct ApplicationUser JPARepository implementation
@Repository
public interface UserRepository extends JpaRepository<AbstractUser, Long> {

    /**
     * Find a user by its user code.
     *
     * @param usercode
     * @return a user with corresponding user code.
     */
    AbstractUser findAbstractUserByUserCode(String usercode);

    /**
     * Find a user by its email.
     *
     * @param email
     * @return a user with corresponding email.
     */
    AbstractUser findAbstractUserByEmail(String email);

    List<AbstractUser> findAllByUserCodeContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String u, String f, String l, String e);

}
