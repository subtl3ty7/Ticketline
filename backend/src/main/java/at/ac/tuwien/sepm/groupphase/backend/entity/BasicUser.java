package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "application_user")
@DiscriminatorValue("BASIC_USER")
public class BasicUser extends AbstractUser {

    public BasicUser() {
    }

    public BasicUser(String email, String password, String usercode) {
     this.setEmail(email);
     this.setPassword(password);
     this.setUserCode(usercode);
    }
    public BasicUser(String email, String password, String usercode, String firstName, String lastName) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCode(usercode);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }



}
