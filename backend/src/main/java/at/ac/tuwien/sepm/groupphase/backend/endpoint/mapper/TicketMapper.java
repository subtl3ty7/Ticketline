package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface TicketMapper {

    @Named(value = "ticketToTicketDto")
    TicketDto ticketToTicketDto(Ticket ticket);

    @Named(value = "ticketDtoToTicket")
    Ticket ticketDtoToTicket(TicketDto ticketDto);

    @IterableMapping(qualifiedByName = "ticketDtoToTicket")
    List<Ticket> ticketDtoListToTicketList(List<TicketDto> ticketDto);

    @IterableMapping(qualifiedByName = "ticketToTicketDto")
    List<TicketDto> ticketListToTicketDtoList(List<Ticket> ticket);
}


