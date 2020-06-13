package at.ac.tuwien.sepm.groupphase.backend.basetest;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import org.hibernate.mapping.Table;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Duration;
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

    String DEFAULT_USER = "user@email.com";
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
    EventCategoryEnum CAT = EventCategoryEnum.ROCK;
    EventTypeEnum TYP = EventTypeEnum.MUSIC;
    EventCategoryEnum CAT1 = EventCategoryEnum.HIPHOP;
    EventTypeEnum TYP1 = EventTypeEnum.MUSIC;
    Image PHOTO = new Image(null, "no photo");
    LocalDateTime START =
        LocalDateTime.of(2020, 11, 13, 12, 15, 0, 0);
    LocalDateTime END =
        LocalDateTime.of(2020, 11, 13, 18, 15, 0, 0);
    Duration DURATION = Duration.between(START, END);
    List<Integer> PRICES = List.of(30, 40);
    List<Show> SHOWS = new ArrayList<>() {
        {
            add(Show.builder()
                .id(ID).eventCode(USER_CODE).startsAt(START).endsAt(END).ticketsSold(TOTAL).ticketsAvailable(TOTAL)
                .eventCategory(CAT1).eventType(TYP1).description(DESC).photo(PHOTO).build());
        }
    };
    int TOTAL = 500;

    Double PRICE = 50.0;
    String LOCATION_BASE_URI = BASE_URI + "/eventLocations/all";
    String STREET = "street";
    String COUNTRY = "country";
    String CITY = "city";
    String PLZ = "1000";
    List<Section> SECTIONS = new ArrayList<>() {
        {
            add(Section.builder()
                .id(ID).name(FNAME).eventLocationId(ID).description(DESC).capacity(TOTAL).seats(SEATS).build());
        }
    };
    Long LOCATIONID = 1L;
    String COLUMN = "A";
    String ROW = "1";
    List<Seat> SEATS = new ArrayList<>() {
        {
            add(Seat.builder().id(ID).sectionId(ID).seatColumn(COLUMN).seatRow(ROW).build());
        }
    };

    String USER_CODE_TICKET = "code00";
    String EMAIL_TICKET = "ticket@email.com";
    Customer USER_TICKET = Customer.CustomerBuilder.aCustomer().withId(ID).withUserCode(USER_CODE_TICKET).withFirstName(FNAME)
        .withLastName(LNAME).withEmail(EMAIL_TICKET).withPassword(PASS).withBirthday(BIRTHDAY).withCreatedAt(CRE)
        .withUpdatedAt(UPD).withIsBlocked(false).withIsLogged(false).withPoints(POINTS).build();

    String TICKETS_BASE_URI = BASE_URI + "/tickets";

    String TYP_I = "purchase";
    InvoiceCategoryEnum CAT_I = InvoiceCategoryEnum.MERCHANDISE_INVOICE;
    String PAY = "card";
    LocalDateTime GENERATE = LocalDateTime.of(2020,6,1,10,0,0);
    String NUM = "000001";
    List<Ticket> TICKETS = new ArrayList<>(){
        {
            add(Ticket.builder().ticketId(ID).ticketCode(USER_CODE).isPurchased(false).isReserved(false)
                .purchaseDate(START).price(PRICE).userCode(USER_CODE).seat(SEATS.get(0)).show(SHOWS.get(0)).build());
        }
    };

    String ARTIST_BASE_URI = BASE_URI + "/artists";
    String NEWS_BASE_URI = BASE_URI + "/news";

    Event EVENT = Event.builder().id(ID).eventCode(USER_CODE).name(NAME).description(DESC).category(CAT).type(TYP)
        .startsAt(START).endsAt(END).prices(PRICES).totalTicketsSold(TOTAL).shows(SHOWS).build();

}
