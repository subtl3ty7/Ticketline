package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Entity
@Table(name = "user_attempt")
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

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts){
        this.attempts = attempts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
