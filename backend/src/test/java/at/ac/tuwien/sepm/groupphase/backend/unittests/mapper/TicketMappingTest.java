package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TicketMappingTest implements TestData {

    private final Ticket ticket = Ticket.builder()
        .ticketId(ID)
        .ticketCode(USER_CODE)
        .isPurchased(false)
        .purchaseDate(START)
        .price(TOTAL)
        .userCode(USER_CODE)
        .seat(SEATS.get(0))
        .show(SHOWS.get(0))
        .build();

    @Autowired
    private TicketMapper ticketMapper;

    @Test
    public void shouldMapTicketToTicketDTO() {
        TicketDto ticketDto = ticketMapper.ticketToTicketDto(ticket);
        assertAll(
            () -> assertEquals(ID, ticketDto.getTicketId()),
            () -> assertEquals(USER_CODE, ticketDto.getTicketCode()),
            () -> assertFalse( ticketDto.isPurchased()),
            () -> assertEquals(START, ticketDto.getPurchaseDate()),
            () -> assertEquals(TOTAL, ticketDto.getPrice()),
            () -> assertEquals(USER_CODE, ticketDto.getUserCode()),
            () -> assertEquals(SEATS.get(0), ticketDto.getSeat()),
            () -> assertEquals(SHOWS.get(0), ticketDto.getShow())
        );
    }

    @Test
    public void shouldMapTicketListToTicketDTOList() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticket);
        List<TicketDto> ticketDtos = ticketMapper.ticketListToTicketDtoList(tickets);
        TicketDto ticketDto = ticketDtos.get(0);
        assertAll(
            () -> assertEquals(ID, ticketDto.getTicketId()),
            () -> assertEquals(USER_CODE, ticketDto.getTicketCode()),
            () -> assertFalse( ticketDto.isPurchased()),
            () -> assertEquals(START, ticketDto.getPurchaseDate()),
            () -> assertEquals(TOTAL, ticketDto.getPrice()),
            () -> assertEquals(USER_CODE, ticketDto.getUserCode()),
            () -> assertEquals(SEATS.get(0), ticketDto.getSeat()),
            () -> assertEquals(SHOWS.get(0), ticketDto.getShow())
        );
    }

    @Test
    public void shouldMapTicketDTOToTicket() {
        TicketDto ticketDto = ticketMapper.ticketToTicketDto(ticket);
        Ticket ticket1 = ticketMapper.ticketDtoToTicket(ticketDto);
        assertAll(
            () -> assertEquals(ID, ticket1.getTicketId()),
            () -> assertEquals(USER_CODE, ticket1.getTicketCode()),
            () -> assertFalse( ticket1.isPurchased()),
            () -> assertEquals(START, ticket1.getPurchaseDate()),
            () -> assertEquals(TOTAL, ticket1.getPrice()),
            () -> assertEquals(USER_CODE, ticket1.getUserCode()),
            () -> assertEquals(SEATS.get(0), ticket1.getSeat()),
            () -> assertEquals(SHOWS.get(0), ticket1.getShow())
        );
    }
}
