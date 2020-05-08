package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.EncoderConfig;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

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
        .withIsLogged(false)
        .withPoints(POINTS)
        .build();

    @BeforeEach
    public void beforeEach() {
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
            .withIsLogged(false)
            .withPoints(POINTS)
            .build();
    }

    @Test
    public void givenUserLoggedIn_whenFindAll_then200() throws Exception {
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/all")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );
    }

    @Test
    public void givenNoOneLoggedIn_whenFindAll_then401() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/all"))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    public void givenNothing_whenFindAll_thenEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/all")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<UserDto> userDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            UserDto[].class));

        assertEquals(0, userDtos.size());
    }

    @Test
    public void givenOneUser_whenFindAll_thenListWithSizeOneAndUserWithAllProperties()
        throws Exception {
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/all")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<UserDto> userDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            UserDto[].class));

        assertEquals(1, userDtos.size());
        UserDto userDto = userDtos.get(0);
        assertAll(
            () -> assertEquals(USER_CODE, userDto.getUserCode()),
            () -> assertEquals(FNAME, userDto.getFirstName()),
            () -> assertEquals(LNAME, userDto.getLastName()),
            () -> assertEquals(DEFAULT_USER, userDto.getEmail()),
            () -> assertEquals(PASS, userDto.getPassword()),
            () -> assertEquals(BIRTHDAY, userDto.getBirthday()),
            () -> assertFalse(userDto.isBlocked()),
            () -> assertFalse(userDto.isLogged()),
            () -> assertEquals(POINTS, userDto.getPoints())
            );
    }

    @Test
    public void givenNothing_whenPost_thenUserWithAllSetProperties() throws Exception {
        UserDto userDto = userMapper.abstractUserToUserDto(abstractUser);
        String body = objectMapper.writeValueAsString(userDto);

        MvcResult mvcResult = this.mockMvc.perform(post(CUSTOMER_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        UserDto userDto1 = objectMapper.readValue(response.getContentAsString(), UserDto.class);
        assertAll(
            () -> assertEquals(USER_CODE, userDto.getUserCode()),
            () -> assertEquals(FNAME, userDto1.getFirstName()),
            () -> assertEquals(LNAME, userDto1.getLastName()),
            () -> assertEquals(DEFAULT_USER, userDto1.getEmail()),
            () -> assertEquals(PASS, userDto1.getPassword()),
            () -> assertEquals(BIRTHDAY, userDto1.getBirthday()),
            () -> assertFalse(userDto1.isBlocked()),
            () -> assertFalse(userDto1.isLogged()),
            () -> assertEquals(POINTS, userDto1.getPoints())
        );
    }

    @Test
    public void givenNothing_whenPostInvalid_then400() throws Exception {
        abstractUser.setEmail(null);
        abstractUser.setFirstName(null);
        UserDto userDto = userMapper.abstractUserToUserDto(abstractUser);
        String body = objectMapper.writeValueAsString(userDto);

        MvcResult mvcResult = this.mockMvc.perform(post(CUSTOMER_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(2, errors.length);
            }
        );
    }

    @Test
    public void givenUser_whenPostSame_then400() throws Exception {
        userRepository.save(abstractUser);
        UserDto userDto = userMapper.abstractUserToUserDto(abstractUser);
        String body = objectMapper.writeValueAsString(userDto);

        MvcResult mvcResult = this.mockMvc.perform(post(CUSTOMER_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void givenBlockedUser_whenUnblockAsAdmin_then200() throws Exception {
        ((Customer)abstractUser).setBlocked(true);
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/unblock/" + USER_CODE)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertFalse(((Customer)userRepository.findAbstractUserByEmail(DEFAULT_USER)).isBlocked());
    }

    @Test
    public void givenBlockedUser_whenUnblockAsCustomer_then403() throws Exception {
        ((Customer)abstractUser).setBlocked(true);
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/unblock/" + USER_CODE)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        assertTrue(((Customer)userRepository.findAbstractUserByEmail(DEFAULT_USER)).isBlocked());
    }



}