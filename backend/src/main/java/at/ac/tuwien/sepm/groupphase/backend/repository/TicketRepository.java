package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.TicketEndpoint;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
   Ticket findTicketByTicketCode(String ticketCode);
   List<Ticket> findTicketsByUserCode(String userCode);
   Ticket findTicketByTicketId(Long id);
}
