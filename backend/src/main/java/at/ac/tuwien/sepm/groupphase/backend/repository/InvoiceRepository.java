package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Invoice findInvoiceById(Long id);

    Invoice findInvoiceByInvoiceNumber(String invoice_number);

    List<Invoice> findInvoicesByUserCode(String userCode);

    Invoice findInvoiceByUserCodeAndGeneratedAt(String userCode, LocalDateTime generatedAt);

    Invoice findInvoiceByTicketsContaining(Ticket ticket);
}
