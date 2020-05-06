package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class ShowDto {
    private Long id;
    private String eventCode;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private int ticketsSold;
    private int ticketsAvailable;
    private EventLocationDto eventLocation;
}
