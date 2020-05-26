package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
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
public class NewsEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private News news = News.builder()
        .id(ID)
        .newsCode(USER_CODE)
        .title(TEST_NEWS_TITLE)
        .publishedAt(TEST_NEWS_PUBLISHED_AT)
        .stopsBeingRelevantAt(TEST_NEWS_PUBLISHED_AT)
        .summary(TEST_NEWS_SUMMARY)
        .text(TEST_NEWS_TEXT)
        .author(FNAME)
        .photo(PHOTO)
        .build();

    private AbstractUser abstractUser = Customer.CustomerBuilder.aCustomer()
        .withId(ID)
        .withUserCode(USER_CODE)
        .withFirstName(FNAME)
        .withLastName(LNAME)
        .withEmail(DEFAULT_USER)
        .withPassword(PASS)
        .withBirthday(BIRTHDAY)
        .withCreatedAt(CRE)
        .withUpdatedAt(UPD)
        .withIsBlocked(false)
        .withIsLogged(true)
        .withPoints(POINTS)
        .build();

    /*@BeforeEach
    public void beforeEach() {
        newsRepository.deleteAll();
        news = News.builder()
            .id(ID)
            .newsCode(USER_CODE)
            .title(TEST_NEWS_TITLE)
            .publishedAt(TEST_NEWS_PUBLISHED_AT)
            .stopsBeingRelevantAt(TEST_NEWS_PUBLISHED_AT)
            .summary(TEST_NEWS_SUMMARY)
            .text(TEST_NEWS_TEXT)
            .author(FNAME)
            .photo(PHOTO)
            .build();

        userRepository.deleteAll();
        abstractUser = Customer.CustomerBuilder.aCustomer()
            .withId(ID)
            .withUserCode(USER_CODE)
            .withFirstName(FNAME)
            .withLastName(LNAME)
            .withEmail(DEFAULT_USER)
            .withPassword(PASS)
            .withBirthday(BIRTHDAY)
            .withCreatedAt(CRE)
            .withUpdatedAt(UPD)
            .withIsBlocked(false)
            .withIsLogged(true)
            .withPoints(POINTS)
            .build();
    }*/

    @Order(1)
    @Test
    public void givenNothing_whenPublishNewsAsCustomer_then500() throws Exception{
        NewsDto newsDto = newsMapper.newsToNewsDto(news);
        String body = objectMapper.writeValueAsString(newsDto);

        MvcResult mvcResult = this.mockMvc.perform(post(NEWS_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Order(2)
    @Test
    public void givenNews_whenGetByNewsCode_then200AndNewsWithAllProperties() throws Exception{
        userRepository.save(abstractUser);
        newsRepository.save(news);

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI + "/" + USER_CODE)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        NewsDto newsDto = objectMapper.readValue(response.getContentAsString(), NewsDto.class);
        assertAll(
            () -> assertEquals(USER_CODE, newsDto.getNewsCode()),
            () -> assertEquals(TEST_NEWS_TITLE, newsDto.getTitle()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, newsDto.getPublishedAt()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, newsDto.getStopsBeingRelevantAt()),
            () -> assertEquals(TEST_NEWS_SUMMARY, newsDto.getSummary()),
            () -> assertEquals(TEST_NEWS_TEXT, newsDto.getText()),
            () -> assertEquals(FNAME, newsDto.getAuthor())
        );
    }

    @Order(3)
    @Test
    public void givenNews_whenGetLatestNews_then200AndNewsListWith1Element() throws Exception{
        //newsRepository.save(news);

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI + "/latest?limit=1")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        List<NewsDto> newsDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            NewsDto[].class));
        assertEquals(1, newsDtos.size());

        newsRepository.deleteAll();
        userRepository.deleteAll();
    }

}
