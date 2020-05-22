package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tickets")
public class TicketEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketEndpoint(TicketMapper ticketMapper, TicketService ticketService){
        this.ticketMapper = ticketMapper;
        this.ticketService = ticketService;
    }

    @PostMapping(value = "/purchase")
    @ApiOperation(
        value = "Purchasing a ticket",
        notes = "Purchasing a ticket",
         authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 201, message = "Ticket is successfully purchased"),
        @ApiResponse(code = 400, message = "Ticket is already purchased"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> buyTicket(@RequestBody List<DetailedTicketDto> detailedTicketDto) {
        LOGGER.info("POST " + detailedTicketDto);
        List<Ticket> tickets = ticketService.buyTickets(ticketMapper.detailedTicketDtoListToTicketList(detailedTicketDto));

        ResponseEntity response = new ResponseEntity(ticketMapper.ticketListToSimpleTicketDtoList(tickets), HttpStatus.CREATED);

        LOGGER.info("Sending API Response: [" + response.getBody() + ", " + response.getStatusCode() + "]");
        return response;
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{userCode}")
    @ApiOperation(
        value = "Get all tickets of one user",
        notes = "Get all tickets of one user")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Tickets are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Ticket is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<SimpleTicketDto> getAllTicketsOneUser(@PathVariable String userCode) {
        LOGGER.info("GET /api/v1/tickets/" + userCode);

        List<Ticket> tickets = ticketService.allTicketsOfUser(userCode);

        return new ResponseEntity(ticketMapper.ticketListToSimpleTicketDtoList(tickets), HttpStatus.OK);
    }

    /*public List<SimpleTicketDto> setIdsOfShowAndSeatObjects(List<Ticket> tickets){
        List<SimpleTicketDto> simpleTickets = ticketMapper.ticketListToSimpleTicketDtoList(tickets);

        // sets simpleTicketDtos ids to the corresponding id of the detailedTicketDto objects
        int i = 0;
        for (SimpleTicketDto ticketEntity: simpleTickets
        ) {
            ticketEntity.setShowId(tickets.get(i).getShow().getId());
            ticketEntity.setSeatId(tickets.get(i).getSeat().getId());
            ++i;
        }
        return simpleTickets;
    }*/
    @PostMapping(value = "/reserve")
    @ApiOperation(
        value = "Reserving a ticket",
        notes = "Reserving a ticket",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 201, message = "Ticket is successfully reserved"),
        @ApiResponse(code = 400, message = "Ticket is already reserved"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> reserveTicket(@RequestBody List<DetailedTicketDto> detailedTicketDto) {
        LOGGER.info("POST " + detailedTicketDto);
        List<Ticket> tickets = ticketService.reserveTickets(ticketMapper.detailedTicketDtoListToTicketList(detailedTicketDto));

        ResponseEntity response = new ResponseEntity(ticketMapper.ticketListToSimpleTicketDtoList(tickets), HttpStatus.CREATED);

        LOGGER.info("Sending API Response: [" + response.getBody() + ", " + response.getStatusCode() + "]");
        return response;
    }
    @DeleteMapping(value = "/cancelPurchased/{ticketCode}")
    @ApiOperation(
        value = "Cancel purchased ticket.",
        notes = "Cancel purchased ticket.",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 204, message = "Ticket is successfully canceled"),
        @ApiResponse(code = 404, message = "Ticket is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<Void> cancelPurchasedTicket(@PathVariable String ticketCode) {
        LOGGER.info("DELETE /api/v1/tickets/cancelPurchased/" + ticketCode);
        ticketService.cancelPurchasedTicket(ticketCode);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "/purchaseReserved/{ticketCode}")
    @ApiOperation(
        value = "Purchasing a reserved ticket.",
        notes = "Purchasing a reserved ticket.",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 201, message = "Ticket is successfully purchased"),
        @ApiResponse(code = 400, message = "Ticket is already purchased"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> purchaseReservedTicket(@PathVariable String ticketCode) {
        LOGGER.info("POST /api/v1/tickets/purchaseReserved/" + ticketCode);
        Ticket ticket = ticketService.purchaseReservedTicket(ticketCode);

        ResponseEntity response = new ResponseEntity(ticketMapper.ticketToSimpleTicketDto(ticket), HttpStatus.CREATED);

        LOGGER.info("Sending API Response: [" + response.getBody() + ", " + response.getStatusCode() + "]");
        return response;
    }



}
