package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.ShowService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.TicketValidator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private final ShowService showService;
    private final UserValidator userValidator;

    @Autowired
    public CustomTicketService(TicketRepository ticketRepository, TicketValidator validator, SeatRepository seatRepository, ShowRepository showRepository,
                               EventRepository eventRepository, UserRepository userRepository, InvoiceService invoiceService, ShowService showService, UserValidator userValidator) {
        this.ticketRepository = ticketRepository;
        this.validator = validator;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.invoiceService = invoiceService;
        this.showService = showService;
        this.userValidator = userValidator;
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
            ((Customer) user).setPoints(Long.sum(currentPoints, ticketEntity.getPrice().intValue()));
            userRepository.save(user);

            Ticket savedTicket = ticketRepository.save(ticketEntity);
            savedTickets.add(savedTicket);

            LOGGER.debug("Purchased ticket " + savedTicket);

        }
        invoiceService.createTicketInvoice(savedTickets, "Kaufrechnung", savedTickets.get(0).getPurchaseDate());
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
        userValidator.validateUserIdentityWithGivenUserCode(userCode).throwIfViolated();
        validator.validateAllTicketsOfUser(userCode).throwIfViolated();

        List<Ticket> allTickets= ticketRepository.findTicketsByUserCode(userCode);
        List<Ticket> filteredTickets = new LinkedList<>();

        for (Ticket ticket: allTickets
             ) {
            if(!ticket.isCancelled()){
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    @Override
    public Ticket save(Ticket ticketEntity) throws ValidationException, DataAccessException {
            LOGGER.debug("Validating ticket " + ticketEntity);
            ticketEntity.setTicketCode(getNewTicketCode());
            LocalDateTime now = LocalDateTime.now();
            ticketEntity.setPurchaseDate(now);
            ticketEntity.setCancelled(false);
            validator.validateBefore(ticketEntity).throwIfViolated();


            Seat seat = seatRepository.findSeatById(ticketEntity.getSeat().getId());
            ticketEntity.setSeat(seat);

            Show show = showService.findShowById(ticketEntity.getShow().getId(), false);
            ticketEntity.setShow(show);

            Event event = eventRepository.findEventByEventCode(ticketEntity.getShow().getEventCode());
            ticketEntity.setEvent(event);

            ticketEntity.setPrice(show.getPrice() + seat.getPrice());
            validator.validateSave(ticketEntity).throwIfViolated();
            validator.validate(ticketEntity).throwIfViolated();
            show.setTicketsSold(show.getTicketsSold() + 1);
            show.setTicketsAvailable(show.getTicketsAvailable() - 1);
            event.setTotalTicketsSold(event.getTotalTicketsSold() + 1);
            show.getTakenSeats().add(seat);
            showRepository.save(show);
            eventRepository.save(event);

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

            LOGGER.debug("Reserved ticket " + reservedTicket);
        }
        return reservedTickets;
    }

    @Override
    @Transactional
    public void cancelPurchasedTicket(String ticketCode) throws ValidationException, DataAccessException{
        LOGGER.debug("Validating ticket with ticketCode " + ticketCode);
        validator.validatePurchased(ticketCode).throwIfViolated();

        Ticket ticket1 = ticketRepository.findTicketByTicketCode(ticketCode);
        Seat seat = ticket1.getSeat();
        Show show = showRepository.findShowById(ticket1.getShow().getId());
        show.getTakenSeats().remove(seat);
        showRepository.save(show);

        //ticketRepository.delete(ticket1);
        ticket1.setPurchased(false);
        ticket1.setCancelled(true);
        ticketRepository.save(ticket1);
        invoiceService.createTicketInvoice(List.of(ticket1), "Kauf Stornorechnung", LocalDateTime.now());

        LOGGER.info("Canceled ticket with ticketCode " + ticketCode);
    }

    @Override
    public Ticket purchaseReservedTicket(String ticketCode) {
        LOGGER.info("Validating ticket with ticketCode " + ticketCode);

        validator.validateReserved(ticketCode).throwIfViolated();

        Ticket ticket = ticketRepository.findTicketByTicketCode(ticketCode);
        ticket.setPurchased(true);
        ticket.setReserved(false);
        ticket.setPurchaseDate(LocalDateTime.now());

        // updating premium points
        AbstractUser user = userRepository.findAbstractUserByUserCode(ticket.getUserCode());
        long currentPoints = ((Customer) user).getPoints();
        ((Customer) user).setPoints(Long.sum(currentPoints, ticket.getPrice().intValue()));
        userRepository.save(user);

        ticketRepository.save(ticket);

        LOGGER.info("Purchased ticket " + ticket);
        invoiceService.createTicketInvoice(List.of(ticket), "Kaufrechnung", ticket.getPurchaseDate());

        return ticket;
    }

    @Override
    @Transactional
    public void cancelReservedTicket(String ticketCode) {
        LOGGER.info("Validating reservation with ticketCode: " + ticketCode);
        validator.validateReserved(ticketCode).throwIfViolated();

        Ticket chosenTicket = ticketRepository.findTicketByTicketCode(ticketCode);

        Seat seat = chosenTicket.getSeat();
        Show show = showRepository.findShowById(chosenTicket.getShow().getId());
        show.getTakenSeats().remove(seat);
        showRepository.save(show);

        chosenTicket.setReserved(false);
        chosenTicket.setCancelled(true);
        ticketRepository.save(chosenTicket);
        invoiceService.createTicketInvoice(List.of(chosenTicket), "Reservation Stornorechnung", LocalDateTime.now());
        //ticketRepository.delete(chosenTicket);
        LOGGER.info("Reservation with the ticket code" + ticketCode +  " cancelled!");
    }
}
