package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.TicketInvoice;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceService {

    TicketInvoice createTicketInvoice(List<Ticket> tickets, String type, LocalDateTime generated_st);
    List<TicketInvoice> allInvoicesOfUser(String userCode);

}
