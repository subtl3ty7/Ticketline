package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.datagenerator.EventDataGenerator;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("generateData")
@DependsOn({"EventDataGenerator", "UserDataGenerator"})
public class TicketDataGenerator {
    private final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TicketRepository ticketRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final TicketService ticketService;
    private final UserService userService;
    private final EventRepository eventRepository;
    private final EntityManagerFactory entityManagerFactory;
    private static final int NUMBER_OF_TICKETS_TO_GENERATE = 100; // will generate double the amount
    private int counter = 0;
    private int eventCounter = 0;

    public TicketDataGenerator(TicketRepository ticketRepository, ShowRepository showRepository, SeatRepository seatRepository
                                , EntityManagerFactory entityManagerFactory, TicketService ticketService, UserService userService,
                               EventRepository eventRepository){
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.ticketService = ticketService;
        this.entityManagerFactory = entityManagerFactory;
        this.userService = userService;
        this.eventRepository = eventRepository;
    }

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @PostConstruct
    private void generate() {
        if(ticketRepository.findAll().size() > 0) {
            LOGGER.debug("Ticket Data already generated");
        } else {
            LOGGER.info("Generating Tickets Test Data");
            LocalDateTime start = LocalDateTime.now();
            generateTickets();
            generateMerchTickets();
            LocalDateTime end = LocalDateTime.now();
            float runningTime = Duration.between(start, end).toMillis();
            LOGGER.info("Generating Tickets Test Data took " + runningTime / 1000.0 + " seconds");
        }
    }

    public ArrayList<Show> getAllShows(){
        return showRepository.findAll();
    }

    public ArrayList<Seat> getAllSeats(){
        return seatRepository.findAll();
    }

    public int getShowNumber(){
        if(counter >= 150){
            counter = 1;
        }
        ++counter;
        return counter;
    }

    public int getEventNumber(){
        if(eventCounter >= 49){
            eventCounter = 1;
        }
        ++eventCounter;
        return eventCounter;
    }

    public void generateTickets(){
        boolean bool = true;
        List<AbstractUser> users = userService.loadAllUsers();
        Customer customer0 = (Customer) users.get(0);
        Customer customer1 = (Customer) users.get(1);
        ArrayList<Show> shows = showRepository.findAll();
        ArrayList<Seat> seats = seatRepository.findAll();
        ArrayList<Event> events = eventRepository.findAll();

        if(seats.isEmpty()){
            throw new NotFoundException("No seats in argument list. Needs to contain at least one.");
        }

        if(shows.isEmpty()){
            throw new NotFoundException("No shows in argument list. Needs to contain at least one.");
        }

        // reserve tickets for customer0
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_TICKETS_TO_GENERATE/2; i++) {
            int j = getShowNumber();
            int e = getEventNumber();
            Ticket ticket = Ticket.builder()
                .price(0d)
                .purchaseDate(LocalDateTime.now())
                .seat(seats.get(i))
                .show(shows.get(j))
                .userCode(customer0.getUserCode())
                .event(events.get(e))
                .build();
            tickets.add(ticket);
            randomPurchaseReserve(tickets, bool);
            bool = !bool;
            tickets = new ArrayList<>();
        }

        // reserve tickets for customer1
        for (int i = NUMBER_OF_TICKETS_TO_GENERATE/2; i < NUMBER_OF_TICKETS_TO_GENERATE && i < seats.size(); i++) {
            int j = getShowNumber();
            int e = getEventNumber();
            Ticket ticket = Ticket.builder()
                .price(100.0 - i*3 )
                .purchaseDate(LocalDateTime.now())
                .seat(seats.get(i))
                .show(shows.get(getShowNumber()))
                .userCode(customer1.getUserCode())
                .event(events.get(e))
                .build();
            tickets.add(ticket);
            randomPurchaseReserve(tickets, bool);
            bool = !bool;
            tickets = new ArrayList<>();
        }


    }

    public void generateMerchTickets(){

    }

    private void randomPurchaseReserve(List<Ticket> tickets, boolean bool){
        if(bool) {
            ticketService.reserveTickets(tickets);
        } else {
            ticketService.buyTickets(tickets);
        }
    }


}
