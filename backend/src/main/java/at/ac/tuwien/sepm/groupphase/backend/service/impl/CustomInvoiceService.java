package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
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
    public Invoice createTicketInvoice(List<Ticket> tickets, String type, LocalDateTime generatedAt) {
        if(type.equals("Kauf Stornorechnung")) {
            invoiceRepository.delete(invoiceRepository.findInvoiceByUserCodeAndGeneratedAt(tickets.get(0).getUserCode(), tickets.get(0).getPurchaseDate()));
        }

        Invoice ticketInvoice = Invoice.builder()
            .invoice_type(type)
            .invoice_category("Ticket invoice")
            .userCode(tickets.get(0).getUserCode())
            .payment_method("Kreditkarte")
            .generatedAt(generatedAt)
            .invoice_number(CodeGenerator.generateInvoiceNumber())
            .tickets(tickets)
            .build();

        LOGGER.debug("Ticket invoice generated " + ticketInvoice);
        return invoiceRepository.save(ticketInvoice);
    }

    @Override
    public Invoice createMerchandiseInvoice(Merchandise merchandise, String userCode, String pay) {

        Invoice merchandiseInvoice = Invoice.builder()
            .invoice_type("Kauf rechnung")
            .invoice_category("Merchandise invoice")
            .userCode(userCode)
            .payment_method(pay)
            .generatedAt(LocalDateTime.now())
            .invoice_number(CodeGenerator.generateInvoiceNumber())
            .merchandise_code(merchandise.getMerchandiseProductCode())
            .build();

        LOGGER.debug("Merchandise invoice generated " + merchandiseInvoice);
        return invoiceRepository.save(merchandiseInvoice);
    }

    @Override
    public List<Invoice> allInvoicesOfUser(String userCode) {
        return invoiceRepository.findInvoicesByUserCode(userCode);
    }
}
