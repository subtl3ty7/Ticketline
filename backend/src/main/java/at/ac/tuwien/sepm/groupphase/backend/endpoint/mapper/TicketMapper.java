package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Image;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Mapper(imports = Image.class, uses = {ShowMapper.class, EventMapper.class})
public interface TicketMapper {

    @Named(value = "ticketToSimpleTicketDto")
    @Mapping(source = "ticket", target = "showId", qualifiedByName = "setShowId")
    @Mapping(source = "ticket", target = "seatId", qualifiedByName = "setSeatId")
    @Mapping(source = "ticket", target = "showTime", qualifiedByName = "setShowTime")
    @Mapping(source = "ticket", target = "eventName", qualifiedByName = "setEventName")
    SimpleTicketDto ticketToSimpleTicketDto(Ticket ticket);

    @Named(value = "ticketDtoToTicket")
    @Mapping(target = "show", qualifiedByName = { "ShowMapper", "simpleShowDtoToShow" })
    @Mapping(target = "event", qualifiedByName = { "EventMapper", "simpleEventDtoToEvent" })
    Ticket detailedTicketDtoToTicket(DetailedTicketDto ticket);

    @IterableMapping(qualifiedByName = "ticketDtoToTicket")
    List<Ticket> detailedTicketDtoListToTicketList(List<DetailedTicketDto> tickets);

    @IterableMapping(qualifiedByName = "ticketToSimpleTicketDto")
    List<SimpleTicketDto> ticketListToSimpleTicketDtoList(List<Ticket> ticket);

    @Named(value= "ticketToDetailedTicketDto")
    @Mapping(target = "show", qualifiedByName = { "ShowMapper", "showToSimpleShowDto" })
    @Mapping(target = "event", qualifiedByName = { "EventMapper", "eventToSimpleEventDto" })
    DetailedTicketDto ticketToDetailedTicketDto(Ticket ticket);

    @IterableMapping(qualifiedByName = "ticketToDetailedTicketDto")
    List<DetailedTicketDto> ticketListToDetailedTicketDtoList(List<Ticket> tickets);

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


