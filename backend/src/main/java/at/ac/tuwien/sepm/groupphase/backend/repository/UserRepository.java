package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Admin;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

//TODO: replace this class with a correct ApplicationUser JPARepository implementation
@Repository
public class UserRepository {

    private final AbstractUser user;
    private final AbstractUser admin;

    @Autowired
    public UserRepository(PasswordEncoder passwordEncoder) {
        user = new ApplicationUser("user@email.com", passwordEncoder.encode("password"), "U123XY");
        admin = new Admin("admin@email.com", passwordEncoder.encode("password"), "U123XY");
    }

    public AbstractUser findUserByEmail(String email) {
        if (email.equals(user.getEmail())) return user;
        if (email.equals(admin.getEmail())) return admin;
        return null; // In this case null is returned to fake Repository behavior
    }


}
