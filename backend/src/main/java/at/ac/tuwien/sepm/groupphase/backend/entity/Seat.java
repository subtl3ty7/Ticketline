package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class Seat implements Serializable {

    @Column(nullable = false)
    private char seatRow;

    @Column(nullable = false)
    private char seatColumn;

    public Seat() {

    }

    public Seat(char row, char column) {
        this.seatColumn = column;
        this.seatRow= row;
    }

    @Override
    public String toString() {
        return "Seat{" +
            "seatRow=" + seatRow +
            ", seatColumn=" + seatColumn +
            '}';
    }
}
