package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class SimpleEventLocationDto {
    private Long id;
    private Long parentId;
    private String name;
    private String eventLocationDescription;
    private String street;
    private String plz;
    private String city;
    private String country;
    private int capacity;
}
