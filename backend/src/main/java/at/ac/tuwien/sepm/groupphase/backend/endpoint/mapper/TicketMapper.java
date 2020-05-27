package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TicketMapper {

    @Named(value = "ticketToSimpleTicketDto")
    @Mapping(source = "ticket", target = "showId", qualifiedByName = "setShowId")
    @Mapping(source = "ticket", target = "seatId", qualifiedByName = "setSeatId")
    @Mapping(source = "ticket", target = "showTime", qualifiedByName = "setShowTime")
    @Mapping(source = "ticket", target = "eventName", qualifiedByName = "setEventName")
    SimpleTicketDto ticketToSimpleTicketDto(Ticket ticket);

    @Named(value = "ticketDtoToTicket")
    Ticket detailedTicketDtoToTicket(DetailedTicketDto detailedTicketDto);

    @IterableMapping(qualifiedByName = "ticketDtoToTicket")
    List<Ticket> detailedTicketDtoListToTicketList(List<DetailedTicketDto> detailedTicketDto);

    @IterableMapping(qualifiedByName = "ticketToSimpleTicketDto")
    List<SimpleTicketDto> ticketListToSimpleTicketDtoList(List<Ticket> ticket);

    @Named(value= "ticketToDetailedTicketDto")
    DetailedTicketDto ticketToDetailedTicketDto(Ticket ticket);

    @IterableMapping(qualifiedByName = "ticketToDetailedTicketDto")
    List<DetailedTicketDto> ticketListToDetailedTicketDtoList(List<Ticket> ticket);

    @Named("setShowId")
    default long setShowId(Ticket ticket){
        return ticket.getShow().getId();
    }

    @Named("setSeatId")
    default long setSeatId(Ticket ticket){
        return ticket.getSeat().getId();
    }

    @Named("setShowTime")
    default LocalDateTime setShowTime(Ticket ticket){
        return ticket.getShow().getStartsAt();
    }

    @Named("setEventName")
    default String setEventName(Ticket ticket){
        return ticket.getEvent().getName();
    }
}


