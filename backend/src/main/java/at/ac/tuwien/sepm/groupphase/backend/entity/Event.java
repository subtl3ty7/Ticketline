package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String eventCode;

    @Column(nullable = false, length = 6)
    private String eventHallCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 10000)
    private String description;

    @Column(nullable = false, name = "start_datetime")
    private LocalDateTime startsAt;

    @Column(nullable = false, name = "end_datetime")
    private LocalDateTime endsAt;

    @Column(nullable = false, name = "photo")
    private String photo;

    @Column
    private int ticketsSold;

    @Column(nullable = false)
    private int capacity;

    @Column
    private int ticketsLeft = capacity - ticketsSold;

    @Column(nullable = false)
    private String category;

    @ElementCollection
    private List<String> artists;

    @ElementCollection
    private List<Integer> prices;

    @ElementCollection
    private List<String> freeSeats;

    public Event() {
    }

    public Event(String eventCode, String eventHallCode, String name, String description, List<String> artists, LocalDateTime startsAt, LocalDateTime endsAt, int capacity, List<String> freeSeats, String category, List<Integer> prices) {
        this.eventCode = eventCode;
        this.eventHallCode = eventHallCode;
        this.name = name;
        this.description = description;
        this.artists = artists;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.capacity = capacity;
        this.freeSeats = freeSeats;
        this.category = category;
        this.prices = prices;
    }


}
