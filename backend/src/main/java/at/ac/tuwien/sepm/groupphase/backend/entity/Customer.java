package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "application_user")
@DiscriminatorValue("CUSTOMER")
public class Customer extends AbstractUser {

    @Column(name = "POINTS")
    private Long points;

    @Column(name = "IS_BLOCKED")
    private boolean isBlocked;

    public Customer() {
    }

    public Customer(String email, String password, String usercode) {
     this.setEmail(email);
     this.setPassword(password);
     this.setUserCode(usercode);
    }
    public Customer(String email, String password, String usercode, String firstName, String lastName) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCode(usercode);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked){
        this.isBlocked = blocked;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
