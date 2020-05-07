package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface EventMapper {

    @Named("simpleEvent")
    @Mapping(expression = "java(event.getPrices().get(0))", target = "startPrice")
    SimpleEventDto eventToSimpleEventDto(Event event);

    /**
     * This is necessary since the SimpleEventDto misses the text property and the collection mapper can't handle
     * missing fields
     **/
    @IterableMapping(qualifiedByName = "simpleEvent")
    List<SimpleEventDto> eventToSimpleEventDto(List<Event> event);

    @Mapping(expression = "java(event.getPrices().get(0))", target = "startPrice")
    DetailedEventDto eventToDetailedEventDto(Event event);

    Event detailedEventDtoToEvent(DetailedEventDto detailedEventDto);
}
