package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class TicketRepositoryTest implements TestData {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void givenNothing_whenSaveTicket_thenFindListWithOneElementAndFindTicketByCode() {
        Ticket ticket = Ticket.builder()
            .ticketId(ID)
            .ticketCode(USER_CODE)
            .isPurchased(false)
            .isReserved(false)
            .purchaseDate(START)
            .price(PRICE)
            .userCode(USER_CODE)
            .build();

        ticketRepository.save(ticket);

        assertAll(
            () -> assertEquals(1, ticketRepository.findAll().size()),
            () -> assertNotNull(ticketRepository.findTicketByTicketCode(ticket.getTicketCode())),
            () -> assertNotNull(ticketRepository.findTicketByTicketId(ticket.getTicketId()))
        );
    }
}
