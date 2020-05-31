package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.MerchandiseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface MerchandiseMapper {

    @Named(value = "merchandiseToMerchandiseDto")
    MerchandiseDto merchandiseToMerchandiseDto(Merchandise merchandise);

    @IterableMapping(qualifiedByName = "merchandiseToMerchandiseDto")
    List<MerchandiseDto> merchandiseToMerchandiseDto(List<Merchandise> merchandise);





}
