package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.datagenerator.EventDataGenerator;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.util.Resources;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    private Resources resources;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    private DetailedTicketDto detailedTicketDto;

    @Test
    @Order(1)
    public void givenNothing_whenBuyTicket_then200AndTicketList_AndTicketPurchasedTrue() throws Exception{

        userRepository.save(USER_TICKET);
        EventDataGenerator eventDataGenerator = new EventDataGenerator(sectionRepository, seatRepository,
            showRepository, eventService, eventLocationService, entityManagerFactory, artistRepository, artistService, resources, eventLocationRepository);
        eventDataGenerator.generate();

        detailedTicketDto = ticketMapper.ticketToDetailedTicketDto(
            Ticket.builder()
                .ticketId(ID)
                .userCode(USER_CODE)
                .isPurchased(false)
                .isReserved(false)
                .purchaseDate(START)
                .seat(seatRepository.findSeatById(6L))
                .userCode(USER_CODE_TICKET)
                .price(PRICE)
                .show(showRepository.findShowById(6L))
                .event(eventRepository.findEventById(1L))
                .build()
        );

        List<DetailedTicketDto> ticketDtos = new ArrayList<>();
        ticketDtos.add(detailedTicketDto);
        String body = objectMapper.writeValueAsString(ticketDtos);

        MvcResult mvcResult = this.mockMvc.perform(post(TICKETS_BASE_URI + "/purchasing")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_TICKET, USER_ROLES)))
            //.andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<SimpleTicketDto> ticketDtos1 = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleTicketDto[].class));
        assertEquals(1, ticketDtos1.size());
        assertTrue(ticketDtos1.get(0).isPurchased());
    }

    @Test
    @Order(2)
    public void givenNothing_whenReserveTicket_then201AndTicketList_AndTicketReservedTrue() throws Exception{
        detailedTicketDto = ticketMapper.ticketToDetailedTicketDto(
            Ticket.builder()
                .ticketId(2L)
                .userCode("code12")
                .isPurchased(false)
                .isReserved(false)
                .purchaseDate(START)
                .seat(seatRepository.findSeatById(7L))
                .userCode(USER_CODE_TICKET)
                .price(PRICE)
                .show(showRepository.findShowById(7L))
                .event(eventRepository.findEventById(1L))
                .build()
        );

        List<DetailedTicketDto> ticketDtos = new ArrayList<>();
        ticketDtos.add(detailedTicketDto);
        String body = objectMapper.writeValueAsString(ticketDtos);

        MvcResult mvcResult = this.mockMvc.perform(post(TICKETS_BASE_URI + "/reserving")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_TICKET, USER_ROLES)))
            //.andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<SimpleTicketDto> ticketDtos1 = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleTicketDto[].class));
        assertEquals(1, ticketDtos1.size());
        assertTrue(ticketDtos1.get(0).isReserved());
    }

    @Test
    @Order(3)
    public void givenReservedTicket_whenCancel_then204AndTicketNotCancelled() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(delete(TICKETS_BASE_URI + "/cancelingR/" +
            ticketRepository.findTicketByTicketId(2L).getTicketCode())
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_TICKET, USER_ROLES)))
            //.andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus()),
            () -> assertTrue(ticketRepository.findTicketByTicketId(2L).isCancelled()),
            () -> assertFalse(ticketRepository.findTicketByTicketId(2L).isReserved())
        );
    }

    @Order(4)
    @Test
    public void givenReservedTicket_whenPurchase_then201AndTicketListWithPurchasedTickets() throws Exception{
        detailedTicketDto = ticketMapper.ticketToDetailedTicketDto(
            Ticket.builder()
                .ticketId(3L)
                .userCode("code13")
                .isPurchased(false)
                .isReserved(false)
                .purchaseDate(START)
                .seat(seatRepository.findSeatById(7L))
                .userCode(USER_CODE_TICKET)
                .price(PRICE)
                .show(showRepository.findShowById(7L))
                .event(eventRepository.findEventById(1L))
                .build()
        );
        List<DetailedTicketDto> ticketDtos = new ArrayList<>();
        ticketDtos.add(detailedTicketDto);
        String body = objectMapper.writeValueAsString(ticketDtos);
        this.mockMvc.perform(post(TICKETS_BASE_URI + "/reserving")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_TICKET, USER_ROLES)));

        MvcResult mvcResult = this.mockMvc.perform(post(TICKETS_BASE_URI + "/purchasingR/" +
            ticketRepository.findTicketByTicketId(3L).getTicketCode())
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_TICKET, USER_ROLES)))
            //.andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        SimpleTicketDto simpleTicketDto = objectMapper.readValue(response.getContentAsString(),
            SimpleTicketDto.class);

        assertAll(
            () ->  assertTrue(simpleTicketDto.isPurchased()),
            () ->  assertFalse(simpleTicketDto.isReserved())
        );

    }

    @Test
    @Order(5)
    public void givenTickets_whenGetTicketsByUserCode_then200AndTicketListWith2Elements() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(TICKETS_BASE_URI + "/" + USER_CODE_TICKET)
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_TICKET, USER_ROLES)))
            //.andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<SimpleTicketDto> ticketDtos1 = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleTicketDto[].class));
        assertEquals(2, ticketDtos1.size());
    }

    @Test
    @Order(6)
    public void givenPurchasedTicket_whenCancelPurchase_then204AndTicketCancelled() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(delete(TICKETS_BASE_URI + "/cancelingP/" +
            ticketRepository.findTicketByTicketId(ID).getTicketCode())
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_TICKET, USER_ROLES)))
            //.andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus()),
            () -> assertTrue(ticketRepository.findTicketByTicketId(ID).isCancelled()),
            () -> assertFalse(ticketRepository.findTicketByTicketId(ID).isPurchased())
        );

        invoiceRepository.deleteAll();
        ticketRepository.deleteAll();
    }

}
