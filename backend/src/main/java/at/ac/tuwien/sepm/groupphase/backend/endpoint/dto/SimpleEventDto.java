package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;


import lombok.*;

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
    private Integer startPrice;
    private LocalDateTime endsAt;
    private String photo;
    private int totalTicketsSold;
}
