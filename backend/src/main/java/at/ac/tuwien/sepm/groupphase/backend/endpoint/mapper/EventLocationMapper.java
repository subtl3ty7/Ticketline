package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface EventLocationMapper {
    @Named(value = "EventLocationToEventLocationDto")
    EventLocationDto EventLocationToEventLocationDto(EventLocation eventLocation);

    @IterableMapping(qualifiedByName = "EventLocationToEventLocationDto")
    List<EventLocationDto> EventLocationToEventLocationDto(List<EventLocation> eventLocations);

    @Named(value = "eventLocationDtoToEventLocation")
    EventLocation eventLocationDtoToEventLocation(EventLocationDto eventLocation);

    @Named(value = "EventLocationToSimpleEventLocationDto")
    SimpleEventLocationDto EventLocationToSimpleEventLocationDto(EventLocation eventLocation);

    @IterableMapping(qualifiedByName = "EventLocationToSimpleEventLocationDto")
    List<SimpleEventLocationDto> EventLocationToSimpleEventLocationDto(List<EventLocation> eventLocations);

}
