package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Autowired
    public CustomEventService(EventRepository eventRepository, EventValidator validator, EventLocationRepository eventLocationRepository, ArtistRepository artistRepository) {
        this.eventRepository = eventRepository;
        this.validator = validator;
        this.eventLocationRepository = eventLocationRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Event> findTop10EventsOfMonth() {
        List<Event> allEventsFromMonth = eventRepository.findAllByStartsAtAfterOrderByTotalTicketsSoldDesc(LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),1,0,0));
        List<Event> top10 = new ArrayList<>();
        for(int i = 0; i < Math.min(10, allEventsFromMonth.size()); i++) top10.add(allEventsFromMonth.get(i));
        if(top10.size() < 1) {
            throw new NotFoundException("Could not find any Event.");
        }
        return top10;
    }

    @Override
    public List<Event> findTop10EventsOfMonthByCategory(String category) {
        List<Event> allEventsFromMonth = eventRepository.findAllByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),1,0,0), category);
        List<Event> top10 = new ArrayList<>();
        for(int i = 0; i < Math.min(10, allEventsFromMonth.size()); i++) top10.add(allEventsFromMonth.get(i));
        return top10;
    }

    @Override
    public List<Event> findAllEvents() {
        List<Event> allEvents = eventRepository.findAll();
        return allEvents;
    }

    @Override
    public Event createNewEvent(Event event){
        LOGGER.debug("Moving Event Entity through Service Layer: " + event);
        event.setEventCode(getNewEventCode());
        validator.validate(event).throwIfViolated();

        //Give shows new EventLocation Entities (copies of existing ones) to make sure they all can have different seating assignments
        for(Show show: event.getShows()) {
            EventLocationOriginal eventLocation = eventLocationRepository.findEventLocationById(show.getEventLocationOriginalId());
            EventLocationCopy eventLocationCopy = new EventLocationCopy(eventLocation);
            eventLocationCopy.setParentId(eventLocation.getId());
            show.setEventLocationCopy(eventLocationCopy);
        }

        return eventRepository.save(event);
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
    public Event findByEventCode(String eventCode) {
        validator.validateExists(eventCode).throwIfViolated();
        Event event = eventRepository.findEventByEventCode(eventCode);
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
        return eventRepository.findEventsByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Event> findEventsAdvanced(String name, EventTypeEnum type, EventCategoryEnum category, LocalDateTime startsAt, LocalDateTime endsAt, Duration showDuration, Integer price) {
        return eventRepository.findEventsByNameContainingIgnoreCaseAndEventTypeAndEventCategoryAndStartsAtIsGreaterThanEqualAndEndsAtIsLessThanEqualAndShowsDurationLessThanEqualAndPricesLessThanEqual(name, type, category, startsAt, endsAt, showDuration, price);
    }
}
