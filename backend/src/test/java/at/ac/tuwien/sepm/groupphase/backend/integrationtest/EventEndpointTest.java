package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
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
        .category(CAT1)
        .type(TYP1)
        .startsAt(START)
        .endsAt(END)
        .duration(DURATION)
        .prices(PRICES)
        .totalTicketsSold(TOTAL)
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
            .category(CAT1)
            .type(TYP1)
            .startsAt(START)
            .endsAt(END)
            .duration(DURATION)
            .prices(PRICES)
            .totalTicketsSold(TOTAL)
            .photo(PHOTO)
            .build();
    }

    @Test
    public void givenEvent_whenGetAll_then200andListWith1Element() throws Exception {
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENT_BASE_URI + "/all")
            .param("size", "1")
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

        MvcResult mvcResult = this.mockMvc.perform(get(EVENT_TOP10)
            .param("category", CAT1.toString())
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
    public void givenEvent_whenGetAllCategories_then200andListWith1Element() throws Exception {
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENT_BASE_URI + "/eventCategories")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<EventCategoryEnum> eventCategoryEnums = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            EventCategoryEnum[].class));

        assertEquals(11, eventCategoryEnums.size());
    }

    @Test
    public void givenEvent_whenGetAllTypes_then200andListWith1Element() throws Exception {
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENT_BASE_URI + "/eventTypes")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<EventTypeEnum> eventTypeEnums = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            EventTypeEnum[].class));

        assertEquals(4, eventTypeEnums.size());
    }

    @Test
    public void givenEvent_whenGetEventByCode_then200AndEventWithProperties() throws Exception {
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

        DetailedEventDto detailedEventDto = objectMapper.readValue(response.getContentAsString(), DetailedEventDto.class);
        assertAll(
            () -> assertEquals(USER_CODE, detailedEventDto.getEventCode()),
            () -> assertEquals(NAME, detailedEventDto.getName()),
            () -> assertEquals(DESC, detailedEventDto.getDescription()),
            () -> assertEquals(START, detailedEventDto.getStartsAt()),
            () -> assertEquals(END, detailedEventDto.getEndsAt()),
            () -> assertEquals(PRICES.get(0), detailedEventDto.getStartPrice()),
            () -> assertEquals(TOTAL, detailedEventDto.getTotalTicketsSold())
        );
    }

    @Test
    public void givenEvent_whenGetEventByName_then200AndEventWithProperties() throws Exception {
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENT_BASE_URI)
            .param("name", event.getName()).param("size", "0")
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
        SimpleEventDto simpleEventDto = simpleEventDtos.get(0);
        assertAll(
            () -> assertEquals(USER_CODE, simpleEventDto.getEventCode()),
            () -> assertEquals(NAME, simpleEventDto.getName()),
            () -> assertEquals(DESC, simpleEventDto.getDescription()),
            () -> assertEquals(START, simpleEventDto.getStartsAt()),
            () -> assertEquals(END, simpleEventDto.getEndsAt()),
            () -> assertEquals(PRICES.get(0), simpleEventDto.getStartPrice())
        );
    }

    @Test
    public void givenEvent_whenDeleteEventByCode_then204AndEmptyList() throws Exception {
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(delete(EVENT_BASE_URI + "/" + event.getEventCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus()),
            () -> assertEquals(0, eventRepository.findAll().size())
        );
    }

}
