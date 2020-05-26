package at.ac.tuwien.sepm.groupphase.backend.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@SQLDelete(sql = "UPDATE USER_ATTEMPT SET is_Deleted=true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_Deleted=false")
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

    @Column
    private boolean isDeleted;

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

    @PreRemove
    public void deleteUser() {
        this.isDeleted = true;
    }
}
