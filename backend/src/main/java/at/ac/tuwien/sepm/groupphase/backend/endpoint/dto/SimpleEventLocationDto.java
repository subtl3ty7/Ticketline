package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class SimpleEventLocationDto {
    private Long id;
    private Long showId;
    private String name;
}
