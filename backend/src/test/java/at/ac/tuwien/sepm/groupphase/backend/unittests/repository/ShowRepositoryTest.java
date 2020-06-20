package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Image;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class ShowRepositoryTest implements TestData {

    @Autowired
    ShowRepository showRepository;

    @Test
    public void givenNothing_whenSaveShow_thenFindListWithOneElementAndFindShowById() {
        Show show = Show.builder()
            .id(ID)
            .startsAt(START)
            .endsAt(END)
            .eventCode(USER_CODE)
            .ticketsAvailable(TOTAL)
            .ticketsSold(TOTAL)
            .eventCategory(CAT1)
            .eventType(TYP1)
            .description(DESC)
            .photo(PHOTO)
            .price(PRICE)
            .eventName(NAME)
            .duration(DURATION)
            .build();

        showRepository.save(show);

        assertAll(
            () -> assertEquals(1, showRepository.findAll().size()),
            () -> assertNotNull(showRepository.findById(show.getId()))
        );
    }
}
