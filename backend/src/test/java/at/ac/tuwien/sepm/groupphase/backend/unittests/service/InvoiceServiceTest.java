package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
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
public class InvoiceServiceTest implements TestData {

    @Autowired
    private InvoiceService invoiceService;

    @Test
    public void whenCreateMerchandiseInvoice_thenInvoiceSetWithProperties() {
        Merchandise merchandise = Merchandise.builder().merchandiseProductCode(USER_CODE).build();

        Invoice invoice = invoiceService.createMerchandiseInvoice(merchandise, USER_CODE_INVOICE, PAY);
        assertAll(
            () -> assertEquals(USER_CODE_INVOICE, invoice.getUserCode()),
            () -> assertEquals(PAY, invoice.getPayment_method()),
            () -> assertNotNull(invoice.getGeneratedAt()),
            () -> assertNotNull(invoice.getInvoiceNumber()),
            () -> assertEquals(TYP_I, invoice.getInvoice_type())
        );
    }
}
