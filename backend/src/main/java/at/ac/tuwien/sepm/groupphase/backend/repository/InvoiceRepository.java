package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.TicketInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<AbstractInvoice, Long> {

    AbstractInvoice findInvoiceById(Long id);
    List<AbstractInvoice> findInvoicesByUserCode(String userCode);
}
