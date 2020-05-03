package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DetailedEventDto {

    private String eventCode;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private String name;
    private String description;
    private int startPrice;
    private String photo;
    private List<Show> shows;
    private List<String> artists;
    private String type;
    private String category;
    private List<Integer> prices;
    private int totalTicketsSold;


    public static final class DetailedEventDtoBuilder {

        private String eventCode;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private String name;
        private String description;
        private int startPrice;
        private String photo;
        private List<Show> shows;
        private List<String> artists;
        private String type;
        private String category;
        private List<Integer> prices;
        private int totalTicketsSold;

        private DetailedEventDtoBuilder(String eventCode, LocalDateTime startsAt, String name,
                                      String description, int startPrice, LocalDateTime endsAt,
                                      String photo, List<Show> shows, List<String> artists, String type,
                                      String category, List<Integer> prices, int totalTicketsSold) {
            this.eventCode = eventCode;
            this.startsAt = startsAt;
            this.name = name;
            this.description = description;
            this.startPrice = startPrice;
            this.endsAt = endsAt;
            this.photo = photo;
            this.shows = shows;
            this.artists = artists;
            this.type = type;
            this.category = category;
            this.prices = prices;
            this.totalTicketsSold = totalTicketsSold;
        }

        public static DetailedEventDto.DetailedEventDtoBuilder aDetailedEventDto(String eventCode, LocalDateTime startsAt, String name,
                                                                           String description, int startPrice, LocalDateTime endsAt,
                                                                           String photo, List<Show> shows, List<String> artists, String type,
                                                                           String category, List<Integer> prices, int totalTicketsSold) {

            return new DetailedEventDto.DetailedEventDtoBuilder(eventCode, startsAt, name, description, startPrice, endsAt, photo, shows, artists,
                type, category, prices, totalTicketsSold);
        }

        public DetailedEventDto build() {
            DetailedEventDto detailedEventDto = new DetailedEventDto();
            detailedEventDto.setEventCode(eventCode);
            detailedEventDto.setStartsAt(startsAt);
            detailedEventDto.setName(name);
            detailedEventDto.setDescription(description);
            detailedEventDto.setStartPrice(startPrice);
            detailedEventDto.setEndsAt(endsAt);
            detailedEventDto.setPhoto(photo);
            detailedEventDto.setShows(shows);
            detailedEventDto.setArtists(artists);
            detailedEventDto.setType(type);
            detailedEventDto.setCategory(category);
            detailedEventDto.setPrices(prices);
            detailedEventDto.setTotalTicketsSold(totalTicketsSold);
            return detailedEventDto;
        }
    }
}
