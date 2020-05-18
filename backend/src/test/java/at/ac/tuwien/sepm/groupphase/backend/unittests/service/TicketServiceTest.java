package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ShowRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TicketServiceTest implements TestData {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    private Ticket ticket = Ticket.builder()
        .ticketId(ID)
        .ticketCode(USER_CODE)
        .isPurchased(false)
        .purchaseDate(START)
        .price(TOTAL)
        .userCode(USER_CODE)
        .seat(SEATS.get(0))
        .show(SHOWS.get(0))
        .build();

    @BeforeEach
    public void beforeEach() {
        ticketRepository.deleteAll();
        userRepository.deleteAll();
        seatRepository.deleteAll();
        showRepository.deleteAll();
        ticket = Ticket.builder()
            .ticketId(ID)
            .ticketCode(USER_CODE)
            .isPurchased(false)
            .purchaseDate(START)
            .price(TOTAL)
            .userCode(USER_CODE)
            .seat(SEATS.get(0))
            .show(SHOWS.get(0))
            .build();
    }
}
