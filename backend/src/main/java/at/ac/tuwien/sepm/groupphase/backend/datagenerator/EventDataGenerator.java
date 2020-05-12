package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Profile("generateData")
@Component
public class EventDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SectionRepository sectionRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final EventService eventService;
    private final EventLocationService eventLocationService;
    private final EntityManagerFactory entityManagerFactory;
    private final ResourceLoader resourceLoader;


    private final static int numberOfEventLocations = 5;
    private static final int numberOfEvents = 15;

    public EventDataGenerator(SectionRepository sectionRepository,
                              SeatRepository seatRepository, ShowRepository showRepository,
                              EventService eventService,
                              EventLocationService eventLocationService,
                              EntityManagerFactory entityManagerFactory,
                              ResourceLoader resourceLoader
    ) {
        this.sectionRepository = sectionRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.eventService = eventService;
        this.eventLocationService = eventLocationService;
        this.entityManagerFactory = entityManagerFactory;
        this.resourceLoader = resourceLoader;
    }

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @PostConstruct
    private void generate() {
        if(seatRepository.findAll().size() > 0) {
            LOGGER.debug("Event Test Data already generated");
        } else {
            generateEventLocations(true);
            List<EventLocation> eventLocations = eventLocationService.getAllEventLocations();
            generateEvents(eventLocations);
        }
    }

    private void updateEvent(Event event) {
        Session session = getSession();
        session.beginTransaction();
        Event eventNew = eventService.findByEventCode(event.getEventCode());
        eventNew.getShows().remove(2);
        eventNew.getShows().remove(1);
        eventNew.getShows().add(Show.builder().startsAt(LocalDateTime.now()).endsAt(LocalDateTime.now()).build());
        eventService.createNewEvent(eventNew);
        session.getTransaction().commit();
        int i=0;
    }

    private void deleteEvents(List<Event> events) {
        for(Event event: events) {
            eventService.deletebyEventCode(event.getEventCode());
        }
    }

    private List<Event> generateEvents(List <EventLocation> eventLocations) {
        LOGGER.info("Generating Event Test Data");

        if(eventLocations.isEmpty()) {
            throw new NotFoundException("No Event Locations in argument list. Needs to contain at least one.");
        }

        List<Event> events = new ArrayList<>();
        for(int i=0; i<numberOfEvents; i++) {
            int eventLocationIndex = i;
            if(eventLocationIndex >= eventLocations.size()) {
                eventLocationIndex = eventLocations.size()-1;
            }

            String imgName = "";
            if(i<4) {
                imgName = "Luffy.txt";
            } else {
                imgName = "Gigi.txt";
            }

            Event event = Event.builder()
                .artists(List.of("Artist1", "Artist2", "Artist3"))
                .category("Talk")
                .description("Interesting debate!")
                .startsAt(LocalDateTime.now())
                .endsAt(LocalDateTime.now())
                .eventCode("E1234" + i)
                .name("Talk Event")
                .photo(getImage(imgName))
                .prices(List.of(1,2,3))
                .totalTicketsSold(5)
                .type("Of the cool type")
                .shows(generateShows(eventLocations.get(eventLocationIndex)))
                .build();
            event = eventService.createNewEvent(event);
            events.add(event);
        }

        return events;
    }

    private List<Show> generateShows(EventLocation eventLocation) {
        LOGGER.info("Generating Show Test Data");
        int numberOfShows = 2;

        List<Show> shows = new ArrayList<>();
        for(int i=0; i<numberOfShows; i++) {
            //List<EventLocation> location = new ArrayList<>();
            //location.add(new EventLocation(eventLocation));
            Show show = Show.builder()
                .startsAt(LocalDateTime.now())
                .endsAt(LocalDateTime.now())
                .ticketsAvailable(1000)
                .ticketsSold(300)
                .eventLocation(List.of(eventLocation))
                .build();
            shows.add(show);
        }

        return shows;
    }


    private List<EventLocation> generateEventLocations(boolean doSave) {
        LOGGER.info("Generating Event Location Test Data");

        List<EventLocation> eventLocations = new ArrayList<>();
        for(int i=0; i<numberOfEventLocations; i++) {
            List<Section> sections = generateSections();
            EventLocation eventLocation = EventLocation.builder()
                .eventLocationName("Stephansplatz " + i)
                .city("Vienna")
                .country("Austria")
                .plz("1010")
                .street("Stephansplatz " + i)
                .sections(sections)
                .capacity(getCapacitySum(sections))
                .build();
            eventLocations.add(eventLocation);
            if (doSave) {
                eventLocationService.save(eventLocation);
            }
        }
        return eventLocations;
    }


    private List<Section> generateSections() {
        LOGGER.info("Generating Section Test Data");
        int numberOfSections = 4;

        List<Section> sections = new ArrayList<>();
        for(int i=0; i<numberOfSections; i++) {
            List<Seat> seats = generateSeats();
            Section section = Section.builder()
                .sectionName("Section Bla" + i)
                .sectionDescription("BlaDescription")
                .seats(seats)
                .capacity(seats.size()+i)
                .build();

            sections.add(section);
        }

        return sections;
    }

    private List<Seat> generateSeats() {
        LOGGER.info("Generating Seat Test Data");
        String[] columns = new String[]{
            "1",
            "2",
            "3"
        };
        String[] rows = new String[] {
            "A",
            "B",
            "C"
        };

        List<Seat> seats = new ArrayList<>();
        for(String i: rows) {
            for(String j: columns) {
                Seat seat = Seat.builder()
                    .seatRow(i)
                    .seatColumn(j)
                    .isFree(true)
                    .build();
                seats.add(seat);
            }
        }

        return seats;
    }

    private int getCapacitySum(List<Section> sections) {
        int sum = 0;
        for(Section section: sections) {
            sum += section.getCapacity();
        }
        return sum;
    }

    private String getImage(String imgName) {
        try {
            InputStream inputStream = resourceLoader.getResource("classpath:" + imgName).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String contents = reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
            return contents;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load Image File for EventDataGenerator.", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Couldn't load Image File for EventDataGenerator.", e);
        }
    }
}
