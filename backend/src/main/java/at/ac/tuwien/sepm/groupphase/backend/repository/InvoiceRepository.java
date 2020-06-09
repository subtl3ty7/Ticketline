package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.TicketInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<TicketInvoice, Long> {

    AbstractInvoice findInvoiceById(Long id);

    List<TicketInvoice> findInvoicesByUserCode(String userCode);

    TicketInvoice findInvoiceByUserCodeAndGeneratedAt(String userCode, LocalDateTime generatedAt);
}
