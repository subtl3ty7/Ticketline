package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventServiceTest implements TestData {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    private Event event = Event.builder()
        .id(ID)
        .eventCode(USER_CODE)
        .name(NAME)
        .description(DESC)
        .category(CAT)
        .type(TYP)
        .eventCategory(CAT1)
        .eventType(TYP1)
        .startsAt(START)
        .endsAt(END)
        .prices(PRICES)
        .totalTicketsSold(TOTAL)
        .shows(SHOWS)
        .photo(PHOTO)
        .build();

    @BeforeEach
    public void beforeEach() {
        eventRepository.deleteAll();
        event = Event.builder()
            .id(ID)
            .eventCode(USER_CODE)
            .name(NAME)
            .description(DESC)
            .category(CAT)
            .type(TYP)
            .eventCategory(CAT1)
            .eventType(TYP1)
            .startsAt(START)
            .endsAt(END)
            .prices(PRICES)
            .totalTicketsSold(TOTAL)
            .shows(SHOWS)
            .photo(PHOTO)
            .build();
    }

    @Test
    public void givenEvent_whenFindByEventCode_thenEventWithProperties() {
        eventRepository.save(event);

        Event event1 = eventService.findByEventCode(eventRepository.findAll().get(0).getEventCode());
        assertAll(
            () -> assertEquals(USER_CODE, event1.getEventCode()),
            () -> assertEquals(NAME, event1.getName()),
            () -> assertEquals(DESC, event1.getDescription()),
            () -> assertEquals(TYP, event1.getType()),
            () -> assertEquals(CAT, event1.getCategory()),
            () -> assertEquals(TYP1, event1.getEventType()),
            () -> assertEquals(CAT1, event1.getEventCategory()),
            () -> assertEquals(START, event1.getStartsAt()),
            () -> assertEquals(END, event1.getEndsAt()),
            () -> assertEquals(PRICES, event1.getPrices()),
            () -> assertEquals(TOTAL, event1.getTotalTicketsSold())
        );
    }

    @Test
    public void givenEvent_whenGetTop10_thenListWith1Element() {
        eventRepository.save(event);

        List<Event> events = eventService.findTop10EventsOfMonthByCategory(CAT);
        assertEquals(1, events.size());
    }

    @Test
    public void given3Events_whenGetTop10ByCategory_thenListWith2Elements() {
        eventRepository.save(event);
        event.setEventCode("code12");
        eventRepository.save(event);
        event.setEventCode("code13");
        event.setCategory("newCategory");
        eventRepository.save(event);

        List<Event> events = eventService.findTop10EventsOfMonthByCategory(CAT);
        assertEquals(2, events.size());
    }
}
