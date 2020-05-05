package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Profile("generateData")
@Component
public class EventDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SectionRepository sectionRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;
    private final EntityManagerFactory entityManagerFactory;

    public EventDataGenerator(SectionRepository sectionRepository,
                              SeatRepository seatRepository, ShowRepository showRepository,
                              EventRepository eventRepository,
                              EventLocationRepository eventLocationRepository,
                              EntityManagerFactory entityManagerFactory
    ) {
        this.sectionRepository = sectionRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.eventRepository = eventRepository;
        this.eventLocationRepository = eventLocationRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @PostConstruct
    private void generate() {
        if(seatRepository.findAll().size() > 0) {
            LOGGER.debug("Event Test Data already generated");
        } else {
            List<Event> events = generateEvents();
            // run this to test if the events and all their children (shows, eventlocations, sections, seats) get deleted
            // deleteEvents(events);
        }
    }

    private void deleteEvents(List<Event> events) {
        for(Event event: events) {
            eventRepository.deleteById(event.getId());
        }
    }

    private List<Event> generateEvents() {
        LOGGER.info("Generating Event Test Data");
        int numberOfEvents = 3;

        List<Event> events = new ArrayList<>();
        for(int i=0; i<numberOfEvents; i++) {
            Event event = Event.builder()
                .artists(List.of("Artist1", "Artist2", "Artist3"))
                .category("Talk")
                .description("Interesting debate!")
                .startsAt(LocalDateTime.now())
                .endsAt(LocalDateTime.now())
                .eventCode("E1234" + i)
                .name("Talk Event")
                .photo("no")
                .prices(List.of(1,2,3))
                .totalTicketsSold(5)
                .type("Of the cool type")
                .shows(generateShows())
                .build();
            event = eventRepository.save(event);
            events.add(event);
        }

        return events;
    }

    private List<Show> generateShows() {
        LOGGER.info("Generating Show Test Data");
        int numberOfShows = 3;

        List<Show> shows = new ArrayList<>();
        for(int i=0; i<numberOfShows; i++) {
            Show show = Show.builder()
                .startsAt(LocalDateTime.now())
                .endsAt(LocalDateTime.now())
                .ticketsAvailable(1000)
                .ticketsSold(300)
                .eventLocation(List.of(generateEventLocation()))
                .build();
            shows.add(show);
        }

        return shows;
    }

    private EventLocation generateEventLocation() {
        LOGGER.info("Generating Event Location Test Data");

        EventLocation eventLocation = EventLocation.builder()
            .name("Stephansplatz")
            .city("Vienna")
            .country("Austria")
            .plz("1010")
            .street("Stephansplatz 1")
            .sections(generateSections())
            .build();

        EventLocation eventLocationPass = eventLocation.toBuilder().sections(generateSections()).build();
        eventLocationRepository.save(eventLocation);
        return eventLocationPass;
    }

    private List<Section> generateSections() {
        LOGGER.info("Generating Section Test Data");
        int numberOfSections = 2;

        List<Section> sections = new ArrayList<>();
        for(int i=0; i<numberOfSections; i++) {
            Section section = Section.builder()
                .name("Section Bla")
                .description("BlaDescription")
                .seats(generateSeats())
                .build();

            sections.add(section);
        }

        return sections;
    }

    private List<Seat> generateSeats() {
        LOGGER.info("Generating Seat Test Data");
        char[] columns = new char[]{
            '1',
            '2',
            '3'
        };
        char[] rows = new char[] {
            'A',
            'B',
            'C'
        };

        List<Seat> seats = new ArrayList<>();
        for(char i: rows) {
            for(char j: columns) {
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
}
