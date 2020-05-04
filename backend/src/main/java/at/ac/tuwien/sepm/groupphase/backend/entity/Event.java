package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6,name = "event_code", unique=true)
    private String eventCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 10000)
    private String description;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false, length = 100)
    private String type;

    @Column(nullable = false, name = "start_datetime")
    private LocalDateTime startsAt;

    @Column(nullable = false, name = "end_datetime")
    private LocalDateTime endsAt;

    @Column(nullable = false, name = "photo")
    private String photo;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="event_code", referencedColumnName="event_code")
    private List<Show> shows;

    @Column
    private int totalTicketsSold;

    @ElementCollection
    private List<String> artists;

    @ElementCollection
    private List<Integer> prices;


    public Event() {
    }

    public Event(String eventCode, String name, String description, String photo, List<Show> shows, List<String> artists, LocalDateTime startsAt, LocalDateTime endsAt, String type, String category, List<Integer> prices, int totalTicketsSold) {
        this.eventCode = eventCode;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.shows = shows;
        this.artists = artists;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.type = type;
        this.category = category;
        this.prices = prices;
        this.totalTicketsSold = totalTicketsSold;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", eventCode='" + eventCode + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", category='" + category + '\'' +
            ", type='" + type + '\'' +
            ", startsAt=" + startsAt +
            ", endsAt=" + endsAt +
            ", photo='" + photo + '\'' +
            ", shows=" + shows.toString() +
            ", totalTicketsSold=" + totalTicketsSold +
            ", artists=" + artists.toString() +
            ", prices=" + prices.toString() +
            '}';
    }
}
