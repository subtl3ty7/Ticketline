package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShowDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ShowRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ShowEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private Show show = Show.builder()
        .id(ID)
        .startsAt(START)
        .endsAt(END)
        .eventCode(USER_CODE)
        .ticketsAvailable(TOTAL)
        .ticketsSold(TOTAL)
        .description(DESC)
        .photo(PHOTO)
        .eventCategory(CAT1)
        .eventType(TYP1)
        .duration(DURATION)
        .eventName(NAME)
        .price(PRICE)
        .build();

    @BeforeEach
    public void beforeEach() {
        showRepository.deleteAll();
        show = Show.builder()
            .id(ID)
            .startsAt(START)
            .endsAt(END)
            .eventCode(USER_CODE)
            .ticketsAvailable(TOTAL)
            .ticketsSold(TOTAL)
            .description(DESC)
            .photo(PHOTO)
            .eventCategory(CAT1)
            .eventType(TYP1)
            .duration(DURATION)
            .eventName(NAME)
            .price(PRICE)
            .build();
    }

    @Test
    public void givenShow_whenGetShowsByWrongParams_then200AndEmptyShowList() throws Exception{
        showRepository.save(show);

        MvcResult mvcResult = this.mockMvc.perform(get(SHOW_BASE_URI)
            .param("eventName", show.getEventName() + "a")
            .param("type", show.getEventType().toString())
            .param("category", show.getEventCategory().toString())
            .param("startsAt", "")
            .param("endsAt", "")
            .param("duration", "")
            .param("price", "")
            .param("size", "0")
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<ShowDto> showDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(), ShowDto[].class));
        assertEquals(0, showDtos.size());
    }
}
