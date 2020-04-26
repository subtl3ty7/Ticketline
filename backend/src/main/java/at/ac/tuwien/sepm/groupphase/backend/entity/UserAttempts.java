package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_attempt")
public class UserAttempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_CODE", referencedColumnName = "USER_CODE")
    private AbstractUser user;


    @Column(name = "attempts")
    private int attempts;

    public UserAttempts() {

    }
    public UserAttempts(AbstractUser user, int attempts) {
        this.user = user;
        this.attempts = attempts;
    }

}
