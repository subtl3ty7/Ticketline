package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class EventLocation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SHOW_ID")
    private Long showId;

    @NotNull
    @Size(min=1, max = 100)
    @Column(nullable = false, length = 100)
    private String name;

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


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,  orphanRemoval = true)
    @JoinColumn(name = "EVENT_LOCATION_ID", referencedColumnName = "ID")
    private List<Section> sections;

    @Column
    private int capacity;

    public EventLocation() {}

    public EventLocation(EventLocation eventLocation) {
        this.city = eventLocation.getCity();
        this.country = eventLocation.getCountry();
        this.capacity = eventLocation.getCapacity();
        this.plz = eventLocation.getPlz();
        this.eventLocationDescription = eventLocation.getEventLocationDescription();
        this.name = eventLocation.getName();
        this.street = eventLocation.getStreet();
        this.sections = new ArrayList<>();
        for(Section section: eventLocation.getSections()) {
            this.sections.add(new Section(section));
        }
    }
}
