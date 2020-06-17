package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleShowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimplerShowDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Image;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Named("ShowMapper")
@Mapper(imports = Image.class, uses = EventLocationMapper.class)
public interface ShowMapper {
    @Named("showToShowDto")
    @Mapping(expression = "java(show.getPhoto().getImage())", target = "photo")
    @Mapping(expression = "java(show.getDuration().toMillis())", target = "duration")
    ShowDto showToShowDto(Show show);

    @IterableMapping(qualifiedByName = "showToShowDto")
    List<ShowDto> showToShowDto(List<Show> show);

    @Named("showToSimpleShowDto")
    @Mapping(expression = "java(show.getPhoto().getImage())", target = "photo")
    @Mapping(expression = "java(show.getDuration().toMillis())", target = "duration")
    SimpleShowDto showToSimpleShowDto(Show show);

    @IterableMapping(qualifiedByName = "showToSimpleShowDto")
    List<SimpleShowDto> showToSimpleShowDto(List<Show> show);

    @Named("showDtoToShow")
    @Mapping(expression = "java(new Image(null, show.getPhoto()))", target = "photo")
    @Mapping(target = "duration", ignore = true)
    Show showDtoToShow(ShowDto show);

    @IterableMapping(qualifiedByName = "showDtoToShow")
    List<Show> showDtoToShow(List<ShowDto> shows);

    @Named("simpleShowDtoToShow")
    @Mapping(expression = "java(new Image(null, show.getPhoto()))", target = "photo")
    @Mapping(target = "duration", ignore = true)
    Show simpleShowDtoToShow(SimpleShowDto show);

    @IterableMapping(qualifiedByName = "simpleShowDtoToShow")
    List<Show> simpleShowDtoToShow(List<SimpleShowDto> shows);

    @Named("showToSimplerShowDto")
    @Mapping(expression = "java(show.getPhoto().getImage())", target = "photo")
    @Mapping(expression = "java(show.getDuration().toMillis())", target = "duration")
    SimplerShowDto showToSimplerShowDto(Show show);

    @IterableMapping(qualifiedByName = "showToSimplerShowDto")
    List<SimplerShowDto> showToSimplerShowDto(List<Show> show);

}
