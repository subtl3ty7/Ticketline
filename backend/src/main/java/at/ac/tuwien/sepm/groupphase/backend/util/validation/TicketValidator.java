package at.ac.tuwien.sepm.groupphase.backend.util.validation;

import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

/**
 *  Implements all validation methods for validating all requests in ticket service before they are sent to the repository.
 */
public interface TicketValidator {
    Constraints validateTicketCode(String ticketCode);
    Constraints validate(Ticket ticket);
    Constraints validateSave(Ticket ticket);
    Constraints validateAllTicketsOfUser(String ticketCode);
    Constraints validatePurchased(String ticketCode);
    Constraints validateBefore(Ticket ticket);
    Constraints validateReserved(String ticketCode);

}
