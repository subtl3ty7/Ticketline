package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventLocationMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventLocationMappingTest implements TestData {

    private final EventLocation eventLocation = EventLocation.builder()
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

    @Autowired
    private EventLocationMapper eventLocationMapper;

    @Test
    public void shouldMapEventLocationToEventLocationDto() {
        EventLocationDto eventLocationDto = eventLocationMapper.eventLocationToEventLocationDto(eventLocation);
        assertAll(
            () -> assertEquals(FNAME, eventLocationDto.getName()),
            () -> assertEquals(DESC, eventLocationDto.getEventLocationDescription()),
            () -> assertEquals(TOTAL, eventLocationDto.getCapacity()),
            () -> assertEquals(STREET, eventLocationDto.getStreet()),
            () -> assertEquals(CITY, eventLocationDto.getCity()),
            () -> assertEquals(COUNTRY, eventLocationDto.getCountry()),
            () -> assertEquals(PLZ, eventLocationDto.getPlz())
        );
    }

    @Test
    public void shouldMapEventLocationListToEventLocationDto() {
        List<EventLocation> eventLocations = new ArrayList<>();
        eventLocations.add(eventLocation);
        eventLocations.add(eventLocation);

        List<EventLocationDto> eventLocationDtos = eventLocationMapper.eventLocationToEventLocationDto(eventLocations);
        EventLocationDto eventLocationDto = eventLocationDtos.get(0);
        assertAll(
            () -> assertEquals(FNAME, eventLocationDto.getName()),
            () -> assertEquals(DESC, eventLocationDto.getEventLocationDescription()),
            () -> assertEquals(TOTAL, eventLocationDto.getCapacity()),
            () -> assertEquals(ID, eventLocationDto.getId()),
            () -> assertEquals(STREET, eventLocationDto.getStreet()),
            () -> assertEquals(CITY, eventLocationDto.getCity()),
            () -> assertEquals(COUNTRY, eventLocationDto.getCountry()),
            () -> assertEquals(PLZ, eventLocationDto.getPlz())
        );
    }
}
