package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocationCopy;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocationOriginal;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface EventLocationMapper {
    @Named(value = "eventLocationCopyToEventLocationDto")
    EventLocationDto eventLocationCopyToEventLocationDto(EventLocationCopy eventLocation);

    @IterableMapping(qualifiedByName = "eventLocationCopyToEventLocationDto")
    List<EventLocationDto> eventLocationCopyToEventLocationDto(List<EventLocationCopy> eventLocations);


    @Named(value = "eventLocationOriginalToEventLocationDto")
    EventLocationDto eventLocationOriginalToEventLocationDto(EventLocationOriginal eventLocation);

    @IterableMapping(qualifiedByName = "eventLocationOriginalToEventLocationDto")
    List<EventLocationDto> eventLocationOriginalToEventLocationDto(List<EventLocationOriginal> eventLocations);


    @Named(value = "eventLocationDtoToEventLocationOriginal")
    EventLocationOriginal eventLocationDtoToEventLocationOriginal(EventLocationDto eventLocation);

}
