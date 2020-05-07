package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EVENT_LOCATION_ID")
    private Long eventLocationId;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String sectionName;

    @Size(max = 1000)
    @Column(length = 1000)
    private String sectionDescription;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "SECTION_ID", referencedColumnName = "ID")
    private List<Seat> seats;

    @Column
    private int capacity;

    public Section() {}
}


