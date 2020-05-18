package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttemptsRepository extends JpaRepository<UserAttempts, Long> {

    /**
     * Find attempts of a user by user email.
     *
     * @param email
     * @return an user attempts object for user with email which contains login attempts information
     */
    UserAttempts findUserAttemptsByEmail(String email);

}
