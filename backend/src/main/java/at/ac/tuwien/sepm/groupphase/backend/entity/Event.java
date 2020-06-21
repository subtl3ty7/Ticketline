package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Duration;
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

    @Size(max=10000)
    @Column(length = 10000)
    private String description;

    @NotNull
    @Column(nullable = false, name = "start_datetime")
    private LocalDateTime startsAt;

    @NotNull
    @Column(nullable = false, name = "end_datetime")
    private LocalDateTime endsAt;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_code", referencedColumnName = "event_code")
    private List<Show> shows;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Artist> artists;

    @Column
    private int totalTicketsSold;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Column(columnDefinition = "DECIMAL (10, 2)")
    private List<Double> prices;

    @ToString.Exclude
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Image photo;

    @NotNull
    @Column
    private EventTypeEnum type;

    @NotNull
    @Column
    private EventCategoryEnum category;

    @NotNull
    @Column
    public Duration duration;
}