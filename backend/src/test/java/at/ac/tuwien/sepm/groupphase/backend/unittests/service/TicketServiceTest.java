package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
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
    private UserRepository userRepository;

    private Ticket ticket = Ticket.builder()
        .ticketId(ID)
        .ticketCode(USER_CODE)
        .purchaseDate(START)
        .price(PRICE)
        .userCode(USER_CODE_TICKET)
        .build();

    @BeforeEach
    public void beforeEach() {
        ticketRepository.deleteAll();
        userRepository.deleteAll();
        ticket = Ticket.builder()
            .ticketId(ID)
            .ticketCode(USER_CODE)
            .purchaseDate(START)
            .price(PRICE)
            .userCode(USER_CODE_TICKET)
            .build();
    }

    @Test
    public void given2Tickets_whenGetAllOfUser_thenListWithTicketsElement() {
        USER_TICKET.setEmail("new@email.com");
        USER_TICKET.setUserCode("code22");
        userRepository.save(USER_TICKET);
        ticket.setUserCode(USER_TICKET.getUserCode());
        ticketRepository.save(ticket);
        ticket.setTicketId(2L);
        ticket.setTicketCode("code12");
        ticketRepository.save(ticket);

        assertEquals(2, ticketService.allTicketsOfUser(USER_TICKET.getUserCode()).size());

    }
}
