package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.MerchandiseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Image;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import jdk.jfr.Name;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(imports = Image.class)
public interface MerchandiseMapper {

    @Named(value = "merchandiseToMerchandiseDto")
    @Mapping(expression = "java(merchandise.getPhoto().getImage())", target = "photo")
    MerchandiseDto merchandiseToMerchandiseDto(Merchandise merchandise);

    @IterableMapping(qualifiedByName = "merchandiseToMerchandiseDto")
    List<MerchandiseDto> merchandiseToMerchandiseDto(List<Merchandise> merchandise);

    @Named(value = "merchandiseDtoToMerchandise")
    @Mapping(expression = "java(new Image(null, merchandise.getPhoto()))", target = "photo")
    Merchandise merchandiseDtoToMerchandise(MerchandiseDto merchandise);

    @IterableMapping(qualifiedByName = "merchandiseDtoToMerchandise")
    List<Merchandise> merchandiseDtoListToMerchandiseList(List<MerchandiseDto> merchandise);




}
