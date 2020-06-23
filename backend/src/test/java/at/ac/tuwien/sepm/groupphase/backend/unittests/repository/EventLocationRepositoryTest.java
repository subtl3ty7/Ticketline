package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class EventLocationRepositoryTest implements TestData {

    @Autowired
    EventLocationRepository eventLocationRepository;

    @Test
    public void givenNothing_whenSaveEvent_thenFindListWithOneElementAndFindEventByIdAndCode() {
        EventLocation eventLocation = EventLocation.builder()
            .id(ID)
            .name(FNAME)
            .eventLocationDescription(DESC)
            .capacity(TOTAL)
            .street(STREET)
            .city(CITY)
            .country(COUNTRY)
            .plz(PLZ)
            .sections(SECTIONS)
            .build();

        eventLocationRepository.save(eventLocation);

        assertAll(
            () -> assertEquals(1, eventLocationRepository.findAll().size()),
            () -> assertNotNull(eventLocationRepository.findById(eventLocation.getId()))
        );
    }
}
