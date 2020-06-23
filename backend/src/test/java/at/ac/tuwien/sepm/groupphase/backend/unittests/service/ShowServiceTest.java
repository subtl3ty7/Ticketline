package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventTypeEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.repository.ShowRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ShowService;
import org.junit.jupiter.api.Test;
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
public class ShowServiceTest implements TestData {

    @Autowired
    private ShowService showService;

    @Autowired
    private ShowRepository showRepository;

    @Test
    public void givenShow_whenGetByWrongParams_thenListWithNoShowEntities() {
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

        List<Show> shows = showService.findShowsAdvanced(NAME + "a", EventTypeEnum.valueOf(TYP1.toString()).ordinal(), EventCategoryEnum.valueOf(CAT1.toString()).ordinal(), START, END, DURATION, PRICE.intValue(), 0);
        assertEquals(0, shows.size());
    }
}
