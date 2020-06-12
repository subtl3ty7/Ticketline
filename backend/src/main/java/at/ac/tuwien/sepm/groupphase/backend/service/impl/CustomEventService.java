package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.EventValidator;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomEventService implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;
    private final EventValidator validator;
    private final ArtistRepository artistRepository;

    private float runningTimeTest = 0;
    private float runningTime2= 0;
    private float runningTime3 = 0;
    private float runningTime4 = 0;


    @Autowired
    public CustomEventService(EventRepository eventRepository, EventValidator validator, EventLocationRepository eventLocationRepository, ArtistRepository artistRepository) {
        this.eventRepository = eventRepository;
        this.validator = validator;
        this.eventLocationRepository = eventLocationRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Event> findTop10EventsOfMonth() {
        List<Event> top10 = eventRepository.findTop10ByStartsAtAfterOrderByTotalTicketsSoldDesc(LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),1,0,0));
        return top10;
    }

    @Override
    public List<Event> findTop10EventsOfMonthByCategory(String category) {
        List<Event> top10 = eventRepository.findTop10ByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),1,0,0), category);
        return top10;
    }

    @Override
    public List<Event> findAllEvents() {
        List<Event> allEvents = eventRepository.findAll();
        return allEvents;
    }

    @Override
    public Event createNewEvent(Event event){
        event.setEventCode(getNewEventCode());
        validator.validate(event).throwIfViolated();

        List<Artist> artists = new ArrayList<>();
        for (Artist a : event.getArtists()) {
            //if the artist has an id assigned and it exists in the database, then replace it with the one in the database
            //otherwise, set the id null so that a new artist entity can be created in the database
            if(a.getId() != null) {
                Artist artist = artistRepository.findArtistById(a.getId());
                if(artist == null) {
                    a.setId(null);
                    artists.add(a);
                } else {
                    artists.add(artist);
                }
            }
        }

        //Give shows new EventLocation Entities (copies of existing ones) to make sure they all can have different seating assignments
        for(Show show: event.getShows()) {
            EventLocation eventLocation = eventLocationRepository.findEventLocationById(show.getEventLocation().getId());
            show.setEventLocation(eventLocation);
            show.setPhoto(event.getPhoto());
        }

        LocalDateTime startTest = LocalDateTime.now();
        //event.setPhoto(new Image(null, "dd"));
        //event.setShows(null);
        Event event1 = eventRepository.save(event);
        LocalDateTime endTest = LocalDateTime.now();
        this.runningTimeTest += Duration.between(startTest, endTest).toMillis();
        LOGGER.info("Test Area took " + runningTimeTest/1000.0 + " seconds");

        return event1;
    }

    private String getNewEventCode() {
        final int maxAttempts = 1000;
        String eventCode = "";
        int i;
        for(i=0; i<maxAttempts; i++) {
            eventCode = CodeGenerator.generateEventCode();
            if(!validator.validateEventCode(eventCode).isViolated()) {
                break;
            }
        }
        if(i==1000) {
            throw new ServiceException("Something went wrong while generating EventCode", null);
        }
        return eventCode;
    }

    @Override
    @Transactional
    public Event findByEventCode(String eventCode) {
        validator.validateExists(eventCode).throwIfViolated();
        Event event = eventRepository.findEventByEventCode(eventCode);
        for(Show show: event.getShows()) {
            Hibernate.initialize(show.getEventLocation().getShows());
        }
        Hibernate.initialize(event.getArtists());
        return event;
    }

    @Override
    public void deletebyEventCode(String eventCode) {
        validator.validateExists(eventCode).throwIfViolated();
        Event event = eventRepository.findEventByEventCode(eventCode);
        eventRepository.delete(event);
    }

    @Override
    public List<Event> findEventsByArtistId(Long artistId) {
        validator.validateExists(artistId).throwIfViolated();
        Artist artist = artistRepository.findArtistById(artistId);
        return eventRepository.findEventsByArtistsContaining(artist);

    }

    @Override
    public List<Event> findEventsByName(String name) {
        List<Event> events = eventRepository.findEventsByNameContainingIgnoreCase(name);
        return events;
    }

    @Override
    public List<Event> findEventsAdvanced(String name, Integer type, Integer category, LocalDateTime startsAt, LocalDateTime endsAt, Duration showDuration) {
        return eventRepository.findEventsByNameContainingIgnoreCaseAndTypeContainingAndCategoryContainingAndStartsAtIsGreaterThanEqualAndEndsAtIsLessThanEqualAndShowsDurationLessThanEqual(name, type, category, startsAt, endsAt, showDuration);
    }

    @Override
    public List<Event> findSimpleEventsByParam(String eventCode, String name, LocalDateTime startRange, LocalDateTime endRange) {
        List<Event> events =  eventRepository.findAllByEventCodeContainingIgnoreCaseAndNameContainingIgnoreCaseAndStartsAtBetween(eventCode, name, startRange, endRange);
        return events;
    }
}
