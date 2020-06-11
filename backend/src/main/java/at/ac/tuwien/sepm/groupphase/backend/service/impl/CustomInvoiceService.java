package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.TicketInvoice;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomInvoiceService implements InvoiceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceRepository invoiceRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public CustomInvoiceService(InvoiceRepository invoiceRepository, TicketRepository ticketRepository) {
        this.invoiceRepository = invoiceRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketInvoice createTicketInvoice(List<Ticket> tickets, String type, LocalDateTime generatedAt) {
        if(type.equals("PURCHASE CANCELLATION")) {
            invoiceRepository.delete(invoiceRepository.findInvoiceByUserCodeAndGeneratedAt(tickets.get(0).getUserCode(), tickets.get(0).getPurchaseDate()));
        }
        TicketInvoice ticketInvoice = TicketInvoice.builder()
            .invoice_type(type)
            .userCode(tickets.get(0).getUserCode())
            .payment_method("Credit card")
            .generatedAt(generatedAt)
            .invoice_number(CodeGenerator.generateInvoiceNumber())
            .tickets(tickets)
            .build();

        LOGGER.debug("Invoice generated: " + ticketInvoice);
        return invoiceRepository.save(ticketInvoice);
    }

    @Override
    public List<AbstractInvoice> allInvoicesOfUser(String userCode) {
        return invoiceRepository.findInvoicesByUserCode(userCode);
    }
}
