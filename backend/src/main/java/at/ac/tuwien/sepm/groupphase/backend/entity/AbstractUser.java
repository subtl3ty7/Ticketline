package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "USER_CODE", length = 6)
    private String userCode;

    @Column(nullable = false, name = "FIRST_NAME", length = 30)
    private String firstName;

    @Column(nullable = false, name = "LAST_NAME", length = 30)
    private String lastName;

    @Column(nullable = false, name = "EMAIL", length = 100)
    private String email;

    @Column(nullable = false, name = "PASSWORD", length = 100)
    private String password;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "IS_LOGGED")
    private boolean isLogged;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

}
