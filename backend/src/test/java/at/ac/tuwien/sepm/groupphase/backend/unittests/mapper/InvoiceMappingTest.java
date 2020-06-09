package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.TicketInvoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class InvoiceMappingTest implements TestData {

    private final TicketInvoice invoice = TicketInvoice.builder()
        .id(ID)
        .invoice_type(TYP_I)
        .userCode(USER_CODE)
        .payment_method(PAY)
        .generatedAt(GENERATE)
        .invoice_number(NUM)
        .build();

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Test
    public void shouldMapInvoiceToInvoiceDto() {
        InvoiceDto invoiceDto = invoiceMapper.invoiceToInvoiceDto(invoice);
        assertAll(
            () -> assertEquals(ID, invoiceDto.getId()),
            () -> assertEquals(USER_CODE, invoiceDto.getUserCode()),
            () -> assertEquals(PAY, invoiceDto.getPayment_method()),
            () -> assertEquals(GENERATE, invoiceDto.getGeneratedAt()),
            () -> assertEquals(NUM, invoiceDto.getInvoice_number())
        );
    }

    @Test
    public void shouldMapInvoiceListToInvoiceDtoList() {
        List<TicketInvoice> invoices = new ArrayList<>();
        invoices.add(invoice);
        invoices.add(invoice);

        List<InvoiceDto> invoiceDtos = invoiceMapper.invoiceListToInvoiceDtoList(invoices);
        InvoiceDto invoiceDto = invoiceDtos.get(0);
        assertAll(
            () -> assertEquals(ID, invoiceDto.getId()),
            () -> assertEquals(USER_CODE, invoiceDto.getUserCode()),
            () -> assertEquals(PAY, invoiceDto.getPayment_method()),
            () -> assertEquals(GENERATE, invoiceDto.getGeneratedAt()),
            () -> assertEquals(NUM, invoiceDto.getInvoice_number())
        );
    }

    @Test
    public void shouldMapInvoiceDtoToInvoice() {
        InvoiceDto invoiceDto = invoiceMapper.invoiceToInvoiceDto(invoice);
        TicketInvoice invoice1 = invoiceMapper.invoiceDtoToInvoice(invoiceDto);
        assertAll(
            () -> assertEquals(ID, invoice1.getId()),
            () -> assertEquals(USER_CODE, invoice1.getUserCode()),
            () -> assertEquals(PAY, invoice1.getPayment_method()),
            () -> assertEquals(GENERATE, invoice1.getGeneratedAt()),
            () -> assertEquals(NUM, invoice1.getInvoice_number())
        );
    }
}
