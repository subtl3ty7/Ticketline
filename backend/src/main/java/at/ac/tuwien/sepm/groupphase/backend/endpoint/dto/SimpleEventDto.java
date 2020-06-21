package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;


import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventTypeEnum;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SimpleEventDto {
    private String eventCode;
    private LocalDateTime startsAt;
    private String name;
    private String description;
    private Double startPrice;
    private LocalDateTime endsAt;
    private String photo;
    private int totalTicketsSold;
    private long duration;
    private EventTypeEnum eventType;
    private EventCategoryEnum eventCategory;
}
