package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
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
    private boolean isAdmin;


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
            ", isAdmin=" + isAdmin +
            '}';
    }
}
