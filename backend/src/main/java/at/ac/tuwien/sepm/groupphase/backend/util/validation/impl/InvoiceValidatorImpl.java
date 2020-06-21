package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.InvoiceValidator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceValidatorImpl implements InvoiceValidator {
    private final TicketRepository ticketRepository;
    private final UserValidator userValidator;

    @Autowired
    public InvoiceValidatorImpl(TicketRepository ticketRepository, UserValidator userValidator) {
        this.ticketRepository = ticketRepository;
        this.userValidator = userValidator;
    }

    @Override
    public Constraints validateInvoiceByTicket(Ticket ticket) {
        Constraints constraints = new Constraints();
        Ticket ticketFromDatabase = ticketRepository.findTicketByTicketCode(ticket.getTicketCode());
        constraints.add("ticket_exists", ticketFromDatabase != null);
        if(ticketFromDatabase != null) {
            constraints.add(userValidator.validateUserIdentityWithGivenUserCode(ticketFromDatabase.getUserCode()));
        }
        return constraints;
    }
}
