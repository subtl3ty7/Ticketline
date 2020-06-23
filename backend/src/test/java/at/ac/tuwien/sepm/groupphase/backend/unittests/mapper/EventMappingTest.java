package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.h2.util.Cache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.Max;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventMappingTest implements TestData {

    private final Event event = Event.builder()
        .id(ID)
        .eventCode(USER_CODE)
        .name(NAME)
        .description(DESC)
        .category(CAT1)
        .type(TYP1)
        .startsAt(START)
        .endsAt(END)
        .duration(DURATION)
        .prices(PRICES)
        .totalTicketsSold(TOTAL)
        .shows(SHOWS)
        .photo(PHOTO)
        .build();

    @Autowired
    private EventMapper eventMapper;

    @Test
    public void shouldMapEventToSimpleEventDto() {
        SimpleEventDto simpleEventDto = eventMapper.eventToSimpleEventDto(event);
        assertAll(
            () -> assertEquals(USER_CODE, simpleEventDto.getEventCode()),
            () -> assertEquals(NAME, simpleEventDto.getName()),
            () -> assertEquals(DESC, simpleEventDto.getDescription()),
            () -> assertEquals(START, simpleEventDto.getStartsAt()),
            () -> assertEquals(END, simpleEventDto.getEndsAt()),
            () -> assertEquals(PRICES.get(0), simpleEventDto.getStartPrice())
        );
    }

    @Test
    public void shouldMapEventListToSimpleEventDTOList() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        eventList.add(event);

        List<SimpleEventDto> simpleEventDtos = eventMapper.eventToSimpleEventDto(eventList);
        assertEquals(2, simpleEventDtos.size());
        SimpleEventDto simpleEventDto = simpleEventDtos.get(0);
        assertAll(
            () -> assertEquals(USER_CODE, simpleEventDto.getEventCode()),
            () -> assertEquals(NAME, simpleEventDto.getName()),
            () -> assertEquals(DESC, simpleEventDto.getDescription()),
            () -> assertEquals(START, simpleEventDto.getStartsAt()),
            () -> assertEquals(END, simpleEventDto.getEndsAt()),
            () -> assertEquals(PRICES.get(0), simpleEventDto.getStartPrice())
        );
    }

    @Test
    public void shouldMapEventToDetailedEventDto() {
        DetailedEventDto detailedEventDto = eventMapper.eventToDetailedEventDto(event);
        assertAll(
            () -> assertEquals(USER_CODE, detailedEventDto.getEventCode()),
            () -> assertEquals(NAME, detailedEventDto.getName()),
            () -> assertEquals(DESC, detailedEventDto.getDescription()),
            () -> assertEquals(START, detailedEventDto.getStartsAt()),
            () -> assertEquals(END, detailedEventDto.getEndsAt()),
            () -> assertEquals(PRICES, detailedEventDto.getPrices()),
            () -> assertEquals(PRICES.get(0), detailedEventDto.getStartPrice()),
            () -> assertEquals(TOTAL, detailedEventDto.getTotalTicketsSold())
        );
    }

    @Test
    public void shouldMapDetailedEventDtoToEvent() {
        DetailedEventDto detailedEventDto = eventMapper.eventToDetailedEventDto(event);
        Event event1 = eventMapper.detailedEventDtoToEvent(detailedEventDto);
        assertAll(
            () -> assertEquals(USER_CODE, event1.getEventCode()),
            () -> assertEquals(NAME, event1.getName()),
            () -> assertEquals(DESC, event1.getDescription()),
            () -> assertEquals(TYP1, event1.getType()),
            () -> assertEquals(CAT1, event1.getCategory()),
            () -> assertEquals(START, event1.getStartsAt()),
            () -> assertEquals(END, event1.getEndsAt()),
            () -> assertEquals(PRICES, event1.getPrices()),
            () -> assertEquals(TOTAL, event1.getTotalTicketsSold())
        );
    }



}
