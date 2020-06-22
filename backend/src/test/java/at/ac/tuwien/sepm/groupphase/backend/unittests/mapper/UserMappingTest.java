package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserMappingTest implements TestData {

    private final AbstractUser abstractUser = Customer.CustomerBuilder.aCustomer()
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

    @Autowired
    private UserMapper userMapper;

    @Test
    public void shouldMapAbstractUserToUserDTO() {
        UserDto userDto = userMapper.abstractUserToUserDto(abstractUser);
        assertAll(
            () -> assertEquals(USER_CODE, userDto.getUserCode()),
            () -> assertEquals(FNAME, userDto.getFirstName()),
            () -> assertEquals(LNAME, userDto.getLastName()),
            () -> assertEquals(DEFAULT_USER, userDto.getEmail()),
            () -> assertEquals(PASS, userDto.getPassword()),
            () -> assertEquals(BIRTHDAY, userDto.getBirthday()),
            () -> assertEquals(CRE, userDto.getCreatedAt()),
            () -> assertEquals(UPD, userDto.getUpdatedAt()),
            () -> assertFalse(userDto.isBlocked()),
            () -> assertTrue(userDto.isLogged()),
            () -> assertEquals(POINTS, userDto.getPoints())
        );
    }

    @Test
    public void shouldMapAbstractUserListToUserDTOList() {
        List<AbstractUser> abstractUsers = new ArrayList<>();
        abstractUsers.add(abstractUser);
        abstractUsers.add(abstractUser);

        List<UserDto> userDtos = userMapper.abstractUserToUserDto(abstractUsers);
        assertEquals(2, userDtos.size());
        UserDto userDto = userDtos.get(0);
        assertAll(
            () -> assertEquals(USER_CODE, userDto.getUserCode()),
            () -> assertEquals(FNAME, userDto.getFirstName()),
            () -> assertEquals(LNAME, userDto.getLastName()),
            () -> assertEquals(DEFAULT_USER, userDto.getEmail()),
            () -> assertEquals(PASS, userDto.getPassword()),
            () -> assertEquals(BIRTHDAY, userDto.getBirthday()),
            () -> assertEquals(CRE, userDto.getCreatedAt()),
            () -> assertEquals(UPD, userDto.getUpdatedAt()),
            () -> assertFalse(userDto.isBlocked()),
            () -> assertTrue(userDto.isLogged()),
            () -> assertEquals(POINTS, userDto.getPoints())
        );
    }

    @Test
    public void shouldMapUserDtoToCustomer() {
        UserDto userDto = userMapper.abstractUserToUserDto(abstractUser);
        Customer customer = userMapper.userDtoToCustomer(userDto);
        assertAll(
            () -> assertEquals(USER_CODE, customer.getUserCode()),
            () -> assertEquals(FNAME, customer.getFirstName()),
            () -> assertEquals(LNAME, customer.getLastName()),
            () -> assertEquals(DEFAULT_USER, customer.getEmail()),
            () -> assertEquals(PASS, customer.getPassword()),
            () -> assertEquals(BIRTHDAY, customer.getBirthday()),
            () -> assertEquals(CRE, customer.getCreatedAt()),
            () -> assertEquals(UPD, customer.getUpdatedAt()),
            () -> assertFalse(customer.isBlocked()),
            () -> assertTrue(customer.isLogged()),
            () -> assertEquals(POINTS, customer.getPoints())
        );
    }

    @Test
    public void shouldMapUserDtoTAdmin() {
        UserDto userDto = userMapper.abstractUserToUserDto(abstractUser);
        Administrator admin = userMapper.userDtoToAdministrator(userDto);
        assertAll(
            () -> assertEquals(USER_CODE, admin.getUserCode()),
            () -> assertEquals(FNAME, admin.getFirstName()),
            () -> assertEquals(LNAME, admin.getLastName()),
            () -> assertEquals(DEFAULT_USER, admin.getEmail()),
            () -> assertEquals(PASS, admin.getPassword()),
            () -> assertEquals(BIRTHDAY, admin.getBirthday()),
            () -> assertEquals(CRE, admin.getCreatedAt()),
            () -> assertEquals(UPD, admin.getUpdatedAt()),
            () -> assertTrue(admin.isLogged())
        );
    }

    @Test
    public void checkAdminBlockedAndPoints() {
        assertAll(
            () -> assertFalse(userMapper.setAdmin(abstractUser)),
            () -> assertFalse(userMapper.setBlocked(abstractUser)),
            () -> assertEquals(10L, userMapper.setPoints(abstractUser))
        );
    }

}
