package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.TicketInvoice;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface InvoiceMapper {

    @Named("InvoiceToInvoiceDto")
    InvoiceDto invoiceToInvoiceDto(TicketInvoice invoice);

    @Named("InvoiceDtoToInvoice")
    TicketInvoice invoiceDtoToInvoice(InvoiceDto invoiceDto);

    @IterableMapping(qualifiedByName = "InvoiceListToInvoiceDtoList")
    List<InvoiceDto> invoiceListToInvoiceDtoList(List<TicketInvoice> invoices);

    @IterableMapping(qualifiedByName = "InvoiceDtoListToInvoiceList")
    List<TicketInvoice> invoiceDtoListToInvoiceList(List<InvoiceDto> invoiceDtos);
}
