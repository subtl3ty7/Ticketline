package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

   ResetPassword findByEmail(String email);
   ResetPassword findByResetPasswordCode(String resetPasswordCode);
   List<ResetPassword> findAllByCreatedAtBefore(LocalDateTime localDateTime);

}
