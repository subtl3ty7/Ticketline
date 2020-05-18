package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
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
    public ResponseEntity<String> buyTicket(@RequestBody List<TicketDto> ticketDto) {
        LOGGER.info("POST " + ticketDto);
        List<Ticket> tickets = ticketService.buyTicket(ticketMapper.ticketDtoListToTicketList(ticketDto));
        ResponseEntity response = new ResponseEntity(ticketMapper.ticketListToTicketDtoList(tickets), HttpStatus.CREATED);

        LOGGER.info("Sending API Response: [" + response.getBody() + ", " + response.getStatusCode() + "]");
        return response;
    }




}
