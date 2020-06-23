package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.MerchandiseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.MerchandiseMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.MerchandiseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MerchandiseEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MerchandiseRepository merchandiseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MerchandiseMapper merchandiseMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private AbstractUser abstractUser = Customer.CustomerBuilder.aCustomer()
        .withId(ID)
        .withUserCode(USER_CODE_MERCH)
        .withFirstName(FNAME)
        .withLastName(LNAME)
        .withEmail(EMAIL_MERCH)
        .withPassword(PASS)
        .withBirthday(BIRTHDAY)
        .withCreatedAt(CRE)
        .withUpdatedAt(UPD)
        .withIsBlocked(false)
        .withIsLogged(true)
        .withPoints(POINTS)
        .build();

    private final Merchandise merchandise = Merchandise.builder()
        .id(ID)
        .merchandiseProductName(FNAME)
        .merchandiseProductCode(USER_CODE)
        .premium(true)
        .price(PRICE)
        .premiumPrice(PREM_PRICE)
        .stockCount(TOTAL)
        .photo(PHOTO)
        .build();

    @Order(1)
    @Test
    public void givenMerchandise_whenBuyWithPoints_then201AndMerchandiseSavedWithProperties() throws Exception{
        userRepository.save(abstractUser);
        merchandiseRepository.save(merchandise);
        MerchandiseDto merchandiseDto = merchandiseMapper.merchandiseToMerchandiseDto(merchandise);
        String body = objectMapper.writeValueAsString(merchandiseDto);

        MvcResult mvcResult = this.mockMvc.perform(post(MERCHANDISE_BASE_URI
            + "/purchasingWithPremiumPoints/" + abstractUser.getUserCode())
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_MERCH, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        MerchandiseDto merchandiseDto1 = objectMapper.readValue(response.getContentAsString(), MerchandiseDto.class);
        assertAll(
            () -> assertEquals(ID, merchandiseDto1.getId()),
            () -> assertEquals(FNAME, merchandiseDto1.getMerchandiseProductName()),
            () -> assertTrue(merchandiseDto1.isPremium()),
            () -> assertEquals(PRICE, merchandiseDto1.getPrice()),
            () -> assertEquals(PREM_PRICE, merchandiseDto1.getPremiumPrice()),
            () -> assertEquals(TOTAL - 1, merchandiseDto1.getStockCount())
        );
    }

    @Order(2)
    @Test
    public void givenMerchandise_whenBuyWithMoney_then201AndMerchandiseSavedWithProperties() throws Exception{
        MerchandiseDto merchandiseDto = merchandiseMapper.merchandiseToMerchandiseDto(merchandise);
        String body = objectMapper.writeValueAsString(merchandiseDto);

        MvcResult mvcResult = this.mockMvc.perform(post(MERCHANDISE_BASE_URI
            + "/purchasingWithMoney/" + abstractUser.getUserCode())
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_MERCH, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        MerchandiseDto merchandiseDto1 = objectMapper.readValue(response.getContentAsString(), MerchandiseDto.class);
        assertAll(
            () -> assertEquals(FNAME, merchandiseDto1.getMerchandiseProductName()),
            () -> assertTrue(merchandiseDto1.isPremium()),
            () -> assertEquals(PRICE, merchandiseDto1.getPrice()),
            () -> assertEquals(PREM_PRICE, merchandiseDto1.getPremiumPrice()),
            () -> assertEquals(TOTAL - 2, merchandiseDto1.getStockCount())
        );
    }

    @Order(3)
    @Test
    public void givenMerchandise_whenGetAll_then200AndListWith1Element() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(MERCHANDISE_BASE_URI + "/all")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_MERCH, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        List<MerchandiseDto> merchandiseDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            MerchandiseDto[].class));
        assertEquals(1, merchandiseDtos.size());
    }

    @Order(4)
    @Test
    public void givenMerchandise_whenGetPremium_then200AndListWith1Element() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(MERCHANDISE_BASE_URI + "/premium")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_MERCH, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        List<MerchandiseDto> merchandiseDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            MerchandiseDto[].class));
        assertEquals(1, merchandiseDtos.size());
    }

    @Order(5)
    @Test
    public void givenMerchandise_whenGetByCode_then200() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(MERCHANDISE_BASE_URI + "/" + merchandise.getMerchandiseProductCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMAIL_MERCH, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        userRepository.deleteAll();
        merchandiseRepository.deleteAll();
        invoiceRepository.deleteAll();
    }


}
