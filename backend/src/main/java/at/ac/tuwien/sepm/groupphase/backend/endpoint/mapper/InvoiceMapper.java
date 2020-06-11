package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(uses = {TicketMapper.class})
public interface InvoiceMapper {

    @Named("InvoiceToInvoiceDto")
    InvoiceDto invoiceToInvoiceDto(TicketInvoice invoice);

    @Named("InvoiceDtoToInvoice")
    TicketInvoice invoiceDtoToInvoice(InvoiceDto invoiceDto);

    @IterableMapping(qualifiedByName = "InvoiceListToInvoiceDtoList")
    List<InvoiceDto> invoiceListToInvoiceDtoList(List<TicketInvoice> invoices);

    @IterableMapping(qualifiedByName = "InvoiceDtoListToInvoiceList")
    List<TicketInvoice> invoiceDtoListToInvoiceList(List<InvoiceDto> invoiceDtos);

    /*@Named("setTickets")
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
    } */
}
