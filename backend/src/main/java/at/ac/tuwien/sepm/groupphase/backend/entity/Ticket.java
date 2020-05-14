package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "ticket")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=6, max=6)
    @Column(nullable = false, length = 6, name = "ticket_code", unique = true)
    private String ticketCode;

    @Column
    private boolean isPurchased;

    @OneToOne
    private Seat seat;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(nullable = false, unique = true, name = "USER_CODE", length = 6)
    private String userCode;

    @NotNull
    @Size(min=6, max=6)
    @Column(nullable = false, length = 6, name = "event_code", unique = true)
    private String eventCode;

    @OneToOne
    private EventLocation eventLocation;

    @Column
    private Integer price;

    @ManyToOne
    private Show show;


}
