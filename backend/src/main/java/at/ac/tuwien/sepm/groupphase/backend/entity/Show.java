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
    @ManyToMany(fetch = FetchType.EAGER)
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

    @Column
    private Integer price;

    @Column
    private String eventName;

    @ToString.Exclude
    @NotNull
    @Lob
    @Column(nullable = false, name = "photo")
    private String photo;

    @NotNull
    @Size(min=1, max=10000)
    @Column(nullable = false, length = 10000)
    private String description;

    public Show(){
    }
}
