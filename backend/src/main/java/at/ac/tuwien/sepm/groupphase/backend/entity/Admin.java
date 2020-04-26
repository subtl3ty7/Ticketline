package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Admin extends AbstractUser {

    public Admin() {
    }

    public Admin(String email, String password, String usercode) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCode(usercode);
    }
}
