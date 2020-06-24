package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
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
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private AbstractUser abstractUser = Customer.CustomerBuilder.aCustomer()
        .withId(ID)
        .withUserCode("code12")
        .withFirstName(FNAME)
        .withLastName(LNAME)
        .withEmail("1" + DEFAULT_USER)
        .withPassword(PASS)
        .withBirthday(BIRTHDAY)
        .withCreatedAt(CRE)
        .withUpdatedAt(UPD)
        .withIsBlocked(false)
        .withIsLogged(true)
        .withPoints(POINTS)
        .build();

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
        .seenBy(List.of((Customer)abstractUser))
        .build();

    @Order(1)
    @Test
    public void givenNothing_whenPublishNewsAsCustomer_then500() throws Exception{
        newsRepository.deleteAll();
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
    public void givenNothing_whenPublishNewsAsAdmin_then201() throws Exception{
        userRepository.save(abstractUser);
        NewsDto newsDto = newsMapper.newsToNewsDto(news);
        String body = objectMapper.writeValueAsString(newsDto);

        MvcResult mvcResult = this.mockMvc.perform(post(NEWS_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
    }

    @Order(3)
    @Test
    public void givenNews_whenGetAllAsCustomer_then500() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI + "/all")
            .param("size", "0")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Order(4)
    @Test
    public void givenNews_whenGetByParamAsCustomer_then500() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI)
            .param("newsCode", news.getNewsCode())
            .param("title", news.getTitle())
            .param("author", news.getAuthor())
            .param("startRange", news.getPublishedAt().toString())
            .param("endRange", news.getPublishedAt().toString())
            .param("size", "0")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Order(5)
    @Test
    public void givenNews_whenGetAllAsAdmin_then200AndListWith1Element() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI + "/all")
            .param("size", "0")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        List<SimpleNewsDto> simpleNewsDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleNewsDto[].class));
        assertEquals(1, simpleNewsDtos.size());
    }

    @Order(6)
    @Test
    public void givenNews_whenGetUnseen_then200AndListWith1Element() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI + "/unseen")
            .param("limit", "1")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken("1" + DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        List<SimpleNewsDto> simpleNewsDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleNewsDto[].class));
        assertEquals(1, simpleNewsDtos.size());
    }

    @Order(7)
    @Test
    public void givenNews_whenGetSeen_then200AndListWith0Element() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI + "/seen")
            .param("limit", "1")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken("1" + DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        List<SimpleNewsDto> simpleNewsDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleNewsDto[].class));
        assertEquals(0, simpleNewsDtos.size());
    }

    @Order(8)
    @Test
    public void givenNews_whenGetLatestNews_then200AndNewsListWith1Element() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI + "/latest?limit=1")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
        List<NewsDto> newsDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            NewsDto[].class));
        assertEquals(1, newsDtos.size());

        newsRepository.deleteAll();
        userRepository.deleteAll();
    }

}
