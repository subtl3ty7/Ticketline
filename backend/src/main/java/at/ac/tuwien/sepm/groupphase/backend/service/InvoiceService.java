package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceService {

    /**
     * Create the invoice for the ticket(s)
     *
     * @param tickets - ticket(s) for which an invoice needs to be created
     * @param type - determines if the invoice is for the purchase or the cancellation
     * @param generated_st - the date and time when the invoice was generated
     * @return - an Invoice for the given bought ticket(s)
     */
    Invoice createTicketInvoice(List<Ticket> tickets, String type, LocalDateTime generated_st);

    /**
     *
     * @param merchandise - merchandise product for which an invoice needs to be created
     * @param userCode - code of the user that has bought this merchandise product
     * @param pay - determines if the user paid with premium points or with money
     * @return - an Invoice for the given and bought merchandise product
     */
    Invoice createMerchandiseInvoice(Merchandise merchandise, String userCode, String pay);

    /**
     * Finds all invoices of an user.
     *
     * @param userCode - user code to look for
     * @return - list of all invoices that have the same userCode
     */
    List<Invoice> allInvoicesOfUser(String userCode);

    /**
     * Find invoice by ticket
     *
     * @param ticket - ticket to look for
     * @return - invoice that matches the given ticket
     */
    Invoice findInvoiceByTicket(Ticket ticket);
}
