package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.TicketEndpoint;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * Find ticket by its ticket code
     *
     * @param ticketCode - ticket code to look for
     * @return a single Ticket that matches the given ticketCode
     */
    Ticket findTicketByTicketCode(String ticketCode);

    /**
     * Find tickets by their user code
     *
     * @param userCode - a user code to whom tickets belong
     * @return a list of tickets that have the given userCode
     */
    List<Ticket> findTicketsByUserCode(String userCode);

    /**
     * Find ticket by its id
     *
     * @param id -  id of the ticket to look for
     * @return a single ticket that matches the given id
     */
    Ticket findTicketByTicketId(Long id);
}
