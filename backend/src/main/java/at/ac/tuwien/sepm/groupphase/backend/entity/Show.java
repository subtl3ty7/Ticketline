package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@ToString
public class Show implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_code")
    private String eventCode;

    @NotNull
    @Column(nullable = false, name = "start_datetime")
    private LocalDateTime startsAt;

    @NotNull
    @Column(nullable = false, name = "end_datetime")
    private LocalDateTime endsAt;

    @Column
    private int ticketsSold;

    @Column
    private int ticketsAvailable;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private EventLocation eventLocation;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "SHOW_TAKEN_SEATS",
        joinColumns = { @JoinColumn(name = "show_id") },
        inverseJoinColumns = { @JoinColumn(name = "seat_id") },
        uniqueConstraints = { @UniqueConstraint(columnNames = {"show_id", "seat_id"}) }
    )
    private List<Seat> takenSeats;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, length = 100)
    private EventTypeEnum eventType;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, length = 100)
    private EventCategoryEnum eventCategory;

    @Column
    public Duration duration;

    @NotNull
    @Column(columnDefinition = "DECIMAL (10, 2)")
    private Double price;

    @NotNull
    @Column
    private String eventName;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Image photo;

    @Size(max=10000)
    @Column(nullable = false, length = 10000)
    private String description;

    public Show(){
    }
}
