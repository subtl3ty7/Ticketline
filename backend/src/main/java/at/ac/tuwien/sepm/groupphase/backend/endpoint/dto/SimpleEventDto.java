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


    public static final class SimpleEventDtoBuilder {

        private String eventCode;

        private LocalDateTime startsAt;

        private String name;

        private String description;

        private int startPrice;

        private LocalDateTime endsAt;

        private SimpleEventDtoBuilder(String eventCode, LocalDateTime startsAt, String name, String description, int startPrice, LocalDateTime endsAt) {
            this.eventCode = eventCode;
            this.startsAt = startsAt;
            this.name = name;
            this.description = description;
            this.startPrice = startPrice;
            this.endsAt = endsAt;
        }

        public static SimpleEventDtoBuilder aSimpleEventDto(String eventCode, LocalDateTime startsAt, String name, String description, int startPrice, LocalDateTime endsAt) {
            return new SimpleEventDtoBuilder(eventCode, startsAt, name, description, startPrice, endsAt);
        }

        public SimpleEventDto build() {
            SimpleEventDto simpleEventDto = new SimpleEventDto();
            simpleEventDto.setEventCode(eventCode);
            simpleEventDto.setStartsAt(startsAt);
            simpleEventDto.setName(name);
            simpleEventDto.setDescription(description);
            simpleEventDto.setStartPrice(startPrice);
            simpleEventDto.setEndsAt(endsAt);
            return simpleEventDto;
        }
    }
}
