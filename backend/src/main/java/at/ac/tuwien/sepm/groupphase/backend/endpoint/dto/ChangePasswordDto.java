package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ChangePasswordDto {
    private String email;
    private String newPassword;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangePasswordDto changePasswordDto = (ChangePasswordDto) o;
        return Objects.equals(getEmail(), changePasswordDto.getEmail()) &&
            Objects.equals(getNewPassword(), changePasswordDto.getNewPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getNewPassword());
    }

    @Override
    public String toString() {
        return "ChangePasswordDto{" +
            "email=" + email +
            ", newPassword='" + newPassword + '\'' +
            '}';
    }
}
