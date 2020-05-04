package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
public class Show implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6, name = "event_code")
    private String eventCode;

    @Column(nullable = false, name = "start_datetime")
    private LocalDateTime startsAt;

    @Column(nullable = false, name = "end_datetime")
    private LocalDateTime endsAt;

    @ElementCollection(targetClass = Seat.class, fetch = FetchType.EAGER)
    private List<Seat> freeSeats;

    @Column
    private int ticketsSold;

    @Column
    private int ticketsAvailable;

    public Show() {
    }

    public Show(List<Seat> freeSeats, String eventCode) {
        this.eventCode = eventCode;
        this.freeSeats =  freeSeats;
    }

    @Override
    public String toString() {
        return "Show{" +
            "id=" + id +
            ", eventCode='" + eventCode + '\'' +
            ", startsAt=" + startsAt +
            ", endsAt=" + endsAt +
            ", freeSeats=" + freeSeats.toString() +
            ", ticketsSold=" + ticketsSold +
            ", ticketsAvailable=" + ticketsAvailable +
            '}';
    }
}
