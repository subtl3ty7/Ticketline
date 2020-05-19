package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ShowRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.datagenerator.EventDataGenerator;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
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
    private final EntityManagerFactory entityManagerFactory;
    private static final int NUMBER_OF_TICKETS_TO_GENERATE = 5;

    public TicketDataGenerator(TicketRepository ticketRepository, ShowRepository showRepository, SeatRepository seatRepository
                                , EntityManagerFactory entityManagerFactory, TicketService ticketService){
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.ticketService = ticketService;
        this.entityManagerFactory = entityManagerFactory;
    }

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @PostConstruct
    private void generate() {
        if(ticketRepository.findAll().size() > 0) {
            LOGGER.debug("Ticket Data already generated");
        } else {
            generateTickets();
        }
    }

    public void generateTickets(){
        LOGGER.info("Generating ticket data.");

        if(seatRepository.findAll().isEmpty()){
            throw new NotFoundException("No seats in argument list. Needs to contain at least one.");
        }

        if(showRepository.findAll().isEmpty()){
            throw new NotFoundException("No shows in argument list. Needs to contain at least one.");
        }


        // reserve tickets for user U123X0
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_TICKETS_TO_GENERATE; i++) {
            Ticket ticket = Ticket.builder()
                .price(i)
                .purchaseDate(LocalDateTime.now())
                .seat(seatRepository.findSeatById(((long)i + 1)))
                .show(showRepository.findShowById((long) i + 1))
                .userCode("U123X0")
                .build();
            tickets.add(ticket);
        }
        ticketService.reserveTickets(tickets);

        // buy tickets for user U123X1
        List<Ticket> tickets1 = new ArrayList<>();
        for (int i = NUMBER_OF_TICKETS_TO_GENERATE; i < NUMBER_OF_TICKETS_TO_GENERATE + NUMBER_OF_TICKETS_TO_GENERATE; i++) {
            Ticket ticket = Ticket.builder()
                .price(i)
                .purchaseDate(LocalDateTime.now())
                .seat(seatRepository.findSeatById(((long) i + 1)))
                .show(showRepository.findShowById((long) i+ 1))
                .userCode("U123X1")
                .build();
            tickets1.add(ticket);
        }
        ticketService.buyTickets(tickets1);


    }


}
