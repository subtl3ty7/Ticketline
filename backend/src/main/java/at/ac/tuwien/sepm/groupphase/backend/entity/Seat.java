package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SECTION_ID")
    private Long sectionId;

    @NotNull
    @Size(min=1, max = 100)
    @Column(nullable = false, length = 100)
    private String seatRow;

    @NotNull
    @Size(min=1, max = 100)
    @Column(nullable = false, length = 100)
    private String seatColumn;

    @Column
    private boolean isFree;

    public Seat() {}

    public Seat(String seatColumn, String seatRow) {
        this.seatColumn = seatColumn;
        this.seatRow = seatRow;
    }

    public Seat(Seat seat) {
        this.seatRow = seat.getSeatRow();
        this.seatColumn = seat.getSeatColumn();
        this.isFree = seat.isFree();
    }

}
