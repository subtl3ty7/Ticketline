package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import net.bytebuddy.asm.Advice;
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

    @Autowired
    private TicketMapper ticketMapper;

    private final Ticket ticket = Ticket.builder()
        .ticketId(ID)
        .ticketCode(USER_CODE)
        .isPurchased(false)
        .isReserved(false)
        .purchaseDate(START)
        .price(PRICE)
        .userCode(USER_CODE)
        .seat(SEATS.get(0))
        .show(SHOWS.get(0))
        .event(EVENT)
        .build();

    @Test
    public void shouldMapTicketToSimpleTicketDTO() {
        SimpleTicketDto ticketDto = ticketMapper.ticketToSimpleTicketDto(ticket);
        assertAll(
            () -> assertEquals(ID, ticketDto.getTicketId()),
            () -> assertEquals(USER_CODE, ticketDto.getTicketCode()),
            () -> assertFalse( ticketDto.isPurchased()),
            () -> assertFalse( ticketDto.isReserved()),
            () -> assertEquals(START, ticketDto.getPurchaseDate()),
            () -> assertEquals(USER_CODE, ticketDto.getUserCode()),
            () -> assertEquals(SEATS.get(0).getId(), ticketDto.getSeatId()),
            () -> assertEquals(SHOWS.get(0).getId(), ticketDto.getShowId()),
            () -> assertEquals(EVENT.getName(), ticketDto.getEventName())
        );
    }

    @Test
    public void shouldMapSimpleTicketListToSimpleTicketDTOList() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticket);
        List<SimpleTicketDto> ticketDtos = ticketMapper.ticketListToSimpleTicketDtoList(tickets);
        SimpleTicketDto ticketDto = ticketDtos.get(0);
        assertAll(
            () -> assertEquals(ID, ticketDto.getTicketId()),
            () -> assertEquals(USER_CODE, ticketDto.getTicketCode()),
            () -> assertFalse( ticketDto.isPurchased()),
            () -> assertFalse( ticketDto.isReserved()),
            () -> assertEquals(START, ticketDto.getPurchaseDate()),
            () -> assertEquals(USER_CODE, ticketDto.getUserCode()),
            () -> assertEquals(SEATS.get(0).getId(), ticketDto.getSeatId()),
            () -> assertEquals(SHOWS.get(0).getId(), ticketDto.getShowId()),
            () -> assertEquals(EVENT.getName(), ticketDto.getEventName())
        );
    }

    @Test
    public void shouldMapTicketToDetailedTicketDTO() {
        DetailedTicketDto detailedTicketDto = ticketMapper.ticketToDetailedTicketDto(ticket);
        assertAll(
            () -> assertEquals(ID, detailedTicketDto.getTicketId()),
            () -> assertEquals(USER_CODE, detailedTicketDto.getTicketCode()),
            () -> assertFalse( detailedTicketDto.isPurchased()),
            () -> assertFalse( detailedTicketDto.isReserved()),
            () -> assertEquals(START, detailedTicketDto.getPurchaseDate()),
            () -> assertEquals(USER_CODE, detailedTicketDto.getUserCode()),
            () -> assertEquals(PRICE, detailedTicketDto.getPrice())
        );
    }

    @Test
    public void shouldMapDetailedTicketDTOToTicket() {
        DetailedTicketDto detailedTicketDto = ticketMapper.ticketToDetailedTicketDto(ticket);
        Ticket ticket1 = ticketMapper.detailedTicketDtoToTicket(detailedTicketDto);
        assertAll(
            () -> assertEquals(ID, ticket1.getTicketId()),
            () -> assertEquals(USER_CODE, ticket1.getTicketCode()),
            () -> assertFalse( ticket1.isPurchased()),
            () -> assertFalse( ticket1.isReserved()),
            () -> assertEquals(START, ticket1.getPurchaseDate()),
            () -> assertEquals(USER_CODE, ticket1.getUserCode()),
            () -> assertEquals(PRICE, ticket1.getPrice())
        );
    }
}
