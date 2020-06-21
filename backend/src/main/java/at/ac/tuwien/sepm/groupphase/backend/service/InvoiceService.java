package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceService {

    Invoice createTicketInvoice(List<Ticket> tickets, String type, LocalDateTime generated_st);
    Invoice createMerchandiseInvoice(Merchandise merchandise, String userCode, String pay);
    List<Invoice> allInvoicesOfUser(String userCode);

    Invoice findInvoiceByTicket(Ticket ticket);
}
