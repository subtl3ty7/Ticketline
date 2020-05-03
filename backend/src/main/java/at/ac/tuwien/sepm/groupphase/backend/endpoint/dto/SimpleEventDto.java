package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SimpleEventDto {

    private String eventCode;

    private LocalDateTime startsAt;

    private String name;

    private String description;

    private Integer startPrice;

    private LocalDateTime endsAt;

    private String photo;


    public static final class SimpleEventDtoBuilder {

        private String eventCode;

        private LocalDateTime startsAt;

        private String name;

        private String description;

        private int startPrice;

        private LocalDateTime endsAt;

        private String photo;

        private SimpleEventDtoBuilder(String eventCode, LocalDateTime startsAt, String name, String description, int startPrice, LocalDateTime endsAt, String photo) {
            this.eventCode = eventCode;
            this.startsAt = startsAt;
            this.name = name;
            this.description = description;
            this.startPrice = startPrice;
            this.endsAt = endsAt;
            this.photo = photo;
        }

        public static SimpleEventDtoBuilder aSimpleEventDto(String eventCode, LocalDateTime startsAt, String name, String description, int startPrice, LocalDateTime endsAt, String photo) {
            return new SimpleEventDtoBuilder(eventCode, startsAt, name, description, startPrice, endsAt, photo);
        }

        public SimpleEventDto build() {
            SimpleEventDto simpleEventDto = new SimpleEventDto();
            simpleEventDto.setEventCode(eventCode);
            simpleEventDto.setStartsAt(startsAt);
            simpleEventDto.setName(name);
            simpleEventDto.setDescription(description);
            simpleEventDto.setStartPrice(startPrice);
            simpleEventDto.setEndsAt(endsAt);
            simpleEventDto.setPhoto(photo);
            return simpleEventDto;
        }
    }
}
