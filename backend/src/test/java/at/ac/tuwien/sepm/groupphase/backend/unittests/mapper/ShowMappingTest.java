package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ShowMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ShowMappingTest implements TestData {

    private final Show show = Show.builder()
        .id(ID)
        .startsAt(START)
        .endsAt(END)
        .eventCode(USER_CODE)
        .ticketsAvailable(TOTAL)
        .ticketsSold(TOTAL)
        .build();

    @Autowired
    private ShowMapper showMapper;

    @Test
    public void shouldMapShowToShowDto() {
        ShowDto showDto = showMapper.showToShowDto(show);
        assertAll(
            () -> assertEquals(ID, showDto.getId()),
            () -> assertEquals(START, showDto.getStartsAt()),
            () -> assertEquals(END, showDto.getEndsAt()),
            () -> assertEquals(USER_CODE, showDto.getEventCode()),
            () -> assertEquals(TOTAL, showDto.getTicketsAvailable()),
            () -> assertEquals(TOTAL, showDto.getTicketsAvailable())
        );
    }

    @Test
    public void shouldMapShowListToShowDtoList() {
        List<Show> shows = new ArrayList<>();
        shows.add(show);
        shows.add(show);
        List<ShowDto> showDtos = showMapper.showToShowDto(shows);
        ShowDto showDto = showDtos.get(0);
        assertAll(
            () -> assertEquals(ID, showDto.getId()),
            () -> assertEquals(START, showDto.getStartsAt()),
            () -> assertEquals(END, showDto.getEndsAt()),
            () -> assertEquals(USER_CODE, showDto.getEventCode()),
            () -> assertEquals(TOTAL, showDto.getTicketsAvailable()),
            () -> assertEquals(TOTAL, showDto.getTicketsAvailable())
        );
    }

}
