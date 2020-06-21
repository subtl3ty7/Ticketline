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
import java.util.concurrent.ThreadLocalRandom;

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
    private final EventLocationRepository eventLocationRepository;
    private final ArtistService artistService;
    private final EntityManagerFactory entityManagerFactory;
    private final Resources resources;

    private static final boolean ignoreNumberOfEventLocations = true;
    private static final int numberOfEventLocations = 27;
    private static final int numberOfArtists = 50;
    private static final int numberOfEvents = 200;
    private static final int numberOfShowsPerEvent = 4;
    private static final int eventDurationInHours = 2;

    @Autowired
    public EventDataGenerator(SectionRepository sectionRepository,
                              SeatRepository seatRepository, ShowRepository showRepository,
                              EventService eventService,
                              EventLocationService eventLocationService,
                              EntityManagerFactory entityManagerFactory,
                              ArtistRepository artistRepository,
                              ArtistService artistService,
                              Resources resources,
                              EventLocationRepository eventLocationRepository
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
        this.eventLocationRepository = eventLocationRepository;
    }

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @PostConstruct
    public void generate() {
        if(seatRepository.findAll().size() > 0) {
            LOGGER.info("Event Test Data already generated");
        } else {
            generateEvents();

        }
    }

    private List<Event> generateEvents() {
        LOGGER.info("Generating EventLocation Test Data...");
        LocalDateTime start = LocalDateTime.now();
        List<EventLocation> eventLocations = generateEventLocations();

        if(eventLocations.isEmpty()) {
            throw new NotFoundException("No Event Locations in argument list. Needs to contain at least one.");
        }
        LocalDateTime end = LocalDateTime.now();
        float runningTime_EventLocations = Duration.between(start, end).toMillis();
        LOGGER.info("Generating EventLocation Test Data (" + numberOfEventLocations + " Entities) took " + runningTime_EventLocations/1000.0 + " seconds");

        /*
        LOGGER.info("Generating Artist Test Data...");
        start = LocalDateTime.now();
        generateArtists();
        List<Artist> artists = artistService.getAllArtists();

        if(artists.isEmpty()) {
            throw new NotFoundException("No Artists in argument list. Needs to contain at least one.");
        }
        end = LocalDateTime.now();
        float runningTime_Artists = Duration.between(start, end).toMillis();
        LOGGER.info("Generating Artist Test Data (" + numberOfArtists + " Entities) took " + runningTime_Artists/1000.0 + " seconds");
         */

        LOGGER.info("Generating Event Test Data...");
        start = LocalDateTime.now();
        List<Event> events = new ArrayList<>();

        //String[] eventNames = resources.getText("text/event_title.txt").split("\n");
        EventTypeEnum[] types = EventTypeEnum.values();
        EventCategoryEnum[] categories = EventCategoryEnum.values();

        //checkResources();
        List<Event> dataList = Arrays.asList(resources.getObjectFromJson("entities/events.json", Event[].class));

        for(int i=0; i<numberOfEvents; i++) {
            int dataIndex = i % dataList.size();
            Event data = dataList.get(dataIndex);
            String imgName = data.getPhoto().getImage();
            String eventLocationName = data.getShows().get(0).getEventLocation().getName();

            //find an eventLocation with the name that was given in "data"
            List<EventLocation> list = eventLocationRepository.findByName(eventLocationName);
            if(list.isEmpty()) {
                throw new RuntimeException("Can't find EventLocation with name '" + eventLocationName + "'");
            }
            EventLocation eventLocation = list.get(0);

            Event event = Event.builder()
                .description(data.getDescription())
                .name(data.getName())
                .photo(resources.getImageEncoded(imgName))
                .totalTicketsSold(0)
                .shows(generateShows(eventLocation, data.getType(), data.getCategory(), "Event " + i))
                .artists(data.getArtists())
                .type(data.getType())
                .category(data.getCategory())
                .duration(Duration.ofHours(eventDurationInHours))
                .build();
            event = eventService.createNewEvent(event);
            events.add(event);
        }
        end = LocalDateTime.now();
        float runningTime_Events = Duration.between(start, end).toMillis();
        LOGGER.info("Generating Event Test Data (" + numberOfEvents + " Entities) took " + runningTime_Events/1000.0 + " seconds");

        return events;
    }

    private List<Show> generateShows(EventLocation eventLocation, EventTypeEnum typeEnum, EventCategoryEnum categoryEnum, String eventName) {
        List<Show> shows = new ArrayList<>();
        for(int i = 0; i< numberOfShowsPerEvent; i++) {
            //List<EventLocation> location = new ArrayList<>();
            //location.add(new EventLocation(eventLocation));
            LocalDateTime start = LocalDateTime.now().plusDays((int)(Math.random() * 700)).plusHours((int)(Math.random() * 24)).plusMinutes((int)(Math.random() * 60));
            LocalDateTime end = start.plusHours((int)(Math.random() * 24));
            Show show = Show.builder()
                .startsAt(start)
                .endsAt(end)
                .ticketsAvailable(eventLocation.getCapacity())
                .ticketsSold(0)
                .eventLocation(eventLocation)
                .eventType(typeEnum)
                .eventCategory(categoryEnum)
                .eventName(eventName)
                //.photo(resources.getImageEncoded(imgName))
                .description(resources.getText("text/event_text.txt"))
                .duration(Duration.ofHours(eventDurationInHours))
                .price(Math.random()*53)
                .build();
            shows.add(show);
        }

        return shows;
    }


    private List<EventLocation> generateEventLocations() {
        List<EventLocation> dataList = Arrays.asList(resources.getObjectFromJson("entities/eventLocations.json", EventLocation[].class));
        int numEventLocations = ignoreNumberOfEventLocations ? dataList.size() : numberOfEventLocations;

        List<EventLocation> eventLocations = new ArrayList<>();
        for(int i=0; i<numEventLocations; i++) {
            int dataIndex = i % dataList.size();
            EventLocation data = dataList.get(dataIndex);
            int randomNum = ThreadLocalRandom.current().nextInt(3, 6 + 1);
            List<Section> sections = generateSections(randomNum);

            EventLocation eventLocation = EventLocation.builder()
                .name(data.getName())
                .city(data.getCity())
                .country(data.getCountry())
                .plz(data.getPlz())
                .street(data.getStreet())
                .sections(sections)
                .capacity(getCapacitySum(sections))
                .build();

            eventLocations.add(eventLocationService.save(eventLocation));
        }
        return eventLocations;
    }

    private List<Artist> generateArtists() {
        List<Artist> artists = new ArrayList<>();
        if (artistRepository.findAllByOrderByLastNameAscFirstNameAsc().size() > 0) {
            LOGGER.debug("artist already generated");
        } else {
            for (int i = 0; i < numberOfArtists; i++) {
                Artist artist = Artist.builder()
                    .firstName("Künstler")
                    .lastName(((char) (65 + (i%26))) + "")
                    .build();
                LOGGER.debug("saving artist {}", artist);
                artistRepository.save(artist);
            }
        }
        return artists;
    }

    private List<Section> generateSections(int layout) {
        String[] labels = {};
        switch (layout) {
            case 3: labels = new String[]{
                "A",
                "B",
                "C"
            };
                break;
            case 4: labels = new String[]{
                "A",
                "B",
                "C",
                "D"
            };
                break;
            case 5: labels = new String[]{
                "A",
                "B",
                "C",
                "D",
                "E"
            };
                break;
            case 6: labels = new String[]{
                        "A",
                        "B",
                        "C",
                        "D",
                        "E",
                        "F"
                    };
                    break;
            default: break;
        }

        List<Section> sections = new ArrayList<>();
        int labelNumber= 0;
        boolean midSection = false;
        for(String label: labels) {
            if(layout == 3) {
                midSection = labelNumber == 1;
            }
            if(layout == 4) {
                midSection = (labelNumber == 1) || (labelNumber == 3);
            }
            if(layout == 5) {
                midSection = false;
            }
            if(layout == 6) {
                midSection = (labelNumber == 1) || (labelNumber == 4);
            }

            List<Seat> seats = generateSeats(midSection);
            Section section = Section.builder()
                .name(label)
                .description("Some Description")
                .priceCategory("Average")
                .price(Math.random() * 50)
                .seats(seats)
                .capacity(seats.size())
                .build();

            sections.add(section);
            labelNumber++;
        }

        return sections;
    }

    private List<Seat> generateSeats(boolean midSection) {
        String[] columns = new String[]{
            "1",
            "2",
            "3",
            "4",
            "5"
        };
        String[] rows;
        if (midSection) {
            rows = new String[]{
                "A",
                "B",
                "C"
            };
        } else {
            rows = new String[] {
                "A",
                "B",
                "C",
                "D",
                "E"
            };
        }

        List<Seat> seats = new ArrayList<>();
        for(String i: rows) {
            for(String j: columns) {
                Seat seat = Seat.builder()
                    .seatRow(i)
                    .seatColumn(j)
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
