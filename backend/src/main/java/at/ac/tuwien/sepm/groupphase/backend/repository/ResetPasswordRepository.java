package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

    /**
     *
     *
     * @param email - email to look for
     * @return
     */
    ResetPassword findByEmail(String email);

    /**
     *
     * @param resetPasswordCode - reset password code to look for
     * @return
     */
    ResetPassword findByResetPasswordCode(String resetPasswordCode);

    /**
     *
     * @param localDateTime - local date time to look for
     * @return
     */
    List<ResetPassword> findAllByCreatedAtBefore(LocalDateTime localDateTime);

}
