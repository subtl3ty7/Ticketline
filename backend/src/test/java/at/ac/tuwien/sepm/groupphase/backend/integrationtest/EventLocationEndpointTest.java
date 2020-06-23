package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EventLocationEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventLocationRepository eventLocationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private EventLocation eventLocation = EventLocation.builder()
        .id(ID)
        .name(FNAME)
        .eventLocationDescription(DESC)
        .capacity(TOTAL)
        .street(STREET)
        .city(CITY)
        .country(COUNTRY)
        .plz(PLZ)
        .sections(SECTIONS)
        .build();

    @BeforeEach
    public void beforeEach() {
        eventLocationRepository.deleteAll();
        eventLocation = EventLocation.builder()
            .id(ID)
            .name(FNAME)
            .eventLocationDescription(DESC)
            .capacity(TOTAL)
            .street(STREET)
            .city(CITY)
            .country(COUNTRY)
            .plz(PLZ)
            .build();
    }

    @Test
    public void givenNothing_whenGetAllAsAdmin_then200andEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(LOCATION_BASE_URI + "/all")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<EventLocationDto> eventLocationDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            EventLocationDto[].class));

        assertEquals(0, eventLocationDtos.size());
    }

    @Test
    public void givenUserLoggedIn_whenGetAll_then500() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(LOCATION_BASE_URI + "/all")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void givenLocation_whenGetAll_thenListWithSizeOneAndLocationWithAllProperties() throws Exception {
        eventLocationRepository.save(eventLocation);

        MvcResult mvcResult = this.mockMvc.perform(get(LOCATION_BASE_URI + "/all")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        List<EventLocationDto> eventLocationDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            EventLocationDto[].class));

        assertEquals(1, eventLocationDtos.size());
        EventLocationDto eventLocationDto = eventLocationDtos.get(0);
        assertAll(
            () -> assertEquals(FNAME, eventLocationDto.getName()),
            () -> assertEquals(DESC, eventLocationDto.getEventLocationDescription()),
            () -> assertEquals(STREET, eventLocationDto.getStreet()),
            () -> assertEquals(CITY, eventLocationDto.getCity()),
            () -> assertEquals(COUNTRY, eventLocationDto.getCountry()),
            () -> assertEquals(PLZ, eventLocationDto.getPlz()),
            () -> assertEquals(TOTAL, eventLocationDto.getCapacity())
        );
    }

    @Test
    public void givenLocation_whenGetByParam_then200andListWithOneElement() throws Exception {
        eventLocationRepository.save(eventLocation);

        MvcResult mvcResult = this.mockMvc.perform(get(LOCATION_BASE_URI)
            .param("name", eventLocation.getName()).param("size", "0")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<EventLocationDto> eventLocationDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            EventLocationDto[].class));

        assertEquals(1, eventLocationDtos.size());
    }
}
