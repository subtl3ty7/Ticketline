package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomTicketService implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TicketRepository ticketRepository;
    private final TicketValidator validator;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final InvoiceService invoiceService;

    @Autowired
    public CustomTicketService(TicketRepository ticketRepository, TicketValidator validator, SeatRepository seatRepository, ShowRepository showRepository,
                               EventRepository eventRepository, UserRepository userRepository, InvoiceService invoiceService) {
        this.ticketRepository = ticketRepository;
        this.validator = validator;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.invoiceService = invoiceService;
    }

    @Override
    public List<Ticket> buyTickets(List<Ticket> tickets) throws ValidationException, DataAccessException {
        List<Ticket> savedTickets = new ArrayList<>();

        for ( Ticket ticketEntity: tickets
        ) {
            save(ticketEntity);
            ticketEntity.setPurchased(true);

            // updating premium points
            AbstractUser user = userRepository.findAbstractUserByUserCode(ticketEntity.getUserCode());
            long currentPoints = ((Customer) user).getPoints();
            ((Customer) user).setPoints(Long.sum(currentPoints, ticketEntity.getPrice()));
            userRepository.save(user);

            Ticket savedTicket = ticketRepository.save(ticketEntity);
            savedTickets.add(savedTicket);

            LOGGER.info("Purchased ticket " + savedTicket);
        }
        invoiceService.createTicketInvoice(tickets, "PURCHASE", tickets.get(0).getPurchaseDate());
        return savedTickets;
    }

    private String getNewTicketCode() {
        final int maxAttempts = 1000;
        String ticketCode = "";
        int i;
        for(i=0; i<maxAttempts; i++) {
            ticketCode = CodeGenerator.generateTicketCode();
            if(!validator.validateTicketCode(ticketCode).isViolated()) {
                break;
            }
        }
        if(i==1000) {
            throw new ServiceException("Something went wrong while generating TicketCode", null);
        }
        return ticketCode;
    }

    @Override
    public List<Ticket> allTicketsOfUser(String userCode) throws ValidationException, DataAccessException{
        validator.validateAllTicketsOfUser(userCode).throwIfViolated();
        return ticketRepository.findTicketsByUserCode(userCode);
    }

    @Override
    public Ticket save(Ticket ticketEntity) throws ValidationException, DataAccessException {
            LOGGER.info("Validating ticket " + ticketEntity);
            ticketEntity.setTicketCode(getNewTicketCode());
            LocalDateTime now = LocalDateTime.now();
            ticketEntity.setPurchaseDate(now);

            validator.validateBefore(ticketEntity).throwIfViolated();

            Seat seat = seatRepository.findSeatById(ticketEntity.getSeat().getId());
            ticketEntity.setSeat(seat);

            Show show = showRepository.findShowById(ticketEntity.getShow().getId());
            ticketEntity.setShow(show);

            Event event = eventRepository.findEventByEventCode(ticketEntity.getShow().getEventCode());
            ticketEntity.setEvent(event);

            ticketEntity.setPrice(50);
            validator.validateSave(ticketEntity).throwIfViolated();
            validator.validate(ticketEntity).throwIfViolated();

            show.setTicketsSold(show.getTicketsSold() + 1);
            show.setTicketsAvailable(show.getTicketsAvailable() - 1);
            event.setTotalTicketsSold(event.getTotalTicketsSold() + 1);
            showRepository.save(show);
            eventRepository.save(event);

            seat.setFree(false);
            seatRepository.save(seat);

        return ticketEntity;
    }

    @Override
    public List<Ticket> reserveTickets(List<Ticket> tickets) throws ValidationException, DataAccessException {
        List<Ticket> reservedTickets = new ArrayList<>();

        for ( Ticket ticketEntity: tickets
             ) {
            save(ticketEntity);
            ticketEntity.setReserved(true);
            Ticket reservedTicket = ticketRepository.save(ticketEntity);
            reservedTickets.add(reservedTicket);

            LOGGER.info("Reserved ticket " + reservedTicket);
        }
        return reservedTickets;
    }

    @Override
    public void cancelPurchasedTicket(String ticketCode) throws ValidationException, DataAccessException{
        LOGGER.info("Validating ticket with ticketCode " + ticketCode);
        validator.validatePurchased(ticketCode).throwIfViolated();

        Ticket ticket1 = ticketRepository.findTicketByTicketCode(ticketCode);
        Seat seat = ticket1.getSeat();
        seat.setFree(true);
        seatRepository.save(seat);

        invoiceService.createTicketInvoice(List.of(ticket1), "PURCHASE CANCELLATION", LocalDateTime.now());
        ticketRepository.delete(ticket1);
        // do the money return  and invoices stuff

        LOGGER.info("Canceled ticket with ticketCode " + ticketCode);
    }

    @Override
    public Ticket purchaseReservedTicket(String ticketCode) {
        LOGGER.info("Validating ticket with ticketCode " + ticketCode);

        validator.validateReserved(ticketCode).throwIfViolated();

        Ticket ticket = ticketRepository.findTicketByTicketCode(ticketCode);
        ticket.setPurchased(true);
        ticket.setReserved(false);

        // updating premium points
        AbstractUser user = userRepository.findAbstractUserByUserCode(ticket.getUserCode());
        long currentPoints = ((Customer) user).getPoints();
        ((Customer) user).setPoints(Long.sum(currentPoints, ticket.getPrice()));
        userRepository.save(user);

        ticketRepository.save(ticket);

        LOGGER.info("Purchased ticket " + ticket);
        invoiceService.createTicketInvoice(List.of(ticket), "PURCHASE", ticket.getPurchaseDate());

        return ticket;
    }

    @Override
    public void cancelReservedTicket(String ticketCode) {
        LOGGER.info("Validating reservation with ticketCode: " + ticketCode);
        validator.validateReserved(ticketCode).throwIfViolated();

        Ticket chosenTicket = ticketRepository.findTicketByTicketCode(ticketCode);

        Seat seat = chosenTicket.getSeat();
        seat.setFree(true);
        seatRepository.save(seat);

        invoiceService.createTicketInvoice(List.of(chosenTicket), "RESERVATION CANCELLATION", LocalDateTime.now());
        ticketRepository.delete(chosenTicket);
        LOGGER.info("Reservation with the ticket code" + ticketCode +  " cancelled!");
    }
}
