package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventLocationServiceTest implements TestData {

    @Autowired
    private EventLocationService eventLocationService;

    @Autowired
    private EventLocationRepository eventLocationRepository;

    private Section section = Section.builder()
        .id(ID)
        .sectionName(FNAME)
        .sectionDescription(DESC)
        .capacity(TOTAL)
        .seats(SEATS)
        .build();

    private EventLocation eventLocation = EventLocation.builder()
        .id(ID)
        .name(FNAME)
        .eventLocationDescription(DESC)
        .capacity(TOTAL)
        .street(STREET)
        .city(CITY)
        .country(COUNTRY)
        .plz(PLZ)
        .sections(List.of(section))
        .build();

    @BeforeEach
    public void beforeEach() {
        eventLocationRepository.deleteAll();
        section = Section.builder()
            .id(ID)
            .sectionName(FNAME)
            .sectionDescription(DESC)
            .capacity(TOTAL)
            .seats(SEATS)
            .build();
        eventLocation = EventLocation.builder()
            .id(ID)
            .name(FNAME)
            .eventLocationDescription(DESC)
            .capacity(TOTAL)
            .street(STREET)
            .city(CITY)
            .country(COUNTRY)
            .plz(PLZ)
            .sections(List.of(section))
            .build();
    }

    @Test
    public void whenSaveEventLocationAndGetAll_thenEventLocationWithProperties() {
        eventLocationService.save(eventLocation);

        assertEquals(1, eventLocationService.getAllEventLocations().size());
        EventLocation eventLocation = eventLocationService.getAllEventLocations().get(0);
        assertAll(
            () -> assertEquals(FNAME, eventLocation.getName()),
            () -> assertEquals(DESC, eventLocation.getEventLocationDescription()),
            () -> assertEquals(TOTAL, eventLocation.getCapacity()),
            () -> assertEquals(STREET, eventLocation.getStreet()),
            () -> assertEquals(CITY, eventLocation.getCity()),
            () -> assertEquals(COUNTRY, eventLocation.getCountry()),
            () -> assertEquals(PLZ, eventLocation.getPlz())
        );
    }

}
