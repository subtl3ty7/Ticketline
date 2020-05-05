package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Show implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6, name = "event_code")
    private String eventCode;

    @Column(nullable = false, name = "start_datetime")
    private LocalDateTime startsAt;

    @Column(nullable = false, name = "end_datetime")
    private LocalDateTime endsAt;

    @Column
    private int ticketsSold;

    @Column
    private int ticketsAvailable;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "SHOW_ID", referencedColumnName = "ID")
    private List<EventLocation> eventLocation;

    public Show() {
    }

    public Show(List<Seat> freeSeats, String eventCode) {
        this.eventCode = eventCode;
    }

    @Override
    public String toString() {
        return "Show{" +
            "id=" + id +
            ", eventCode='" + eventCode + '\'' +
            ", startsAt=" + startsAt +
            ", endsAt=" + endsAt +
            ", ticketsSold=" + ticketsSold +
            ", ticketsAvailable=" + ticketsAvailable +
            '}';
    }
}
