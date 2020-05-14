package at.ac.tuwien.sepm.groupphase.backend.basetest;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.hibernate.mapping.Table;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TestData {

    String BASE_URI = "/api/v1";
    String MESSAGE_BASE_URI = BASE_URI + "/messages";
    Long ID = 1L;
    String TEST_NEWS_TITLE = "Title";
    String TEST_NEWS_SUMMARY = "Summary";
    String TEST_NEWS_TEXT = "TestMessageText";
    LocalDateTime TEST_NEWS_PUBLISHED_AT =
        LocalDateTime.of(2019, 11, 13, 12, 15, 0, 0);


    String USER_BASE_URI = BASE_URI + "/users";
    String CUSTOMER_BASE_URI = USER_BASE_URI + "/customers";
    String ADMIN_BASE_URI = USER_BASE_URI + "/administrators";
    String ADMIN_USER = "admin@email.com";
    List<String> ADMIN_ROLES = new ArrayList<>() {
        {
            add("ROLE_ADMIN");
            add("ROLE_USER");
        }
    };

    String DEFAULT_USER = "e0@customer.com";
    List<String> USER_ROLES = new ArrayList<>() {
        {
            add("ROLE_USER");
        }
    };
    String USER_CODE = "code11";
    String FNAME = "name";
    String LNAME = "lastname";
    String PASS = new BCryptPasswordEncoder().encode("Password0");
    LocalDateTime BIRTHDAY = LocalDateTime.of(1998, 5,15,12,0);
    LocalDateTime CRE = LocalDateTime.now();
    LocalDateTime UPD = LocalDateTime.now();
    Long POINTS = 0L;

    String EVENT_BASE_URI = BASE_URI + "/events";
    String EVENT_TOP10 = EVENT_BASE_URI + "/top10";
    String NAME = "event";
    String DESC = "description";
    String CAT = "concert";
    String TYP = "out";
    String PHOTO = "no photo";
    LocalDateTime START =
        LocalDateTime.of(2020, 11, 13, 12, 15, 0, 0);
    LocalDateTime END =
        LocalDateTime.of(2020, 11, 13, 18, 15, 0, 0);
    List<Integer> PRICES = List.of(30, 40);
    List<String> ARTISTS = List.of("Artist1", "Artist2");
    List<Show> SHOWS = new ArrayList<>() {
        {
            add(Show.builder()
                .id(ID).eventCode(USER_CODE).startsAt(START).endsAt(END).ticketsSold(TOTAL).ticketsAvailable(TOTAL).eventLocation(LOCATIONS).build());
        }
    };
    int TOTAL = 500;

    String LOCATION_BASE_URI = BASE_URI + "/eventLocations/all";
    String STREET = "street";
    String COUNTRY = "country";
    String CITY = "city";
    String PLZ = "1000";
    List<Section> SECTIONS = new ArrayList<>() {
        {
            add(Section.builder()
                .id(ID).sectionName(FNAME).eventLocationId(ID).sectionDescription(DESC).capacity(TOTAL).seats(SEATS).build());
        }
    };

    List<EventLocation> LOCATIONS = new ArrayList<>() {
        {
            add(EventLocation.builder()
                .id(ID).showId(ID).eventLocationName(FNAME).eventLocationDescription(DESC).street(STREET).city(CITY).country(COUNTRY).plz(PLZ).sections(SECTIONS).capacity(TOTAL).build());
        }
    };
    String COLUMN = "A";
    String ROW = "1";
    List<Seat> SEATS = new ArrayList<>() {
        {
            add(Seat.builder().id(ID).sectionId(ID).isFree(true).seatColumn(COLUMN).seatRow(ROW).build());
        }
    };

}
