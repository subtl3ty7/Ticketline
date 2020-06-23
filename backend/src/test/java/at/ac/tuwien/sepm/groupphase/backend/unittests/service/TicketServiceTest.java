package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
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

    private Ticket ticket = Ticket.builder()
        .ticketId(ID)
        .ticketCode(USER_CODE)
        .purchaseDate(START)
        .price(PRICE)
        .userCode(USER_CODE_TICKET)
        .build();


    @Test
    public void whenBuyTicketWithoutSeatAndShow_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   ticketService.save(ticket));
    }

    @Test
    public void whenBuyReservedTicketWithNonExistingCode_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   ticketService.purchaseReservedTicket("wrong"));
    }

    @Test
    public void whenCancelReservedTicketWithNonExistingCode_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   ticketService.cancelReservedTicket("wrong"));
    }

    @Test
    public void whenCancelPurchasedTicketWithNonExistingCode_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   ticketService.cancelPurchasedTicket("wrong"));
    }
}
