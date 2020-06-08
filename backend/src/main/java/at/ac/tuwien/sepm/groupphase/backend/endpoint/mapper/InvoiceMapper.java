package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface InvoiceMapper {

    @Named("InvoiceToInvoiceDto")
    @Mapping(source = "invoice", target = "tickets", qualifiedByName = "setTickets")
    @Mapping(source = "invoice", target = "invoice_type", qualifiedByName = "setType")
    InvoiceDto invoiceToInvoiceDto(AbstractInvoice invoice);

    @Named("InvoiceDtoToInvoice")
    TicketInvoice invoiceDtoToInvoice(InvoiceDto invoiceDto);

    @IterableMapping(qualifiedByName = "InvoiceListToInvoiceDtoList")
    List<InvoiceDto> invoiceListToInvoiceDtoList(List<AbstractInvoice> invoices);

    @IterableMapping(qualifiedByName = "InvoiceDtoListToInvoiceList")
    List<TicketInvoice> invoiceDtoListToInvoiceList(List<InvoiceDto> invoiceDtos);

    @Named("setTickets")
    default List<Ticket> setTickets(AbstractInvoice invoice) {
        if(invoice instanceof TicketInvoice){
            return ((TicketInvoice)invoice).getTickets();
        }
        return null;
    }

    @Named("setType")
    default String setType(AbstractInvoice invoice) {
        if(invoice instanceof TicketInvoice){
            return ((TicketInvoice)invoice).getInvoice_type();
        }
        return null;
    }
}
