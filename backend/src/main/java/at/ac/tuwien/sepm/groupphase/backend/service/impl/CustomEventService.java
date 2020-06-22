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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        List<Event> top10 = eventRepository.findTop10ByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),1,0,0), EventCategoryEnum.valueOf(category));
        return top10;
    }

    @Override
    public List<Event> findAllEvents(int size) {
        int page = calculateNumberOfPage(size);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Event> eventsPage = eventRepository.findAll(pageRequest);
        return eventsPage.toList();
    }


    private int calculateNumberOfPage(int size) {
        int result = 0;
        if (size != 0) {
            result = size / 10;
        }
        return result;
    }

    @Transactional
    @Override
    public Event createNewEvent(Event event){
        event.setEventCode(getNewEventCode());
        //set MIN MAX to allow comparison
        event.setStartsAt(LocalDateTime.MAX);
        event.setEndsAt(LocalDateTime.MIN);
        List<Double> prices = new ArrayList<>();
        prices.add(Double.MAX_VALUE);
        event.setPrices(prices);

        //replace artists with artists from repository
        List<Artist> newArtistList = new ArrayList<>();
        for (Artist a : event.getArtists()) {
            if(a.getLastName() == null) {
                a.setLastName("");
            }
            List<Artist> artists = artistRepository.findArtistsByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            if(artists.isEmpty()) {
                Artist artist = artistRepository.save(a);
                //replace with repository object
                newArtistList.add(artist);
            } else {
                //replace with repository object
                newArtistList.add(artists.get(0));
            }
        }
        event.setArtists(newArtistList);

        for(Show show: event.getShows()) {
            //Give shows new EventLocation Entities (copies of existing ones) to make sure they all can have different seating assignments
            EventLocation eventLocation = eventLocationRepository.findEventLocationById(show.getEventLocation().getId());
            show.setEventLocation(eventLocation);

            show.setPhoto(event.getPhoto());
            show.setEventName(event.getName());
            show.setDescription(event.getDescription());
            show.setDuration(Duration.between(show.getStartsAt(), show.getEndsAt()));
            show.setTicketsAvailable(eventLocation.getCapacity());
            //round price to two decimals
            show.setPrice(Math.round(show.getPrice()*100.0)/100.0);

            //set category and type for event
            show.setEventCategory(event.getCategory());
            show.setEventType(event.getType());

            //set earliest startsAt of all shows and latest endsAt of all shows for the event
            LocalDateTime startsAt = show.getStartsAt().isBefore(event.getStartsAt()) ? show.getStartsAt() : event.getStartsAt();
            LocalDateTime endsAt = show.getEndsAt().isAfter(event.getEndsAt()) ? show.getEndsAt() : event.getEndsAt();
            event.setStartsAt(startsAt);
            event.setEndsAt(endsAt);

            //set lowest price
            event.getPrices().set(0, Math.min(event.getPrices().get(0), getMinPrice(show)));
        }
        event.setDuration(Duration.between(event.getStartsAt(), event.getEndsAt()));

        validator.validate(event).throwIfViolated();
        Event event1 = eventRepository.save(event);

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
            Hibernate.initialize(show.getEventLocation());
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
    public List<Event> findEventsByArtistId(Long artistId, int size) {
        validator.validateExists(artistId).throwIfViolated();
        Artist artist = artistRepository.findArtistById(artistId);
        int page = calculateNumberOfPage(size);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Event> eventsPage = eventRepository.findEventsByArtistsContaining(artist, pageRequest);
        return eventsPage.toList();

    }

    @Override
    public List<Event> findEventsByName(String name, int size) {
        int page = calculateNumberOfPage(size);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Event> eventsPage = eventRepository.findEventsByNameContainingIgnoreCase(name, pageRequest);
        return eventsPage.toList();
    }

    @Override
    public List<Event> findEventsAdvanced(String name, Integer type, Integer category, LocalDateTime startsAt, LocalDateTime endsAt, Duration showDuration, int size) {
        int page = calculateNumberOfPage(size);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Event> eventsPage = eventRepository.findEventsByNameContainingIgnoreCaseAndTypeContainingAndCategoryContainingAndStartsAtIsGreaterThanEqualAndEndsAtIsLessThanEqualAndShowsDurationLessThanEqual(name, type, category, startsAt, endsAt, showDuration, pageRequest);
        return eventsPage.toList();
    }

    @Override
    public List<Event> findSimpleEventsByParam(String eventCode, String name, LocalDateTime startRange, LocalDateTime endRange, int size) {
        int page = calculateNumberOfPage(size);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Event> eventsPage =  eventRepository.findAllByEventCodeContainingIgnoreCaseAndNameContainingIgnoreCaseAndStartsAtBetween(eventCode, name, startRange, endRange, pageRequest);
        return eventsPage.toList();
    }

    private Double getMinPrice(Show show) {
        double min = Double.MAX_VALUE;
        for(Section section: show.getEventLocation().getSections()) {
            min = Math.min(min, show.getPrice() + section.getPrice());
        }
        return min;
    }

    @Transactional
    @Override
    public List<Event> findNumberOfEvents(int number) {
        Pageable pageable = PageRequest.of(0, number);
        List<Event> events = eventRepository.findAllBy(pageable);
        for(Event event: events) {
            Hibernate.initialize(event.getShows());
        }
        return events;
    }
}
