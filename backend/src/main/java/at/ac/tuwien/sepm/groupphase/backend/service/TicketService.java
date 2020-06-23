package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;

import java.util.List;

public interface TicketService {

    /**
     * Buy a ticket
     *
     * @param tickets - ticket(s) that should be bought
     * @return a list of bought tickets
     */
    List<Ticket> buyTickets(List<Ticket> tickets);

    /**
     * Find all tickets of some specific user
     *
     * @param userCode - user code of the user to whom the tickets belong
     * @return a list of all tickets that belong to the user
     */
    List<Ticket> allTicketsOfUser(String userCode);

    /**
     * Saves a new ticket in the database
     *
     * @param ticketEntity - ticket that should be saved
     * @return a single, newly-created ticket
     */
    Ticket save(Ticket ticketEntity);

    /**
     * Reserve tickets
     *
     * @param tickets list of tickets that should be reserved
     * @return a list of reserved tickets
     */
    List<Ticket> reserveTickets(List<Ticket> tickets);

    /**
     * Cancel ticket that has already been purchased
     *
     * @param ticketCode - ticket code of the ticket
     */
    void cancelPurchasedTicket(String ticketCode);

    /**
     * Purchase a ticket that has already been reserved.
     *
     * @param ticketCode - ticket code of the ticket
     * @return a single, purchased ticket
     */
    Ticket purchaseReservedTicket(String ticketCode);

    /**
     * Cancel the reservation of the ticket
     *
     * @param ticketCode - ticket code of the ticket
     */
    void cancelReservedTicket(String ticketCode);
}
