package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomTicketService implements TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public CustomTicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }


    @Override
    public Ticket buyTicket(Ticket ticket) {
        if(this.ticketRepository.findTicketByTicketCode(ticket.getTicketCode()).getSeat().isFree()){
            this.ticketRepository.findTicketByTicketCode(ticket.getTicketCode()).getSeat().setFree(true);
            this.ticketRepository.findTicketByTicketCode(ticket.getTicketCode()).setPurchased(true);

        }
        return null;
    }
}
