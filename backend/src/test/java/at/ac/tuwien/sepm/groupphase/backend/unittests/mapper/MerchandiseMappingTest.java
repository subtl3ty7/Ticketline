package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.MerchandiseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.MerchandiseMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class MerchandiseMappingTest implements TestData {

    @Autowired
    private MerchandiseMapper merchandiseMapper;

    private final Merchandise merchandise = Merchandise.builder()
        .id(ID)
        .merchandiseProductName(FNAME)
        .merchandiseProductCode(USER_CODE)
        .premium(true)
        .price(PRICE)
        .premiumPrice(PREM_PRICE)
        .stockCount(TOTAL)
        .photo(PHOTO)
        .build();

    @Test
    public void shouldMapMerchandiseToMerchandiseDTO() {
        MerchandiseDto merchandiseDto = merchandiseMapper.merchandiseToMerchandiseDto(merchandise);
        assertAll(
            () -> assertEquals(ID, merchandiseDto.getId()),
            () -> assertEquals(USER_CODE, merchandiseDto.getMerchandiseProductCode()),
            () -> assertEquals(FNAME, merchandiseDto.getMerchandiseProductName()),
            () -> assertTrue(merchandiseDto.isPremium()),
            () -> assertEquals(PRICE, merchandiseDto.getPrice()),
            () -> assertEquals(PREM_PRICE, merchandiseDto.getPremiumPrice()),
            () -> assertEquals(TOTAL, merchandiseDto.getStockCount())
        );
    }

    @Test
    public void shouldMapMerchandiseListToMerchandiseDTOList() {
        List<Merchandise> merchandises = new ArrayList<>();
        merchandises.add(merchandise);
        merchandises.add(merchandise);
        List<MerchandiseDto> merchandiseDtos = merchandiseMapper.merchandiseToMerchandiseDto(merchandises);
        MerchandiseDto merchandiseDto = merchandiseDtos.get(0);
        assertAll(
            () -> assertEquals(ID, merchandiseDto.getId()),
            () -> assertEquals(USER_CODE, merchandiseDto.getMerchandiseProductCode()),
            () -> assertEquals(FNAME, merchandiseDto.getMerchandiseProductName()),
            () -> assertTrue(merchandiseDto.isPremium()),
            () -> assertEquals(PRICE, merchandiseDto.getPrice()),
            () -> assertEquals(PREM_PRICE, merchandiseDto.getPremiumPrice()),
            () -> assertEquals(TOTAL, merchandiseDto.getStockCount())
        );
    }

    @Test
    public void shouldMapMerchandiseDTOToMerchandise() {
        MerchandiseDto merchandiseDto = merchandiseMapper.merchandiseToMerchandiseDto(merchandise);
        Merchandise merchandise1 = merchandiseMapper.merchandiseDtoToMerchandise(merchandiseDto);
        assertAll(
            () -> assertEquals(ID, merchandise1.getId()),
            () -> assertEquals(USER_CODE, merchandise1.getMerchandiseProductCode()),
            () -> assertEquals(FNAME, merchandise1.getMerchandiseProductName()),
            () -> assertTrue(merchandise1.isPremium()),
            () -> assertEquals(PRICE, merchandise1.getPrice()),
            () -> assertEquals(PREM_PRICE, merchandise1.getPremiumPrice()),
            () -> assertEquals(TOTAL, merchandise1.getStockCount())
        );
    }
}
