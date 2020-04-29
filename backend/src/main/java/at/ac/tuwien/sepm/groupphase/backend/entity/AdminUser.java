package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "admin")
@DiscriminatorValue("ADMIN_USER")
public class AdminUser extends AbstractUser {


    public AdminUser() {
    }

    public AdminUser(String email, String password, String usercode) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCode(usercode);
    }
    public AdminUser(String email, String password, String usercode, String firstName, String lastName) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCode(usercode);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

}
