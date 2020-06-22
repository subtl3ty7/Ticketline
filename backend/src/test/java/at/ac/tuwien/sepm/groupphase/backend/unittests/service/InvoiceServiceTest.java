package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceServiceTest implements TestData {

    @Autowired
    private InvoiceService invoiceService;

    @Order(1)
    @Test
    public void whenCreateMerchandiseInvoice_thenInvoiceSetWithProperties() {
        Merchandise merchandise = Merchandise.builder().merchandiseProductCode(USER_CODE).build();

        Invoice invoice = invoiceService.createMerchandiseInvoice(merchandise, USER_CODE, PAY);
        assertAll(
            () -> assertEquals(USER_CODE, invoice.getUserCode()),
            () -> assertEquals(PAY, invoice.getPayment_method()),
            () -> assertNotNull(invoice.getGeneratedAt()),
            () -> assertNotNull(invoice.getInvoiceNumber()),
            () -> assertEquals(TYP_I, invoice.getInvoice_type())
        );
    }

    @Order(2)
    @Test
    public void givenInvoice_whenGetAllOfUser_thenInvoiceListWith1Element() {

        List<Invoice> invoices = invoiceService.allInvoicesOfUser(USER_CODE);
        assertEquals(1, invoices.size());

        Invoice invoice = invoices.get(0);
        assertAll(
            () -> assertEquals(USER_CODE, invoice.getUserCode()),
            () -> assertEquals(PAY, invoice.getPayment_method()),
            () -> assertNotNull(invoice.getGeneratedAt()),
            () -> assertNotNull(invoice.getInvoiceNumber()),
            () -> assertEquals(TYP_I, invoice.getInvoice_type())
        );
    }
}
