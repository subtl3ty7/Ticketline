package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.service.MerchandiseService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MerchandiseServiceTest implements TestData {

    @Autowired
    private MerchandiseService merchandiseService;

    private Merchandise merchandise = Merchandise.builder()
        .id(ID)
        .merchandiseProductName(FNAME)
        .merchandiseProductCode(USER_CODE)
        .premium(true)
        .price(PRICE)
        .premiumPrice(PREM_PRICE)
        .stockCount(TOTAL)
        .photo(PHOTO)
        .build();;

    @Order(1)
    @Test
    public void whenCreateMerchandiseProduct_thenMerchandiseSetWithProperties() {

        Merchandise merchandise1 = merchandiseService.createNewProduct(merchandise);
        assertAll(
            () -> assertEquals(FNAME, merchandise1.getMerchandiseProductName()),
            () -> assertTrue(merchandise1.isPremium()),
            () -> assertEquals(PRICE, merchandise1.getPrice()),
            () -> assertEquals(PREM_PRICE, merchandise1.getPremiumPrice()),
            () -> assertEquals(TOTAL, merchandise1.getStockCount())
        );
    }

    @Order(2)
    @Test
    public void givenMerchandise_whenBuyWithMoneyAsNonExistingUser_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   merchandiseService.purchaseWithMoney(merchandise, USER_CODE));
    }

    @Order(3)
    @Test
    public void givenMerchandise_whenBuyWithPointsAsNonExistingUser_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   merchandiseService.purchaseWithPremiumPoints(merchandise, USER_CODE));
    }

    @Order(4)
    @Test
    public void givenMerchandise_whenGetAll_thenMerchandiseListWithProperties() {

        List<Merchandise> merchandises = merchandiseService.findAllMerchandiseProducts();
        assertEquals(1, merchandises.size());
        Merchandise merchandise1 = merchandises.get(0);
        assertAll(
            () -> assertEquals(FNAME, merchandise1.getMerchandiseProductName()),
            () -> assertTrue(merchandise1.isPremium()),
            () -> assertEquals(PRICE, merchandise1.getPrice()),
            () -> assertEquals(PREM_PRICE, merchandise1.getPremiumPrice()),
            () -> assertEquals(TOTAL, merchandise1.getStockCount())
        );
    }

    @Order(5)
    @Test
    public void givenMerchandise_whenGetAllPremium_thenMerchandiseListWithProperties() {

        List<Merchandise> merchandises = merchandiseService.findAllMerchandisePremiumProducts();
        assertEquals(1, merchandises.size());
        Merchandise merchandise1 = merchandises.get(0);
        assertAll(
            () -> assertEquals(FNAME, merchandise1.getMerchandiseProductName()),
            () -> assertTrue(merchandise1.isPremium()),
            () -> assertEquals(PRICE, merchandise1.getPrice()),
            () -> assertEquals(PREM_PRICE, merchandise1.getPremiumPrice()),
            () -> assertEquals(TOTAL, merchandise1.getStockCount())
        );
    }

    @Order(5)
    @Test
    public void givenMerchandise_whenGetByNonExistingCode_thenNullObject() {

        assertNull(merchandiseService.findMerchandiseByMerchandiseProductCode("wrong"));
    }

}
