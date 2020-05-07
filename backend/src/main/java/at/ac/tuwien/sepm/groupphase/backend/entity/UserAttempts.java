package at.ac.tuwien.sepm.groupphase.backend.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;


@Entity
@Table(name = "user_attempt")
@Getter
@Setter
@ToString
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

    public UserAttempts(AbstractUser user) {
        this.email = user.getEmail();
        this.attempts = 0;
    }

    public static final class UserAttemptsBuilder {
        private int attempts;
        private Long id;
        private String email;

        private UserAttemptsBuilder() {

        }

        public static UserAttempts.UserAttemptsBuilder aAttempts() {
            return new UserAttempts.UserAttemptsBuilder();
        }

        public UserAttempts.UserAttemptsBuilder withAttempts(int attempts){
            this.attempts = attempts;
            return this;
        }

        public UserAttempts.UserAttemptsBuilder withEmail(String email){
            this.email = email;
            return this;
        }

        public UserAttempts.UserAttemptsBuilder withId(long id){
            this.id = id;
            return this;
        }

        public UserAttempts build(){
            UserAttempts userAttempts = new UserAttempts();
            userAttempts.setAttempts(attempts);
            userAttempts.setEmail(email);
            userAttempts.setId(id);
            return userAttempts;
        }


    }
}
