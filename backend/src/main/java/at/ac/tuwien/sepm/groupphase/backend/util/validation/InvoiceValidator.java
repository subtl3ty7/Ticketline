package at.ac.tuwien.sepm.groupphase.backend.util.validation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;

public interface InvoiceValidator {
    Constraints validateInvoiceByTicket(Ticket ticket);
}
