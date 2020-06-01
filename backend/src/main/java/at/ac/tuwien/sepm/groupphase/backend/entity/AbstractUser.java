package at.ac.tuwien.sepm.groupphase.backend.entity;


import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity(name = "user")
@SQLDelete(sql = "UPDATE USER SET is_Deleted=true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_Deleted=false")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "USER_TYPE", discriminatorType= DiscriminatorType.STRING)
public abstract class AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(nullable = false, unique = true, name = "USER_CODE", length = 6)
    private String userCode;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, name = "FIRST_NAME", length = 30)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, name = "LAST_NAME", length = 30)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, unique = true, name = "EMAIL", length = 100)
    private String email;

    @NotNull
    @Size(min = 8, max = 100)
    @Column(nullable = false, name = "PASSWORD", length = 100)
    private String password;

    @NotNull
    @Column(nullable = false, name = "BIRTHDAY")
    private LocalDateTime birthday;

    @Column(name = "IS_LOGGED")
    private boolean isLogged;

    @NotNull
    @Column(nullable = false, name = "CREATED_AT")
    private LocalDateTime createdAt;

    @NotNull
    @Column(nullable = false, name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name="is_Deleted")
    private boolean isDeleted;

    @PreRemove
    public void deleteUser() {
        this.isDeleted = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "AbstractUser{" +
            "id=" + id +
            ", userCode='" + userCode + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", birthday=" + birthday +
            ", isLogged=" + isLogged +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
