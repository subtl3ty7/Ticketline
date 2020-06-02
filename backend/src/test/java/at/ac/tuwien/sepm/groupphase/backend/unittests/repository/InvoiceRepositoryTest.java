package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.TicketInvoice;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class InvoiceRepositoryTest implements TestData {

    @Autowired
    private InvoiceRepository invoiceRepository;

    private AbstractInvoice invoice = TicketInvoice.builder()
        .id(ID)
        .invoice_type(TYP_I)
        .userCode(USER_CODE)
        .payment_method(PAY)
        .purchased_at(PURCHASE)
        .receipt_number(NUM)
        .build();

    @BeforeEach
    public void beforeEach() {
        invoiceRepository.deleteAll();
        invoice = TicketInvoice.builder()
            .id(ID)
            .invoice_type(TYP_I)
            .userCode(USER_CODE)
            .payment_method(PAY)
            .purchased_at(PURCHASE)
            .receipt_number(NUM)
            .build();
    }

    @Test
    public void givenNothing_whenSaveInvoice_thenFindListWithOneElementAndFindInvoiceById() {
        invoiceRepository.save(invoice);

        assertAll(
            () -> assertEquals(1, invoiceRepository.findAll().size()),
            () -> assertNotNull(invoiceRepository.findInvoiceById(invoiceRepository.findAll().get(0).getId()))
        );
    }

    @Test
    public void givenNothing_whenSave2Invoices_thenFindListWith2ElementAndFindInvoicesByUserCode() {
        invoice.setId(2L);
        invoiceRepository.save(invoice);
        invoice.setId(3L);
        invoiceRepository.save(invoice);

        assertAll(
            () -> assertEquals(2, invoiceRepository.findAll().size()),
            () -> assertEquals(2, invoiceRepository.findInvoicesByUserCode(invoice.getUserCode()).size())
        );
    }
}
