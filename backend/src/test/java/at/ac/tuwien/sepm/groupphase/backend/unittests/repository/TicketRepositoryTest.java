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

    private Ticket ticket = Ticket.builder()
        .ticketId(ID)
        .ticketCode(USER_CODE)
        .isPurchased(false)
        .isReserved(false)
        .purchaseDate(START)
        .price(TOTAL)
        .userCode(USER_CODE)
        .build();

    @BeforeEach
    public void beforeEach() {
        ticketRepository.deleteAll();
        ticket = Ticket.builder()
            .ticketId(ID)
            .ticketCode(USER_CODE)
            .isPurchased(false)
            .isReserved(false)
            .purchaseDate(START)
            .price(TOTAL)
            .userCode(USER_CODE)
            .build();
    }

    @Test
    public void givenNothing_whenSaveTicket_thenFindListWithOneElementAndFindTicketByCode() {
        ticketRepository.save(ticket);

        assertAll(
            () -> assertEquals(1, ticketRepository.findAll().size()),
            () -> assertNotNull(ticketRepository.findTicketByTicketCode(ticket.getTicketCode())),
            () -> assertNotNull(ticketRepository.findTicketByTicketId(ticket.getTicketId()))
        );
    }

    @Test
    public void givenNothing_whenSave2Tickets_thenFindListWith2ElementsAndFindTicketsByUserCode() {
        ticket.setTicketId(2L);
        ticket.setTicketCode("code12");
        ticketRepository.save(ticket);
        ticket.setTicketId(3L);
        ticket.setTicketCode("code13");
        ticket.setUserCode("code12");
        ticketRepository.save(ticket);

        assertAll(
            () -> assertEquals(2, ticketRepository.findAll().size()),
            () -> assertEquals(1, ticketRepository.findTicketsByUserCode(USER_CODE).size())
        );
    }
}
