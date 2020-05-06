package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@Builder
@Entity
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SECTION_ID")
    private Long sectionId;

    @NotNull
    @Column(nullable = false)
    private char seatRow;

    @NotNull
    @Column(nullable = false)
    private char seatColumn;

    @Column
    private boolean isFree;

    public Seat() {}

    public Seat(char seatColumn, char seatRow) {
        this.seatColumn = seatColumn;
        this.seatRow = seatRow;
    }
}
