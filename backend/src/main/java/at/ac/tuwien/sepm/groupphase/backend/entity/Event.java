package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=6, max=6)
    @Column(nullable = false, length = 6, name = "event_code", unique = true)
    private String eventCode;

    @NotNull
    @Size(min=1, max=100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull
    @Size(min=1, max=1000)
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull
    @Size(min=1, max=100)
    @Column(nullable = false, length = 100)
    private String category;

    @NotNull
    @Size(min=1, max=100)
    @Column(nullable = false, length = 100)
    private String type;

    @NotNull
    @Column(nullable = false, name = "start_datetime")
    private LocalDateTime startsAt;

    @NotNull
    @Column(nullable = false, name = "end_datetime")
    private LocalDateTime endsAt;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_code", referencedColumnName = "event_code")
    @Fetch(FetchMode.SELECT) //only way to fetch more than two collections with type eager ...
    private List<Show> shows;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT) //only way to fetch more than two collections with type eager ...
    private List<Artist> artists;

    @Column
    private int totalTicketsSold;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT) //only way to fetch more than two collections with type eager ...
    private List<Integer> prices;

    @ToString.Exclude
    @NotNull
    @Lob
    @Column(nullable = false, name = "photo")
    private String photo;

    @NotNull
    @Column
    private EventTypeEnum eventType;

    @NotNull
    @Column
    private EventCategoryEnum eventCategory;

}