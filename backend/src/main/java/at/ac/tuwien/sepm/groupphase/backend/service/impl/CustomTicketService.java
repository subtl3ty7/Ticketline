package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
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
import at.ac.tuwien.sepm.groupphase.backend.util.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
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
    public List<Ticket> buyTickets(List<Ticket> tickets) throws ValidationException, DataAccessException {
        List<Ticket> savedTickets = new ArrayList<>();

        for ( Ticket ticketEntity: tickets
        ) {
            save(ticketEntity);
            ticketEntity.setPurchased(true);
            Ticket savedTicket = ticketRepository.save(ticketEntity);
            savedTickets.add(savedTicket);

            LOGGER.info("Purchased ticket " + savedTicket);
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

    @Override
    public List<Ticket> allTicketsOfUser(String userCode) throws ValidationException, DataAccessException{
        validator.validateAllTicketsOfUser(userCode).throwIfViolated();
        return ticketRepository.findTicketsByUserCode(userCode);
    }

    @Override
    public Ticket save(Ticket ticketEntity) throws ValidationException, DataAccessException {
            LOGGER.info("Validating ticket " + ticketEntity);
            ticketEntity.setTicketCode(getNewTicketCode());
            LocalDateTime now = LocalDateTime.now();
            ticketEntity.setPurchaseDate(now);

            Seat seat = seatRepository.findSeatById(ticketEntity.getSeat().getId());
            ticketEntity.setSeat(seat);
            seatRepository.save(seat);

            Show show = showRepository.findShowById(ticketEntity.getShow().getId());
            ticketEntity.setShow(show);
            showRepository.save(show);

            validator.validateSave(ticketEntity).throwIfViolated();
            validator.validate(ticketEntity).throwIfViolated();

            show.setTicketsSold(show.getTicketsSold() + 1);
            show.setTicketsAvailable(show.getTicketsAvailable() - 1);
            showRepository.save(show);

            ticketEntity.getSeat().setFree(false);
            seatRepository.save(seat);

        return ticketEntity;
    }

    @Override
    public List<Ticket> reserveTickets(List<Ticket> tickets) throws ValidationException, DataAccessException {
        List<Ticket> reservedTickets = new ArrayList<>();

        for ( Ticket ticketEntity: tickets
             ) {
            save(ticketEntity);
            ticketEntity.setReserved(true);
            Ticket reservedTicket = ticketRepository.save(ticketEntity);
            reservedTickets.add(reservedTicket);

            LOGGER.info("Reserved ticket " + reservedTicket);
        }
        return reservedTickets;
    }




}
