package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class EventRepositoryTest implements TestData {

    @Autowired
    EventRepository eventRepository;

    private Event event = Event.builder()
        .id(ID)
        .eventCode(USER_CODE)
        .name(NAME)
        .description(DESC)
        .category(CAT)
        .type(TYP)
        .startsAt(START)
        .endsAt(END)
        .prices(PRICES)
        .totalTicketsSold(TOTAL)
        .artists(ARTISTS)
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
            .startsAt(START)
            .endsAt(END)
            .prices(PRICES)
            .totalTicketsSold(TOTAL)
            .artists(ARTISTS)
            .shows(SHOWS)
            .photo(PHOTO)
            .build();
    }

    @Test
    public void givenNothing_whenSaveEvent_thenFindListWithOneElementAndFindEventByIdAndCode() {
        Event loadedEvent = eventRepository.save(this.event);

        assertAll(
            () -> assertEquals(1, eventRepository.findAll().size()),
            () -> assertNotNull(eventRepository.findById(loadedEvent.getId())),
            () -> assertNotNull(eventRepository.findEventByEventCode(loadedEvent.getEventCode())),
            () -> assertNotNull(eventRepository.findEventByEventCode(loadedEvent.getEventCode()).getShows()),
            () -> assertNotNull(eventRepository.findEventByEventCode(loadedEvent.getEventCode()).getPrices()),
            () -> assertNotNull(eventRepository.findEventByEventCode(loadedEvent.getEventCode()).getArtists())
        );
    }

    @Test
    public void givenNothing_whenSave3Events_thenFindListWith3Elements_Delete1_thenFind2Elements() {
        eventRepository.save(event);
        event.setEventCode("_code1");
        eventRepository.save(event);
        event.setEventCode("_code2");
        eventRepository.save(event);

        assertEquals(3, eventRepository.findAll().size());
        eventRepository.deleteById(eventRepository.findAll().get(0).getId());
        assertEquals(2, eventRepository.findAll().size());
    }

    @Test
    public void givenNothing_whenSave3Events_thenFindListByStartsAt() {
        eventRepository.save(event);
        event.setEventCode("_code1");
        event.setStartsAt(LocalDateTime.of(2021, 11, 13, 12, 15, 0, 0));
        eventRepository.save(event);
        event.setEventCode("_code2");
        eventRepository.save(event);

        assertEquals(3, eventRepository.findAll().size());
        assertEquals(2, eventRepository.findAllByStartsAtAfterOrderByTotalTicketsSoldDesc(START).size());

    }
}
