package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

    @Column(nullable = false, length = 6, name = "event_code", unique = true)
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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_code", referencedColumnName = "event_code")
    private List<Show> shows;

    @Column
    private int totalTicketsSold;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<String> artists;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT) //only way to fetch more than two collections with type eager ...
    private List<Integer> prices;
}