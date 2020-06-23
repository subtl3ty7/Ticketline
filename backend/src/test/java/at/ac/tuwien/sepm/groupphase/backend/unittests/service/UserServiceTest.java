package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest implements TestData {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private AbstractUser customer = Customer.CustomerBuilder.aCustomer()
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

    private AbstractUser admin = Administrator.AdministratorBuilder.aAdministrator()
        .withId(ID)
        .withUserCode(USER_CODE)
        .withFirstName(FNAME)
        .withLastName(LNAME)
        .withEmail(ADMIN_USER)
        .withPassword(PASS)
        .withBirthday(BIRTHDAY)
        .withCreatedAt(CRE)
        .withUpdatedAt(UPD)
        .withIsLogged(false)
        .build();

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
        customer = Customer.CustomerBuilder.aCustomer()
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

        admin = Administrator.AdministratorBuilder.aAdministrator()
            .withId(ID)
            .withUserCode(RandomString.make(6))
            .withFirstName(FNAME)
            .withLastName(LNAME)
            .withEmail(RandomString.make(5) + "@email.com")
            .withPassword(PASS)
            .withBirthday(BIRTHDAY)
            .withCreatedAt(CRE)
            .withUpdatedAt(UPD)
            .withIsLogged(false)
            .build();
    }

    @Test
    public void whenSaveCustomer_thenFindListWithOneElementAndFindUserByCodeAndEmailWithProperties() {
        userService.registerNewCustomer((Customer)customer);

        assertEquals(1, userService.loadAllUsers().size());
        assertNotNull(userService.findUserByUserCode(userService.findUserByEmail(customer.getEmail()).getUserCode()));
        assertNotNull(userService.findUserByEmail(customer.getEmail()));

        AbstractUser abstractUser = userService.findUserByEmail(customer.getEmail());
        assertAll(
            () -> assertEquals(FNAME, abstractUser.getFirstName()),
            () -> assertEquals(LNAME, abstractUser.getLastName()),
            () -> assertEquals(PASS, abstractUser.getPassword()),
            () -> assertEquals(BIRTHDAY, abstractUser.getBirthday()),
            () -> assertFalse(abstractUser.isLogged())
        );
    }

    @Test
    public void whenSaveAdmin_thenFindListWithOneElementAndFindUserByCodeAndEmailWithProperties() {
        userService.registerNewAdmin((Administrator)admin);

        assertEquals(1, userService.loadAllUsers().size());
        assertNotNull(userService.findUserByUserCode(userService.findUserByEmail(admin.getEmail()).getUserCode()));
        assertNotNull(userService.findUserByEmail(admin.getEmail()));

        AbstractUser abstractUser = userService.findUserByEmail(admin.getEmail());
        assertAll(
            () -> assertEquals(FNAME, abstractUser.getFirstName()),
            () -> assertEquals(LNAME, abstractUser.getLastName()),
            () -> assertEquals(PASS, abstractUser.getPassword()),
            () -> assertEquals(BIRTHDAY, abstractUser.getBirthday()),
            () -> assertFalse(abstractUser.isLogged())
        );
    }

    @Test
    public void givenUnloggedCustomer_whenSaveWithSameEmail_thenValidationException() {
        userService.registerNewCustomer((Customer)customer);

        assertThrows(ValidationException.class,
            () ->   userService.registerNewCustomer((Customer)customer));
    }

    @Test
    public void givenAdmin_whenSaveWithSameEmailOrBlock_thenValidationException() {
        userService.registerNewAdmin((Administrator)admin);

        assertThrows(ValidationException.class,
            () ->   userService.registerNewAdmin((Administrator)admin));
        assertThrows(ValidationException.class,
            () ->   userService.blockCustomer(userService.findUserByEmail(admin.getEmail()).getUserCode()));
    }

    @Test
    public void givenCustomer_whenBlock_thenBlocked() {
        userService.registerNewCustomer((Customer)customer);
        assertFalse(((Customer)userService.findUserByEmail(customer.getEmail())).isBlocked());

        userService.blockCustomer(userService.findUserByEmail(customer.getEmail()).getUserCode());
        assertTrue(((Customer)userService.findUserByEmail(customer.getEmail())).isBlocked());
    }

    @Test
    public void givenUser_whenDeleteNonExitingUser_thenValidationException() {
        userService.registerNewCustomer((Customer)customer);

        assertThrows(ValidationException.class,
            () ->   userService.deleteUserByUsercode("wrong"));
    }

    @Test
    public void givenUser_whenDeleteAndFindAll_thenListWithNoUserEntities() {
        userService.registerNewCustomer((Customer)customer);

        userService.deleteUserByUsercode(customer.getUserCode());

        assertEquals(0, userService.loadAllUsers().size());
    }

    @Test
    public void givenUser_whenEditNonExitingUser_thenValidationException() {
        userService.registerNewCustomer((Customer)customer);

        customer.setUserCode("wrong1");
        assertThrows(ValidationException.class,
            () ->   userService.updateCustomer((Customer)customer));
    }

    @Test
    public void givenUser_whenGetByParams_thenListWith1UserEntity() {
        userService.registerNewCustomer((Customer)customer);

        List<AbstractUser> abstractUsers = userService.findUserByParams(customer.getUserCode(), customer.getFirstName(), customer.getLastName(), customer.getEmail());
        assertEquals(1, abstractUsers.size());
    }

}

