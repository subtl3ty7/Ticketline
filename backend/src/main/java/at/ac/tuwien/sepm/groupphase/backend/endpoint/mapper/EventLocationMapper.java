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
    EventLocationDto eventLocationToEventLocationDto(EventLocation eventLocation);

    @IterableMapping(qualifiedByName = "EventLocationToEventLocationDto")
    List<EventLocationDto> eventLocationToEventLocationDto(List<EventLocation> eventLocations);

    @Named(value = "eventLocationDtoToEventLocation")
    EventLocation eventLocationDtoToEventLocation(EventLocationDto eventLocation);

    @Named(value = "eventLocationToSimpleEventLocationDto")
    SimpleEventLocationDto eventLocationToSimpleEventLocationDto(EventLocation eventLocation);

    @IterableMapping(qualifiedByName = "eventLocationToSimpleEventLocationDto")
    List<SimpleEventLocationDto> eventLocationToSimpleEventLocationDto(List<EventLocation> eventLocations);

}
