package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class SectionDto {
    private Long id;
    private Long eventLocationId;
    private String name;
    private String description;
    private List<Seat> seats;
}
