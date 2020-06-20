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
    private String name;

    @Size(max = 1000)
    @Column(length = 1000)
    private String description;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "SECTION_ID", referencedColumnName = "ID")
    private List<Seat> seats;

    @Column
    private String priceCategory;

    @Column(columnDefinition = "DECIMAL (10, 2)")
    private double price;

    @Column
    private int capacity;

    public Section() {
    }

    public Section(Section section) {
        this.name = section.getName();
        this.description = section.getDescription();
        this.capacity = section.getCapacity();
        this.seats = new ArrayList<>();
        for(Seat seat: section.getSeats()) {
            this.seats.add(new Seat(seat, section.getPrice()));
        }
    }
}


