package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ShowMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        .eventLocation(LOCATIONS)
        .build();

}
