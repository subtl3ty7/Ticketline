package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EventEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private Event event = Event.builder()
        .id(ID)
        .eventCode(USER_CODE)
        .name(NAME)
        .description(DESC)
        .category(CAT)
        .type(TYP)
        .startsAt(START)
        .endsAt(END)
        .prices(PRICES)
        .totalTicketsSold(TOTAL)
        .artists(ARTISTS)
        .shows(SHOWS)
        .photo(PHOTO)
        .build();

    @BeforeEach
    public void beforeEach() {
        eventRepository.deleteAll();
        event = Event.builder()
            .id(ID)
            .eventCode(USER_CODE)
            .name(NAME)
            .description(DESC)
            .category(CAT)
            .type(TYP)
            .startsAt(START)
            .endsAt(END)
            .prices(PRICES)
            .totalTicketsSold(TOTAL)
            .artists(ARTISTS)
            .shows(SHOWS)
            .photo(PHOTO)
            .build();
    }

    @Test
    public void givenUserLoggedIn_whenGetTop10_then200andEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(EVENT_TOP10)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<SimpleEventDto> simpleEventDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleEventDto[].class));

        assertEquals(0, simpleEventDtos.size());
    }

    @Test
    public void givenNothing_whenPostAsUser_then403() throws Exception {
        DetailedEventDto detailedEventDto = eventMapper.eventToDetailedEventDto(event);
        String body = objectMapper.writeValueAsString(detailedEventDto);

        MvcResult mvcResult = this.mockMvc.perform(post(EVENT_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void givenEvent_whenGetTop10_then200andListWith1Element() throws Exception {
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENT_TOP10)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<SimpleEventDto> simpleEventDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleEventDto[].class));

        assertEquals(1, simpleEventDtos.size());
    }

    @Test
    public void givenEvent_whenGetTop10ByCategory_then200andListWith1Element() throws Exception {
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENT_TOP10 + "/" + CAT)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<SimpleEventDto> simpleEventDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleEventDto[].class));

        assertEquals(1, simpleEventDtos.size());
    }

    @Test
    public void givenEvent_whenGetEventByCode_then200AndEventWithAllProperties() throws Exception {
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENT_BASE_URI + "/" + event.getEventCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        SimpleEventDto simpleEventDto = (objectMapper.readValue(response.getContentAsString(), SimpleEventDto.class));
        assertAll(
            () -> assertEquals(USER_CODE, simpleEventDto.getEventCode()),
            () -> assertEquals(NAME, simpleEventDto.getName()),
            () -> assertEquals(DESC, simpleEventDto.getDescription()),
            () -> assertEquals(START, simpleEventDto.getStartsAt()),
            () -> assertEquals(END, simpleEventDto.getEndsAt()),
            () -> assertEquals(PRICES.get(0), simpleEventDto.getStartPrice())
        );
    }

   /* @Test
    public void givenNothing_whenPost_thenEventWithAllSetProperties() throws Exception {
        DetailedEventDto detailedEventDto = eventMapper.eventToDetailedEventDto(event);
        String body = objectMapper.writeValueAsString(detailedEventDto);

        MvcResult mvcResult = this.mockMvc.perform(post(EVENT_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        DetailedEventDto detailedEventDto1 = objectMapper.readValue(response.getContentAsString(), DetailedEventDto.class);
        assertAll(
            () -> assertEquals(USER_CODE, detailedEventDto1.getEventCode()),
            () -> assertEquals(NAME, detailedEventDto1.getName()),
            () -> assertEquals(DESC, detailedEventDto1.getDescription()),
            () -> assertEquals(TYP, detailedEventDto1.getType()),
            () -> assertEquals(CAT, detailedEventDto1.getCategory()),
            () -> assertEquals(START, detailedEventDto1.getStartsAt()),
            () -> assertEquals(END, detailedEventDto1.getEndsAt()),
            () -> assertEquals(PRICES, detailedEventDto1.getPrices()),
            () -> assertEquals(PRICES.get(0), detailedEventDto1.getStartPrice()),
            () -> assertEquals(TOTAL, detailedEventDto1.getTotalTicketsSold()),
            () -> assertEquals(ARTISTS, detailedEventDto1.getArtists()),
            () -> assertEquals(SHOWS, detailedEventDto1.getShows())
        );
    }

    @Test
    public void givenEvent_whenDeleteEventByCode_then200AndEmptyList() throws Exception {
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(delete(EVENT_BASE_URI + "/"
            + eventRepository.findAll().get(0).getEventCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType()),
            () -> assertEquals(0, eventRepository.findAll().size())
        );

    } */


}
