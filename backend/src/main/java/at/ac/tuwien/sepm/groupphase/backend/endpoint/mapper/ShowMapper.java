package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleShowDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(uses = {EventLocationMapper.class})
public interface ShowMapper {
    @Named("showToShowDto")
    //@Mapping(expression= "java(EventLocationMapper.eventLocationCopyToEventLocationDto(show.getEventLocationCopy().get(0)))", target = "eventLocation")
    ShowDto showToShowDto(Show show);

    @IterableMapping(qualifiedByName = "showToShowDto")
    List<ShowDto> showToShowDto(List<Show> show);

    @Named("showToSimpleShowDto")
    SimpleShowDto showToSimpleShowDto(Show show);

    @IterableMapping(qualifiedByName = "showToShowDto")
    List<SimpleShowDto> showToSimpleShowDto(List<Show> show);
}
