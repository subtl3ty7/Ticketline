package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.junit.jupiter.api.*;
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
public class EventServiceTest implements TestData {

    @Autowired
    private EventService eventService;


    @Test
    public void whenFindEventByNonExistingCode_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   eventService.findByEventCode("wrong"));
    }

    @Test
    public void whenDeleteEventByNonExistingCode_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   eventService.deletebyEventCode("wrong"));
    }

    @Test
    public void whenFindEventsByNonExistingArtistId_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   eventService.findEventsByArtistId(-1L, 0));
    }
}
