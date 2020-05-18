package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class DetailedTicketDto {
    private Long ticketId;
    private String ticketCode;
    private boolean isPurchased;
    private LocalDateTime purchaseDate;
    private Seat seat;
    private String userCode;
    private Integer price;
    private Show show;
}
