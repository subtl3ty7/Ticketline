package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "reset_password")
@Getter
@Setter
@ToString
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    @Column(name = "resetPasswordCode")
    private String resetPasswordCode;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public ResetPassword() {

    }
    public ResetPassword(String email, String resetPasswordCode, LocalDateTime createdAt) {
        this.email = email;
        this.resetPasswordCode = resetPasswordCode;
        this.createdAt = createdAt;
    }


}
