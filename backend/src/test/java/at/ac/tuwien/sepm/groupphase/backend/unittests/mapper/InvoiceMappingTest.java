package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
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

    private final Invoice invoice = Invoice.builder()
        .id(ID)
        .invoice_type(TYP_I)
        .merchandise_code(USER_CODE)
        .userCode(USER_CODE)
        .payment_method(PAY)
        .purchased_at(PURCHASE)
        .receipt_number(NUM)
        .tickets(TICKETS)
        .build();

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Test
    public void shouldMapInvoiceToInvoiceDto() {
        InvoiceDto invoiceDto = invoiceMapper.invoiceToInvoiceDto(invoice);
        assertAll(
            () -> assertEquals(ID, invoiceDto.getId()),
            () -> assertEquals(TYP_I, invoiceDto.getInvoice_type()),
            () -> assertEquals(USER_CODE, invoiceDto.getUserCode()),
            () -> assertEquals(USER_CODE, invoiceDto.getMerchandise_code()),
            () -> assertEquals(PAY, invoiceDto.getPayment_method()),
            () -> assertEquals(PURCHASE, invoiceDto.getPurchased_at()),
            () -> assertEquals(NUM, invoiceDto.getReceipt_number()),
            () -> assertEquals(TICKETS, invoiceDto.getTickets())
        );
    }

    @Test
    public void shouldMapInvoiceListToInvoiceDtoList() {
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);
        invoices.add(invoice);

        List<InvoiceDto> invoiceDtos = invoiceMapper.invoiceListToInvoiceDtoList(invoices);
        InvoiceDto invoiceDto = invoiceDtos.get(0);
        assertAll(
            () -> assertEquals(ID, invoiceDto.getId()),
            () -> assertEquals(TYP_I, invoiceDto.getInvoice_type()),
            () -> assertEquals(USER_CODE, invoiceDto.getUserCode()),
            () -> assertEquals(USER_CODE, invoiceDto.getMerchandise_code()),
            () -> assertEquals(PAY, invoiceDto.getPayment_method()),
            () -> assertEquals(PURCHASE, invoiceDto.getPurchased_at()),
            () -> assertEquals(NUM, invoiceDto.getReceipt_number()),
            () -> assertEquals(TICKETS, invoiceDto.getTickets())
        );
    }

    @Test
    public void shouldMapInvoiceDtoToInvoice() {
        InvoiceDto invoiceDto = invoiceMapper.invoiceToInvoiceDto(invoice);
        Invoice invoice1 = invoiceMapper.invoiceDtoToInvoice(invoiceDto);
        assertAll(
            () -> assertEquals(ID, invoice1.getId()),
            () -> assertEquals(TYP_I, invoice1.getInvoice_type()),
            () -> assertEquals(USER_CODE, invoice1.getUserCode()),
            () -> assertEquals(USER_CODE, invoice1.getMerchandise_code()),
            () -> assertEquals(PAY, invoice1.getPayment_method()),
            () -> assertEquals(PURCHASE, invoice1.getPurchased_at()),
            () -> assertEquals(NUM, invoice1.getReceipt_number()),
            () -> assertEquals(TICKETS, invoice1.getTickets())
        );
    }
}
