package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DetailedEventDto {
    private String eventCode;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private String name;
    private String description;
    private double startPrice;
    private String photo;
    private List<ArtistDto> artists;
    @ToString.Exclude
    private List<SimpleShowDto> shows;
    private String type;
    private String category;
    private List<Double> prices;
    private int totalTicketsSold;
    private long duration;
}
