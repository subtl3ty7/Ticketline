package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomTicketService implements TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public CustomTicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }


    @Override
    public List<Ticket> buyTicket(List<Ticket> tickets) {
        for (Ticket ticketEntity: tickets) {
            ticketRepository.save(ticketEntity);
        }
       /* if(this.ticketRepository.findTicketByTicketCode(ticket.getTicketCode()).getSeat().isFree()){
            this.ticketRepository.findTicketByTicketCode(ticket.getTicketCode()).getSeat().setFree(false);
            this.ticketRepository.findTicketByTicketCode(ticket.getTicketCode()).setPurchased(true);
        }*/
        return null;
    }
}
