package at.ac.tuwien.sepm.groupphase.backend.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "user_attempt")
@Getter
@Setter
public class UserAttempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "attempts")
    private int attempts;

    public UserAttempts() {

    }
    public UserAttempts(String email, int attempts) {
        this.email = email;
        this.attempts = attempts;
    }
}
