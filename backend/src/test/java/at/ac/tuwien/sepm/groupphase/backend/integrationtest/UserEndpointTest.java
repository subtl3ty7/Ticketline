package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.EncoderConfig;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ChangePasswordDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserAttempts;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private UserAttemptsRepository userAttemptsRepository;

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
        .withPoints(POINTS_ZERO)
        .build();

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
        abstractUser = Customer.CustomerBuilder.aCustomer()
            .withId(ID)
            .withUserCode(RandomString.make(6))
            .withFirstName(FNAME)
            .withLastName(LNAME)
            .withEmail(RandomString.make(5) + "@email.com")
            .withPassword(PASS)
            .withBirthday(BIRTHDAY)
            .withCreatedAt(CRE)
            .withUpdatedAt(UPD)
            .withIsBlocked(false)
            .withIsLogged(false)
            .withPoints(POINTS_ZERO)
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
    public void givenOneUser_whenFindAll_thenListWithSizeOneAndUserWithAllProperties() throws Exception {
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
            () -> assertEquals(FNAME, userDto.getFirstName()),
            () -> assertEquals(LNAME, userDto.getLastName()),
            () -> assertEquals(PASS, userDto.getPassword()),
            () -> assertEquals(BIRTHDAY, userDto.getBirthday()),
            () -> assertFalse(userDto.isBlocked()),
            () -> assertFalse(userDto.isLogged()),
            () -> assertEquals(POINTS_ZERO, userDto.getPoints())
        );
    }

    @Test
    public void givenOneUser_whenFindByUserCode_thenUserWithProperties() throws Exception {
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/" + abstractUser.getUserCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        UserDto userDto = objectMapper.readValue(response.getContentAsString(), UserDto.class);
        assertAll(
            () -> assertEquals(FNAME, userDto.getFirstName()),
            () -> assertEquals(LNAME, userDto.getLastName()),
            () -> assertEquals(PASS, userDto.getPassword()),
            () -> assertEquals(BIRTHDAY, userDto.getBirthday()),
            () -> assertFalse(userDto.isBlocked()),
            () -> assertFalse(userDto.isLogged()),
            () -> assertEquals(POINTS_ZERO, userDto.getPoints())
        );
    }

    @Test
    public void givenNothing_whenPostCustomer_thenUserWithSetProperties() throws Exception {
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
            () -> assertEquals(FNAME, userDto1.getFirstName()),
            () -> assertEquals(LNAME, userDto1.getLastName()),
            () -> assertEquals(PASS, userDto1.getPassword()),
            () -> assertEquals(BIRTHDAY, userDto1.getBirthday()),
            () -> assertFalse(userDto1.isBlocked()),
            () -> assertFalse(userDto1.isLogged()),
            () -> assertEquals(POINTS_ZERO, userDto1.getPoints())
        );
    }

    @Test
    public void givenNothing_whenPostAdmin_thenUserWithSetProperties() throws Exception {
        Administrator administrator = userMapper.userDtoToAdministrator(userMapper.abstractUserToUserDto(abstractUser));
        administrator.setEmail(ADMIN_USER);
        UserDto userDto = userMapper.abstractUserToUserDto(administrator);
        String body = objectMapper.writeValueAsString(userDto);

        MvcResult mvcResult = this.mockMvc.perform(post(ADMIN_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        UserDto userDto1 = objectMapper.readValue(response.getContentAsString(), UserDto.class);
        assertAll(
            () -> assertEquals(FNAME, userDto1.getFirstName()),
            () -> assertEquals(LNAME, userDto1.getLastName()),
            () -> assertEquals(ADMIN_USER, userDto1.getEmail()),
            () -> assertEquals(PASS, userDto1.getPassword()),
            () -> assertEquals(BIRTHDAY, userDto1.getBirthday()),
            () -> assertFalse(userDto1.isLogged())
        );
    }

    @Test
    public void givenNothing_whenPostInvalid_then400() throws Exception {
        abstractUser.setEmail(null);
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
    public void givenNothing_whenPostAdminAsCustomer_then500() throws Exception {
        Administrator administrator = userMapper.userDtoToAdministrator(userMapper.abstractUserToUserDto(abstractUser));
        administrator.setEmail(ADMIN_USER);
        UserDto userDto = userMapper.abstractUserToUserDto(administrator);
        String body = objectMapper.writeValueAsString(userDto);

        MvcResult mvcResult = this.mockMvc.perform(post(ADMIN_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void givenUnblockedUser_whenBlockAsAdmin_then200() throws Exception {
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/block/" + abstractUser.getUserCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(((Customer)userRepository.findAbstractUserByEmail(abstractUser.getEmail())).isBlocked());
    }

    @Test
    public void givenBlockedUser_whenUnblockAsAdmin_then200() throws Exception {
        ((Customer)abstractUser).setBlocked(true);
        userRepository.save(abstractUser);
        userAttemptsRepository.save(UserAttempts.UserAttemptsBuilder.aAttempts().withId(ID).withEmail(abstractUser.getEmail()).withAttempts(5).build());

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/unblock/" + abstractUser.getUserCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertFalse(((Customer)userRepository.findAbstractUserByEmail(abstractUser.getEmail())).isBlocked());
    }

    @Test
    public void givenUnblockedUser_whenBlockAsCustomer_then500() throws Exception {
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/block/" + abstractUser.getUserCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertFalse(((Customer)userRepository.findAbstractUserByEmail(abstractUser.getEmail())).isBlocked());
    }

    @Test
    public void givenBlockedUser_whenUnblockAsCustomer_then500() throws Exception {
        ((Customer)abstractUser).setBlocked(true);
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/unblock/" + abstractUser.getUserCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertTrue(((Customer)userRepository.findAbstractUserByEmail(abstractUser.getEmail())).isBlocked());
    }

    @Test
    public void givenLoggedUser_whenDelete_then204AndNoUser() throws Exception {
        abstractUser.setLogged(true);
        userRepository.save(abstractUser);
        userAttemptsRepository.save(UserAttempts.UserAttemptsBuilder.aAttempts().withId(ID).withEmail(abstractUser.getEmail()).withAttempts(5).build());

        MvcResult mvcResult = this.mockMvc.perform(delete(USER_BASE_URI + "/" + abstractUser.getUserCode())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertNull(userRepository.findAbstractUserByEmail(abstractUser.getEmail()));
    }

    @Test
    public void givenLoggedUser_whenLogout_then200() throws Exception {
        abstractUser.setLogged(true);
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI + "/logout")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(abstractUser.getEmail(), USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertFalse(userRepository.findAbstractUserByEmail(abstractUser.getEmail()).isLogged());
    }

    @Test
    public void givenUser_whenGetByParams_then200AndListWithOneElement() throws Exception {
        userRepository.save(abstractUser);

        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI)
            .param("userCode", abstractUser.getUserCode())
            .param("firstName", abstractUser.getFirstName())
            .param("lastName", abstractUser.getLastName())
            .param("email", abstractUser.getEmail())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<UserDto> userDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            UserDto[].class));

        assertEquals(1, userDtos.size());
    }

}
