package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface EventLocationMapper {

    @Mapping(source = "eventLocationName", target = "name")
    @Named(value = "eventLocationToEventLocationDto")
    EventLocationDto eventLocationToEventLocationDto(EventLocation eventLocation);

    @IterableMapping(qualifiedByName = "eventLocationToEventLocationDto")
    List<EventLocationDto> eventLocationToEventLocationDto(List<EventLocation> eventLocations);
}
