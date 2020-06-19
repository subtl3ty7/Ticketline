package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class DetailedTicketDto {
    private Long ticketId;
    private String ticketCode;
    private boolean isPurchased;
    private boolean isReserved;
    private boolean isCancelled;
    private LocalDateTime purchaseDate;
    private SeatDto seat;
    private String userCode;
    private Double price;
    @ToString.Exclude
    private SimpleShowDto show;
    @ToString.Exclude
    private SimpleEventDto event;
}
