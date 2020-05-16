package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> buyTicket(List<Ticket> tickets);
}
