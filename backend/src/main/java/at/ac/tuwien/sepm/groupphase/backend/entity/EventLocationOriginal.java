package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @Column
    private String type = this.getClass().getSimpleName();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SHOW_ID", referencedColumnName = "ID")
    @Fetch(FetchMode.SELECT) //only way to fetch more than two collections with type eager ...
    private List<EventLocation> shows;

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

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,  orphanRemoval = true)
    @JoinColumn(name = "EVENT_LOCATION_ID", referencedColumnName = "ID")
    @Fetch(FetchMode.SELECT) //only way to fetch more than two collections with type eager ...
    private List<Section> sections;

    @Column
    private int capacity;

    public EventLocation() {}
}
