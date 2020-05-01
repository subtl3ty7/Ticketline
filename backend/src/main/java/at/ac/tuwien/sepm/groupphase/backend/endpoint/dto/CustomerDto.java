package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

public class CustomerDto extends AbstractUserDto {

    private Long points;
    private boolean isBlocked;

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked){
        this.isBlocked = blocked;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
