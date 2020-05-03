package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Date;
import java.util.Objects;

public class UserDto {
    private String userCode;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date birthday;
    private boolean isLogged;
    private Date createdAt;
    private Date updatedAt;
    private Long points;
    private boolean isBlocked;

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto that = (UserDto) o;
        return userCode.equals(that.userCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCode);
    }

    @Override
    public String toString() {
        return "UserDto{" +
            "userCode='" + userCode + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", birthday=" + birthday +
            ", isLogged=" + isLogged +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", points=" + points +
            ", isBlocked=" + isBlocked +
            '}';
    }
}
