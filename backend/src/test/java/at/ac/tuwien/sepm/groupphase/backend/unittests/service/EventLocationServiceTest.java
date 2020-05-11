package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventLocationServiceTest implements TestData {

    @Autowired
    private EventLocationService eventLocationService;

    private final EventLocation eventLocation = EventLocation.builder()
        .id(ID)
        .eventLocationName(FNAME)
        .eventLocationDescription(DESC)
        .showId(ID)
        .capacity(TOTAL)
        .street(STREET)
        .city(CITY)
        .country(COUNTRY)
        .plz(PLZ)
        .sections(SECTIONS)
        .build();

   /* @Test
    public void whenSaveEventLocations_thenFindEventLocationsWithProperties() {
        eventLocationService.save(eventLocation);

        assertEquals(1, eventLocationService.getAllEventLocations().size());
        EventLocation eventLocation = eventLocationService.getAllEventLocations().get(0);
        assertAll(
            () -> assertEquals(ID, eventLocation.getId()),
            () -> assertEquals(FNAME, eventLocation.getEventLocationName()),
            () -> assertEquals(DESC, eventLocation.getEventLocationDescription()),
            () -> assertEquals(ID, eventLocation.getShowId()),
            () -> assertEquals(TOTAL, eventLocation.getCapacity()),
            () -> assertEquals(STREET, eventLocation.getStreet()),
            () -> assertEquals(CITY, eventLocation.getCity()),
            () -> assertEquals(COUNTRY, eventLocation.getCountry()),
            () -> assertEquals(PLZ, eventLocation.getPlz()),
            () -> assertEquals(SECTIONS, eventLocation.getSections())
        );
    } */

}
