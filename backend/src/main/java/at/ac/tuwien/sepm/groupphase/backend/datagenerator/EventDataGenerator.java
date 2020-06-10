package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.util.Resources;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Profile("generateData")
@Component("EventDataGenerator")
public class EventDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SectionRepository sectionRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final ArtistRepository artistRepository;
    private final EventService eventService;
    private final EventLocationService eventLocationService;
    private final ArtistService artistService;
    private final EntityManagerFactory entityManagerFactory;
    private final Resources resources;


    private final static int numberOfEventLocations = 25;
    public static final int numberOfEvents = 25;
    private static final int numberOfArtists = 5;
    private static final int eventDurationInHours = 2;
    public static final int numberOfShowsPerEvent = 2;


    @Autowired
    public EventDataGenerator(SectionRepository sectionRepository,
                              SeatRepository seatRepository, ShowRepository showRepository,
                              EventService eventService,
                              EventLocationService eventLocationService,
                              EntityManagerFactory entityManagerFactory,
                              ArtistRepository artistRepository, ArtistService artistService, Resources resources
    ) {
        this.sectionRepository = sectionRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.eventService = eventService;
        this.eventLocationService = eventLocationService;
        this.entityManagerFactory = entityManagerFactory;
        this.resources = resources;
        this.artistRepository = artistRepository;
        this.artistService = artistService;
    }

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @PostConstruct
    public void generate() {
        if(seatRepository.findAll().size() > 0) {
            LOGGER.info("Event Test Data already generated");
        } else {
            LOGGER.info("Generating Event Test Data");
            LocalDateTime start = LocalDateTime.now();
            generateEvents();
            LocalDateTime end = LocalDateTime.now();
            float runningTime = Duration.between(start, end).toMillis();
            LOGGER.info("Generating Event Test Data took " + runningTime/1000.0 + " seconds");

        }
    }

    private List<Event> generateEvents() {
        generateEventLocations(true);
        List<EventLocationOriginal> eventLocations = eventLocationService.getAllEventLocations();

        if(eventLocations.isEmpty()) {
            throw new NotFoundException("No Event Locations in argument list. Needs to contain at least one.");
        }

        generateArtists();
        List<Artist> artists = artistService.getAllArtists();

        if(artists.isEmpty()) {
            throw new NotFoundException("No Artists in argument list. Needs to contain at least one.");
        }

        List<Event> events = new ArrayList<>();
        for(int i=0; i<numberOfEvents; i++) {
            int eventLocationIndex = i;
            if(eventLocationIndex >= eventLocations.size()) {
                eventLocationIndex = eventLocations.size()-1;
            }

            String imgName = "event_img" + i%15 + ".jpg";

            List<Artist> addedArtists = new ArrayList<>();
            int artistIndex = i;
            if (artistIndex >= artists.size()) {
                artistIndex = artists.size()-1;
            }
            addedArtists.add(artists.get(artistIndex));

            EventTypeEnum[] types = EventTypeEnum.values();
            EventCategoryEnum[] categories = EventCategoryEnum.values();
            int typeIndex = i % EventTypeEnum.values().length;
            int categoryIndex = i % EventCategoryEnum.values().length;

            Event event = Event.builder()
                .description(resources.getText("event_text.txt"))
                .startsAt(LocalDateTime.now())
                .endsAt(LocalDateTime.now().plusHours(eventDurationInHours))
                .eventCode("E1234" + i)
                .name("Event " + i)
                .photo(resources.getImageEncoded(imgName))
                .prices(List.of(1,2,3))
                .totalTicketsSold(5*i*i*i)
                .shows(generateShows(eventLocations.get(eventLocationIndex), EventTypeEnum.MUSIC, EventCategoryEnum.HIPHOP, "Event " + i, imgName))
                .artists(addedArtists)
                .type(types[typeIndex].toString())
                .eventType(types[typeIndex])
                .category(categories[categoryIndex].toString())
                .eventCategory(categories[categoryIndex])
                .duration(Duration.ofHours(eventDurationInHours))
                .build();
            event = eventService.createNewEvent(event);
            events.add(event);
        }

        return events;
    }

    private List<Show> generateShows(EventLocation eventLocation, EventTypeEnum typeEnum, EventCategoryEnum categoryEnum, String eventName, String imgName) {

        List<Show> shows = new ArrayList<>();
        for(int i=0; i<numberOfShowsPerEvent; i++) {
            //List<EventLocation> location = new ArrayList<>();
            //location.add(new EventLocation(eventLocation));
            LocalDateTime start = LocalDateTime.now();
            LocalDateTime end = start.plusHours(eventDurationInHours);
            Show show = Show.builder()
                .startsAt(start)
                .endsAt(end)
                .ticketsAvailable(1000)
                .ticketsSold(300)
                .eventLocationOriginalId(eventLocation.getId())
                .eventType(typeEnum)
                .eventCategory(categoryEnum)
                .eventName(eventName)
                .photo(resources.getImageEncoded(imgName))
                .description(resources.getText("event_text.txt"))
                .duration(Duration.ofHours(eventDurationInHours))
                .price(50)
                .build();
            shows.add(show);
        }

        return shows;
    }


    private List<EventLocationOriginal> generateEventLocations(boolean doSave) {

        List<EventLocationOriginal> eventLocations = new ArrayList<>();
        for(int i=0; i< numberOfEventLocations; i++) {
            List<Section> sections = generateSections();
            EventLocationOriginal eventLocation = EventLocationOriginal.builder()
                .name("Stephansplatz " + i)
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

    private List<Artist> generateArtists() {
        List<Artist> artists = new ArrayList<>();
        if (artistRepository.findAllByOrderByLastNameAscFirstNameAsc().size() > 0) {
            LOGGER.debug("artist already generated");
        } else {
            LOGGER.debug("generating {} message entries", numberOfArtists);
            for (int i = 0; i < numberOfArtists; i++) {
                Artist artist = Artist.ArtistBuilder.anArtist()
                    .withFirstName(((char) (65 + (i%26))) + "test")
                    .withLastName("Person")
                    .build();
                LOGGER.debug("saving artist {}", artist);
                artistRepository.save(artist);
            }
        }
        return artists;
    }

    private List<Section> generateSections() {
        String[] labels = new String[]{
            "A",
            "B",
            "C",
            "D",
            "E"
        };

        List<Section> sections = new ArrayList<>();
        for(String label: labels) {
            List<Seat> seats = generateSeats();
            Section section = Section.builder()
                .name(label)
                .description("Some Description")
                .priceCategory("Low")
                .price(9.99)
                .seats(seats)
                .capacity(seats.size())
                .build();

            sections.add(section);
        }

        return sections;
    }

    private List<Seat> generateSeats() {
        String[] columns = new String[]{
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9"
        };
        String[] rows = new String[] {
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H"
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

}
