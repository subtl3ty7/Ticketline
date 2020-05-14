package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class EventLocationDto {
    private Long id;
    private Long showId;
    private String name;
    private String eventLocationDescription;
    private String street;
    private String plz;
    private String city;
    private String country;
    private List<SectionDto> sections;
    private int capacity;
}
