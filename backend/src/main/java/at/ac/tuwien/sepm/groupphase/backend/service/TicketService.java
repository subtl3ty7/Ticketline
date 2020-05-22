package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> buyTickets(List<Ticket> tickets);
    List<Ticket> allTicketsOfUser(String userCode);
    Ticket save(Ticket ticketEntity);
    List<Ticket> reserveTickets(List<Ticket> tickets);
    void cancelPurchasedTicket(String ticketCode);
    Ticket purchaseReservedTicket(String ticketCode);
}
