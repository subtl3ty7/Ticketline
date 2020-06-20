package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Image;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(imports = Image.class, uses = ShowMapper.class)
public interface EventMapper {

    @Named("eventToSimpleEventDto")
    @Mapping(expression = "java(event.getPrices().get(0))", target = "startPrice")
    @Mapping(expression = "java(event.getTotalTicketsSold())", target = "totalTicketsSold")
    @Mapping(expression = "java(event.getPhoto().getImage())", target = "photo")
    @Mapping(expression = "java(event.getDuration().toMillis())", target = "duration")
    SimpleEventDto eventToSimpleEventDto(Event event);

    @IterableMapping(qualifiedByName = "eventToSimpleEventDto")
    List<SimpleEventDto> eventToSimpleEventDto(List<Event> event);

    @Named("eventToDetailedEventDto")
    @Mapping(expression = "java(event.getPrices().get(0))", target = "startPrice")
    @Mapping(expression = "java(event.getPhoto().getImage())", target = "photo")
    @Mapping(expression = "java(event.getDuration().toMillis())", target = "duration")
    @Mapping(target = "shows", qualifiedByName = { "ShowMapper", "showToSimpleShowDto" })
    DetailedEventDto eventToDetailedEventDto(Event event);

    @IterableMapping(qualifiedByName = "eventToDetailedEventDto")
    List<DetailedEventDto> eventToDetailedEventDto(List<Event> event);

    @Named("detailedEventDtoToEvent")
    @Mapping(expression = "java(new Image(null, event.getPhoto()))", target = "photo")
    @Mapping(target = "shows", qualifiedByName = { "ShowMapper", "simpleShowDtoToShow" })
    @Mapping(target = "duration", ignore = true)
    Event detailedEventDtoToEvent(DetailedEventDto event);

    @Named("simpleEventDtoToEvent")
    @Mapping(expression = "java(event.getTotalTicketsSold())", target = "totalTicketsSold")
    @Mapping(expression = "java(new Image(null, event.getPhoto()))", target = "photo")
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "prices", ignore = true)
    Event simpleEventDtoToEvent(SimpleEventDto event);
}
