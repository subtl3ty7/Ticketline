package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ShowRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.Validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomTicketService implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TicketRepository ticketRepository;
    private final TicketValidator validator;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    @Autowired
    public CustomTicketService(TicketRepository ticketRepository, TicketValidator validator,
                               SeatRepository seatRepository, ShowRepository showRepository){
        this.ticketRepository = ticketRepository;
        this.validator = validator;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
    }


    @Override
    public List<Ticket> buyTicket(List<Ticket> tickets) throws ValidationException, DataAccessException {
        List<Ticket> savedTickets = new ArrayList<>();

        for (Ticket ticketEntity: tickets) {
            LOGGER.info("Validating ticket " + ticketEntity);
            ticketEntity.setTicketCode(getNewTicketCode());
            LocalDateTime now = LocalDateTime.now();
            ticketEntity.setPurchaseDate(now);
            Seat seat = seatRepository.findSeatById(ticketEntity.getSeat().getId());
            ticketEntity.setSeat(seat);
            seatRepository.save(seat);

            validator.validatePurchase(ticketEntity).throwIfViolated();

            Show show = showRepository.findShowById(ticketEntity.getShow().getId());
            ticketEntity.setShow(show);
            showRepository.save(show);

            ticketEntity.getSeat().setFree(false);
            seatRepository.save(seat);

            ticketEntity.setPurchased(true);
            Ticket savedTicket = ticketRepository.save(ticketEntity);
            savedTickets.add(savedTicket);

            LOGGER.info("Saved ticket " + savedTicket + " in the database.");
        }
        return savedTickets;
    }

    private String getNewTicketCode() {
        final int maxAttempts = 1000;
        String ticketCode = "";
        int i;
        for(i=0; i<maxAttempts; i++) {
            ticketCode = CodeGenerator.generateUserCode();
            if(!validator.validateTicketCode(ticketCode).isViolated()) {
                break;
            }
        }
        if(i==1000) {
            throw new ServiceException("Something went wrong while generating TicketCode", null);
        }
        return ticketCode;
    }
}
