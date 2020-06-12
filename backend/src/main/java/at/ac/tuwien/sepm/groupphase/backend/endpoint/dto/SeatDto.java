package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class SeatDto {
    private Long id;
    private Long sectionId;
    private String seatRow;
    private String seatColumn;
    private double price;
}
