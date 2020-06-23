package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /**
     * Find the invoice by its id
     *
     * @param id - id of the invoice to find
     * @return the single invoice with the given id
     */
    Invoice findInvoiceById(Long id);

    /**
     * Find invoice by its invoice number
     *
     * @param invoice_number - number of the invoice to find
     * @return a single invoice with the given id
     */
    Invoice findInvoiceByInvoiceNumber(String invoice_number);

    /**
     * Find invoices by user code (all invoices of some specific user)
     *
     * @param userCode - code of the user to whom invoices belong
     * @return a list of all invoices of some specific user
     */
    List<Invoice> findInvoicesByUserCode(String userCode);

    /**
     * Find invoice by user code (all invoices of some specific user) and date of creation
     *
     * @param userCode - code of the user  to whom invoices belong
     * @param generatedAt - date of creation
     * @return a single invoice that matches the given criteria
     */
    Invoice findInvoiceByUserCodeAndGeneratedAt(String userCode, LocalDateTime generatedAt);

    /**
     * Find invoice by ticket
     *
     * @param ticket - ticket to look for
     * @return - invoice that matches the given criteria
     */
    Invoice findInvoiceByTicketsContaining(Ticket ticket);
}
