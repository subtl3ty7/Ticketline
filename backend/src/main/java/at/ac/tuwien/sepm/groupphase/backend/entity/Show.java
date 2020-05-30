package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

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
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "EVENT_LOCATION_COPY_ID", referencedColumnName = "ID")
    private EventLocationCopy eventLocationCopy;

    @Column(name = "EVENT_LOCATION_ORIGINAL_ID")
    private Long eventLocationOriginalId;

    @NotNull
    @Column(nullable = false, length = 100)
    private EventTypeEnum type;

    @NotNull
    @Column(nullable = false, length = 100)
    private EventCategoryEnum category;

    @Column
    public Duration duration;

    @Column
    private int price;

    public Show(){
    }
}
