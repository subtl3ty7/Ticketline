package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.repository.MerchandiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class MerchandiseRepositoryTest implements TestData {

    @Autowired
    private MerchandiseRepository merchandiseRepository;

    @Test
    public void givenNothing_whenSaveMerchandise_thenFindListWithOneElementAndFindMerchandiseById() {
        Merchandise merchandise = Merchandise.builder()
            .id(ID)
            .merchandiseProductName(FNAME)
            .merchandiseProductCode(USER_CODE)
            .premium(true)
            .price(PRICE)
            .premiumPrice(PREM_PRICE)
            .stockCount(TOTAL)
            .photo(PHOTO)
            .build();

        merchandiseRepository.save(merchandise);

        assertAll(
            () -> assertEquals(1, merchandiseRepository.findAll().size()),
            () -> assertNotNull(merchandiseRepository.findMerchandiseById(merchandise.getId()))
        );
    }
}
