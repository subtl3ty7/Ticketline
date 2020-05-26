package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper
public interface EventMapper {

    @Named("simpleEvent")
    @Mapping(expression = "java(event.getPrices().get(0))", target = "startPrice")
    @Mapping(expression = "java(event.getTotalTicketsSold())", target = "totalTicketsSold")
    SimpleEventDto eventToSimpleEventDto(Event event);

    @IterableMapping(qualifiedByName = "simpleEvent")
    List<SimpleEventDto> eventToSimpleEventDto(List<Event> event);

    @Mapping(expression = "java(event.getPrices().get(0))", target = "startPrice")
    DetailedEventDto eventToDetailedEventDto(Event event);

    @IterableMapping(qualifiedByName = "detailedEvent")
    List<DetailedEventDto> eventToDetailedEventDto(List<Event> event);

    Event detailedEventDtoToEvent(DetailedEventDto detailedEventDto);
}
