package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class ApplicationUser extends AbstractUser {

    @Column(name = "POINTS")
    private Long points;

    @Column(name = "IS_BLOCKED")
    private boolean isBlocked;

    public ApplicationUser() {
    }

    public ApplicationUser(String email, String password, String usercode) {
     this.setEmail(email);
     this.setPassword(password);
     this.setUserCode(usercode);
    }

}
