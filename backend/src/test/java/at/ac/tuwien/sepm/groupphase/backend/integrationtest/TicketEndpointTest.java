package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.datagenerator.EventDataGenerator;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TicketEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private EventLocationRepository eventLocationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventLocationService eventLocationService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ResourceLoader resourceLoader;

    private DetailedTicketDto detailedTicketDto;

    @BeforeEach
    public void beforeEach() {
        ticketRepository.deleteAll();
        userRepository.save(USER_TICKET);
        EventDataGenerator eventDataGenerator = new EventDataGenerator(sectionRepository, seatRepository,
            showRepository, eventService, eventLocationService, entityManagerFactory, resourceLoader);
        eventDataGenerator.generate();

        detailedTicketDto = DetailedTicketDto.DetailedTicketDtoBuilder.aDetailedTicketDto(
            ID, USER_CODE, false, false, START, seatRepository.findSeatById(6L), USER_CODE, TOTAL, showRepository.findShowById(6L)).build();
    }


    @Test
    public void whenBuyTicket_then201AndTicketListWithBoughtTickets_AndTicketPurchasedTrue() throws Exception{

        List<DetailedTicketDto> ticketDtos = new ArrayList<>();
        ticketDtos.add(detailedTicketDto);
        String body = objectMapper.writeValueAsString(ticketDtos);

        MvcResult mvcResult = this.mockMvc.perform(post(TICKETS_BASE_URI + "/purchase")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<SimpleTicketDto> ticketDtos1 = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleTicketDto[].class));
        assertEquals(1, ticketDtos1.size());
        assertTrue(ticketDtos1.get(0).isPurchased());
    }

    @AfterEach
    public void afterAll(){
        ticketRepository.deleteAll();
        seatRepository.deleteAll();
        showRepository.deleteAll();
        sectionRepository.deleteAll();
        eventLocationRepository.deleteAll();
        eventRepository.deleteAll();
        userRepository.deleteAll();
    }


}
