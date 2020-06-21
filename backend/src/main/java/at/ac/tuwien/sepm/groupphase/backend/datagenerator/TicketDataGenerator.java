package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.ShowService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final EventService eventService;
    private final EntityManagerFactory entityManagerFactory;
    private final ShowService showService;
    private static final int NUMBER_OF_TICKETS_TO_GENERATE = 1000;
    private static final int NUMBER_OF_CUSTOMERS = 5; // number of customers to generate tickets for
    private static final int NUMBER_OF_EVENTS = 10; // number of shows to generate tickets for

    @Autowired
    public TicketDataGenerator(TicketRepository ticketRepository, ShowRepository showRepository, SeatRepository seatRepository
                                , EntityManagerFactory entityManagerFactory, TicketService ticketService, UserService userService,
                               EventRepository eventRepository, ShowService showService, EventService eventService){
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.ticketService = ticketService;
        this.entityManagerFactory = entityManagerFactory;
        this.userService = userService;
        this.eventRepository = eventRepository;
        this.showService = showService;
        this.eventService = eventService;
    }

    @PostConstruct
    private void generate() {
        if(ticketRepository.findAll().size() > 0) {
            LOGGER.debug("Ticket Data already generated");
        } else {
            LOGGER.info("Generating Tickets Test Data...");
            LocalDateTime start = LocalDateTime.now();
            generateTickets();
            generateMerchTickets();
            LocalDateTime end = LocalDateTime.now();
            float runningTime = Duration.between(start, end).toMillis();
            LOGGER.info("Generating Tickets Test Data (" + NUMBER_OF_TICKETS_TO_GENERATE + " Entities) took " + runningTime / 1000.0 + " seconds");
        }
    }

    public void generateTickets(){
        List<AbstractUser> users = userService.loadAllUsers();
        List<Event> events = eventService.findNumberOfEvents(NUMBER_OF_EVENTS);
        //get all showIds of all events
        List<Long> showIds = new ArrayList<>();
        for(Event event: events) {
            for(Show show: event.getShows()) {
                showIds.add(show.getId());
            }
        }

        if(showIds.isEmpty()){
            throw new NotFoundException("No shows in argument list. Needs to contain at least one.");
        }

        for(int i=0; i<NUMBER_OF_CUSTOMERS; i++) {
            int customerIndex = i % users.size();
            int div = NUMBER_OF_TICKETS_TO_GENERATE/NUMBER_OF_CUSTOMERS;
            int remainder = NUMBER_OF_TICKETS_TO_GENERATE % NUMBER_OF_CUSTOMERS;
            int ticketsToGenerate = i==0 ? div + remainder : div;
            generateTickets((Customer) users.get(customerIndex), showIds, ticketsToGenerate);
        }
    }

    private void generateTickets(Customer customer, List<Long> showIds, int ticketsToGenerate) {
        // reserve tickets for customer1
        boolean bool = true;
        for (int i = 0; i < ticketsToGenerate; i++) {
            int showIndex = (int)(Math.random() *  showIds.size());
            Long showId = showIds.get(showIndex);
            Seat seat = seatRepository.findFreeSeatBy(showId);
            //if no free seat found, try again (with new random showIndex) until a seat is found
            if(seat == null) {
                i--;
                continue;
            }

            Ticket ticket = Ticket.builder()
                .price(0D)
                .purchaseDate(LocalDateTime.now().minusDays((int)(Math.random() * 3)).minusMinutes((int)(Math.random() * 1000)))
                .seat(seat)
                .show(Show.builder().id(showId).build())
                .userCode(customer.getUserCode())
                .build();
            List<Ticket> tickets = new ArrayList<>();
            tickets.add(ticket);
            randomPurchaseReserve(tickets, bool);
            bool = !bool;
        }
    }

    public void generateMerchTickets(){

    }

    private void randomPurchaseReserve(List<Ticket> tickets, boolean bool){
        if(bool) {
            ticketService.buyTickets(tickets);
        } else {
            ticketService.reserveTickets(tickets);
        }
    }

}
