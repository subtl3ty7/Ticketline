package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket")
@Setter
@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Ticket {
    public Ticket(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @NotNull
    @Size(min=6, max=6)
    @Column(nullable = false, length = 6, name = "ticket_code", unique = true)
    private String ticketCode;

    @Column
    private boolean isPurchased;

    @Column
    private LocalDateTime purchaseDate;

    @OneToOne
    private Seat seat;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(nullable = false, name = "USER_CODE", length = 6)
    private String userCode;

    @Column
    private Integer price;

    @ManyToOne
    @JoinTable(
        name= "ticket_show",
        joinColumns = @JoinColumn(name = "id"),
        inverseJoinColumns = @JoinColumn(name = "ticketId"))
    private Show show;


}
