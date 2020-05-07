package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class EventLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SHOW_ID")
    private Long showId;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String eventLocationName;

    @Size(max = 1000)
    @Column(length = 1000)
    private String eventLocationDescription;

    @Size(max = 1000)
    @Column(length = 1000)
    private String street;

    @Size(max = 50)
    @Column(length = 50)
    private String plz;

    @Size(max = 1000)
    @Column(length = 1000)
    private String city;

    @Size(max = 100)
    @Column(length = 100)
    private String country;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "EVENT_LOCATION_ID", referencedColumnName = "ID")
    private List<Section> sections;

    @Column
    private int capacity;

    public EventLocation() {}
}
