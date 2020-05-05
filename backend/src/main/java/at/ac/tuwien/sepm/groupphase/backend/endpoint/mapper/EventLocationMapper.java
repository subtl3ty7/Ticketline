package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface EventLocationMapper {

    @Named(value = "eventLocationToSimpleEventLocationDto")
    SimpleEventLocationDto eventLocationToSimpleEventLocationDto(EventLocation eventLocation);

    DetailedEventLocationDto eventLocationToDetailedEventLocationDto(EventLocation eventLocation);

    @IterableMapping(qualifiedByName = "eventLocationToSimpleEventLocationDto")
    List<SimpleEventLocationDto> eventLocationToSimpleEventLocationDto(List<EventLocation> eventLocations);
}
