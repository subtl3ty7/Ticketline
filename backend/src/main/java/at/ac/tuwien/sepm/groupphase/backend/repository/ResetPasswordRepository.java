package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

    /**
     * Find the Resetpassword object which contains email and reset password code
     *  associated with an email
     *
     * @param email - email to look for
     * @return - Resetpassword object which contains email and reset password code
     */
    ResetPassword findByEmail(String email);

    /**
     * Find the Resetpassword object which contains email and reset password code
     * associated with a reset password code.
     * @param resetPasswordCode - reset password code to look for
     * @return - Resetpassword object which contains email and reset password code
     */
    ResetPassword findByResetPasswordCode(String resetPasswordCode);

    /**
     * Find the ResetPassword objects which are created before a specific time
     * @param localDateTime - local date time to look for
     * @return - list of ResetPassword objects that are created before the given time
     */
    List<ResetPassword> findAllByCreatedAtBefore(LocalDateTime localDateTime);

}
