package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.springframework.stereotype.Component;

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

    @NotNull
    @Column(nullable = false, name = "is_purchased")
    private boolean isPurchased;

    @NotNull
    @Column(nullable = false, name = "purchase_date")
    private LocalDateTime purchaseDate;

    @OneToOne
    private Seat seat;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(nullable = false, name = "user_code", length = 6)
    private String userCode;

    @NotNull
    @Column(nullable = false, name = "price")
    private Integer price;

    @ManyToOne
    private Show show;


}
