package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

public interface ShowMapper {
    @Named("show")
    @Mapping(expression = "java(show.getEventLocation().get(0))", target = "eventLocation")
    ShowDto showToShowDto(Show show);
}
