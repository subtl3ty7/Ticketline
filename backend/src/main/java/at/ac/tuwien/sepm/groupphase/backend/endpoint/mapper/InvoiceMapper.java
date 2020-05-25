package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface InvoiceMapper {

    @Named("InvoiceToInvoiceDto")
    InvoiceDto invoiceToInvoiceDto(Invoice invoice);

    @Named("InvoiceDtoToInvoice")
    Invoice invoiceDtoToInvoice(InvoiceDto invoiceDto);

    @IterableMapping(qualifiedByName = "InvoiceListToInvoiceDtoList")
    List<InvoiceDto> invoiceListToInvoiceDtoList(List<Invoice> invoices);

    @IterableMapping(qualifiedByName = "InvoiceDtoListToInvoiceList")
    List<Invoice> invoiceDtoListToInvoiceList(List<InvoiceDto> invoiceDtos);


}
