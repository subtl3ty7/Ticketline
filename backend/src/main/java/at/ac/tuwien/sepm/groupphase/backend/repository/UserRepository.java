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
     * @param usercode - a code of the user to look for
     * @return a user with corresponding user code.
     */
    AbstractUser findAbstractUserByUserCode(String usercode);

    /**
     * Find a user by its email.
     *
     * @param email - email to look for
     * @return a user with corresponding email.
     */
    AbstractUser findAbstractUserByEmail(String email);

    /**
     * Find a soft deleted user by his/hers email
     *
     * @param email - email of the deleted user to look for
     * @return a single user that has been deleted and matches the given email
     */
    @Query(value = "SELECT * FROM USER u WHERE u.EMAIL = :email AND u.IS_DELETED = TRUE", nativeQuery = true)
    AbstractUser findSoftDeletedAbstractUserByEmail(@Param("email") String email);


    /**
     * Find deleted user by his/hers user code
     *
     * @param userCode - user code of the deleted user
     * @return - a deleted user
     */
    @Query(value = "SELECT * FROM USER u WHERE u.EMAIL = :userCode AND u.IS_DELETED = TRUE", nativeQuery = true)
    AbstractUser findSoftDeletedAbstractUserByUserCode(@Param("userCode") String userCode);

    /**
     * Find all users by their user code, first and last name and email
     *
     * @param u - user code to look for
     * @param f - first name to look for
     * @param l - last name to look for
     * @param e - email to look for
     * @return - a list of all users that match the given criteria
     */
    List<AbstractUser> findAllByUserCodeContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String u, String f, String l, String e);

}
