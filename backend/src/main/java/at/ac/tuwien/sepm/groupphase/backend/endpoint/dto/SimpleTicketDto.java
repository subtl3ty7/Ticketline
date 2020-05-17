package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class SimpleTicketDto {
    private Long ticketId;
    private String ticketCode;
    private boolean isPurchased;
    private LocalDateTime purchaseDate;
    private Long seatId;
    private String userCode;
    private Integer price;
    private Long showId;


}
