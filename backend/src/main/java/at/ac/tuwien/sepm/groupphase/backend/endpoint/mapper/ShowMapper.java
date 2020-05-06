package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

public interface ShowMapper {
    @Named("showToShowDto")
    @Mapping(expression = "java(show.getEventLocation().get(0))", target = "eventLocation")
    ShowDto showToShowDto(Show show);

    @IterableMapping(qualifiedByName = "showToShowDto")
    List<ShowDto> showToShowDto(List<Show> show);
}
