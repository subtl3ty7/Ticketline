package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

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
    @ToString.Exclude
    private List<SeatDto> seats;
    private int capacity;
    private double price;
}
