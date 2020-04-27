package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserAttempts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAttemptsRepository extends JpaRepository<UserAttempts, Long> {
    UserAttempts findUserAttemptsByUser(AbstractUser user);
}
