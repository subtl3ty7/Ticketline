package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceCategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.InvoiceValidator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.UserValidator;
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
    private final UserValidator userValidator;
    private final InvoiceValidator validator;

    @Autowired
    public CustomInvoiceService(InvoiceRepository invoiceRepository, TicketRepository ticketRepository, UserValidator userValidator, InvoiceValidator validator) {
        this.invoiceRepository = invoiceRepository;
        this.ticketRepository = ticketRepository;
        this.userValidator = userValidator;
        this.validator = validator;
    }

    @Override
    public Invoice createTicketInvoice(List<Ticket> tickets, String type, LocalDateTime generatedAt) {
        if(type.equals("Kauf Stornorechnung")) {
            if(invoiceRepository.findInvoiceByUserCodeAndGeneratedAt(tickets.get(0).getUserCode(), tickets.get(0).getPurchaseDate()) != null) {
                invoiceRepository.delete(invoiceRepository.findInvoiceByUserCodeAndGeneratedAt(tickets.get(0).getUserCode(), tickets.get(0).getPurchaseDate()));
            }
        }

        Invoice ticketInvoice = Invoice.builder()
            .invoice_type(type)
            .invoice_category(InvoiceCategoryEnum.TICKET_INVOICE)
            .userCode(tickets.get(0).getUserCode())
            .payment_method("Kreditkarte/Paypal")
            .generatedAt(generatedAt)
            .invoiceNumber(getNewInvoiceNumber())
            .tickets(tickets)
            .build();

        LOGGER.debug("Ticket invoice generated " + ticketInvoice);
        return invoiceRepository.save(ticketInvoice);
    }

    @Override
    public Invoice createMerchandiseInvoice(Merchandise merchandise, String userCode, String pay) {

        Invoice merchandiseInvoice = Invoice.builder()
            .invoice_type("Kaufrechnung")
            .invoice_category(InvoiceCategoryEnum.MERCHANDISE_INVOICE)
            .userCode(userCode)
            .payment_method(pay)
            .generatedAt(LocalDateTime.now())
            .invoiceNumber(getNewInvoiceNumber())
            .merchandise_code(merchandise.getMerchandiseProductCode())
            .build();

        LOGGER.debug("Merchandise invoice generated " + merchandiseInvoice);
        return invoiceRepository.save(merchandiseInvoice);
    }

    @Override
    public List<Invoice> allInvoicesOfUser(String userCode) {
        userValidator.validateUserIdentityWithGivenUserCode(userCode).throwIfViolated();
        return invoiceRepository.findInvoicesByUserCode(userCode);
    }

    private String getNewInvoiceNumber() {
        final int maxAttempts = 1000;
        String invoiceNumber = "";
        int i;
        for(i=0; i<maxAttempts; i++) {
            invoiceNumber = CodeGenerator.generateInvoiceNumber();
            if(invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber) == null) {
                break;
            }
        }
        if(i==1000) {
            throw new ServiceException("Something went wrong while generating invoice number", null);
        }
        return invoiceNumber;
    }

    @Override
    public Invoice findInvoiceByTicket(Ticket ticket) {
        validator.validateInvoiceByTicket(ticket).throwIfViolated();
        Ticket ticketFromDatabase = ticketRepository.findTicketByTicketCode(ticket.getTicketCode());
        return invoiceRepository.findInvoiceByTicketsContaining(ticketFromDatabase);
    }
}
