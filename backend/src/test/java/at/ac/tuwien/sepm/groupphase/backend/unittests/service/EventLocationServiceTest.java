package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventLocationServiceTest implements TestData {

    @Autowired
    private EventLocationService eventLocationService;

    @Test
    public void whenSaveEventLocationWithoutSections_thenValidationException() {
        EventLocation eventLocation = EventLocation.builder()
            .id(ID)
            .name(FNAME)
            .eventLocationDescription(DESC)
            .capacity(TOTAL)
            .street(STREET)
            .city(CITY)
            .country(COUNTRY)
            .plz(PLZ)
            .build();

        assertThrows(ValidationException.class,
            () ->   eventLocationService.save(eventLocation));
    }

    @Test
    public void givenEventLocations_whenGetAllEventLocations_thenNonEmptyList() {

        List<EventLocation> eventLocations = eventLocationService.getAllEventLocations();
        assertNotEquals(0, eventLocations.size());
    }
}
