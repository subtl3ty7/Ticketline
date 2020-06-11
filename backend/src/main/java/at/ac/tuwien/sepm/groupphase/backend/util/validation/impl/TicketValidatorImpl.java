package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.TicketValidator;
import org.springframework.stereotype.Component;

@Component
public class TicketValidatorImpl implements TicketValidator {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public TicketValidatorImpl(TicketRepository ticketRepository, UserRepository userRepository, ShowRepository showRepository,
                                SeatRepository seatRepository){
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public Constraints validateTicketCode(String ticketCode) {
        Constraints constraints = new Constraints();
        constraints.add("ticketCode_unique", ticketRepository.findTicketByTicketCode(ticketCode) == null);
        return constraints;
    }

    @Override
    public Constraints validate(Ticket ticket) {
        Constraints constraints = new Constraints();
        constraints.add(validateUnique(ticket));
        constraints.add(AccesoryValidator.validateJavaxConstraints(ticket));
        return constraints;
    }

    @Override
    public Constraints validateSave(Ticket ticket) {
        Constraints constraints = new Constraints();
        AbstractUser userFromDataBase = userRepository.findAbstractUserByUserCode(ticket.getUserCode());
        constraints.add("seat_notFree", !showRepository.findShowById(ticket.getShow().getId()).getTakenSeats().contains(ticket.getSeat()));
        constraints.add("userCode_exists", userFromDataBase != null);
        constraints.add("show_exists", showRepository.findShowById(ticket.getShow().getId()) != null);
        constraints.add("seat_exists", seatRepository.findSeatById(ticket.getSeat().getId()) != null);
        constraints.add("ticket_exists", ticketRepository.findTicketByTicketId(ticket.getTicketId()) == null);
        constraints.add("admin_purchase", !(userFromDataBase instanceof Administrator));
       // constraints.add("tickets_sold", (showRepository.findShowById(ticket.getShow().getId()).getTicketsAvailable()) == 0);
        return constraints;
    }

    @Override
    public Constraints validateAllTicketsOfUser(String userCode) {
        Constraints constraints = new Constraints();
        AbstractUser userFromDataBase = userRepository.findAbstractUserByUserCode(userCode);
        constraints.add("userCode_exists", userFromDataBase != null);
        constraints.add("no_tickets", !ticketRepository.findTicketsByUserCode(userCode).isEmpty());
        return constraints;
    }

    @Override
    public Constraints validatePurchased(String ticketCode) {
        Constraints constraints = new Constraints();
        constraints.add("ticket_notExist", ticketRepository.findTicketByTicketCode(ticketCode) != null);
        if(!constraints.isViolated()){
            constraints.add("ticket_purchased", ticketRepository.findTicketByTicketCode(ticketCode).isPurchased());
        }
        return constraints;
    }

    @Override
    public Constraints validateBefore(Ticket ticket) {
        Constraints constraints = new Constraints();
        constraints.add("ticket_notNull", ticket != null);;
        if(ticket != null) {
            constraints.add("seat_notNull", ticket.getSeat() != null && ticket.getSeat().getId() != null);
            constraints.add("show_notNull", ticket.getShow() != null && ticket.getShow().getId() != null);
            constraints.add("price_notNull", ticket.getPrice() != null );
        }
        return constraints;
    }

    @Override
    public Constraints validateReserved(String ticketCode) {
        Constraints constraints = new Constraints();
        constraints.add("ticket_notExist", ticketRepository.findTicketByTicketCode(ticketCode) != null);
        if(!constraints.isViolated()){
            constraints.add("ticket_reserved", ticketRepository.findTicketByTicketCode(ticketCode).isReserved());
        }
        return constraints;
    }

    private Constraints validateUnique(Ticket ticket){
        Constraints constraints = new Constraints();
        constraints.add("ticketCode_unique", ticketRepository.findTicketByTicketCode(ticket.getTicketCode()) == null);
        return constraints;
    }
}
