package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventTypeEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import lombok.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

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
    private EventTypeEnum type;
    private EventCategoryEnum category;
    private Period period;
    private float price;
    private String eventName;
    private String photo;
    private String description;
    @ToString.Exclude
    private EventLocationDto eventLocation;
    private List<SeatDto> takenSeats;
    private SimpleEventDto event;
    private long duration;
}
