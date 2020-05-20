package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@Entity
public class Section implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EVENT_LOCATION_ID")
    private Long eventLocationId;

    @NotNull
    @Size(min=1, max = 100)
    @Column(nullable = false, length = 100)
    private String sectionName;

    @Size(max = 1000)
    @Column(length = 1000)
    private String sectionDescription;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "SECTION_ID", referencedColumnName = "ID")
    private List<Seat> seats;

    @Column
    private int capacity;

    public Section() {
    }

    public Section(Section section) {
        this.sectionName = section.getSectionName();
        this.sectionDescription = section.getSectionDescription();
        this.capacity = section.getCapacity();
        this.seats = new ArrayList<>();
        for(Seat seat: section.getSeats()) {
            this.seats.add(new Seat(seat));
        }
    }
}


