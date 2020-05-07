package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        .withPoints(POINTS)
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

        admin = Administrator.AdministratorBuilder.aAdministrator()
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
    }

    @Test
    public void whenSaveCustomer_thenFindListWithOneElementAndFindUserByCodeAndEmailWithProperties() {
        userService.registerNewCustomer((Customer)customer);

        assertEquals(1, userService.loadAllUsers().size());
        assertNotNull(userService.findUserByUserCode(userService.findUserByEmail(DEFAULT_USER).getUserCode()));
        assertNotNull(userService.findUserByEmail(DEFAULT_USER));

        AbstractUser abstractUser = userService.findUserByEmail(DEFAULT_USER);
        assertAll(
            () -> assertEquals(FNAME, abstractUser.getFirstName()),
            () -> assertEquals(LNAME, abstractUser.getLastName()),
            () -> assertEquals(DEFAULT_USER, abstractUser.getEmail()),
            () -> assertEquals(PASS, abstractUser.getPassword()),
            () -> assertEquals(BIRTHDAY, abstractUser.getBirthday()),
            () -> assertFalse(abstractUser.isLogged())
        );
    }

    @Test
    public void whenSaveAdmin_thenFindListWithOneElementAndFindUserByCodeAndEmailWithProperties() {
        userService.registerNewAdmin((Administrator)admin);

        assertEquals(1, userService.loadAllUsers().size());
        assertNotNull(userService.findUserByUserCode(userService.findUserByEmail(ADMIN_USER).getUserCode()));
        assertNotNull(userService.findUserByEmail(ADMIN_USER));

        AbstractUser abstractUser = userService.findUserByEmail(ADMIN_USER);
        assertAll(
            () -> assertEquals(FNAME, abstractUser.getFirstName()),
            () -> assertEquals(LNAME, abstractUser.getLastName()),
            () -> assertEquals(ADMIN_USER, abstractUser.getEmail()),
            () -> assertEquals(PASS, abstractUser.getPassword()),
            () -> assertEquals(BIRTHDAY, abstractUser.getBirthday()),
            () -> assertFalse(abstractUser.isLogged())
        );
    }

    @Test
    public void whenSave2Users_thenFindListWith2Elements_AfterDelete1FindListWith1Elements() {
        customer.setLogged(true);
        userRepository.save(customer);
        userService.registerNewAdmin((Administrator)admin);
        assertEquals(2, userService.loadAllUsers().size());

        userService.deleteUserByUsercode(userService.findUserByEmail(DEFAULT_USER).getUserCode());
        assertEquals(1, userService.loadAllUsers().size());
    }

    @Test
    public void givenUnloggedCustomer_saveWithSameEmailOrDeleteOrEdit_thenError() {
        userService.registerNewCustomer((Customer)customer);

        assertThrows(ServiceException.class,
            () ->   userService.registerNewCustomer((Customer)customer));
        assertThrows(ServiceException.class,
            () ->   userService.deleteUserByUsercode(userService.findUserByEmail(DEFAULT_USER).getUserCode()));
        assertThrows(ServiceException.class,
            () ->   userService.updateCustomer(customer, userService.findUserByEmail(DEFAULT_USER).getUserCode()));
    }

    @Test
    public void givenAdmin_saveWithSameEmailOrDeleteOrEditorBlock_thenError() {
        userService.registerNewAdmin((Administrator)admin);

        assertThrows(ServiceException.class,
            () ->   userService.registerNewAdmin((Administrator)admin));
        assertThrows(ServiceException.class,
            () ->   userService.deleteUserByUsercode(userService.findUserByEmail(ADMIN_USER).getUserCode()));
        assertThrows(ServiceException.class,
            () ->   userService.updateCustomer(admin, userService.findUserByEmail(ADMIN_USER).getUserCode()));
        assertThrows(ServiceException.class,
            () ->   userService.blockCustomer(userService.findUserByEmail(ADMIN_USER).getUserCode()));
    }

    @Test
    public void givenCustomer_Edit_thenFindUserEmailWithNewProperties() {
        customer.setLogged(true);
        userRepository.save(customer);

        AbstractUser abstractUser = userService.findUserByEmail(DEFAULT_USER);
        assertAll(
            () -> assertEquals(FNAME, abstractUser.getFirstName()),
            () -> assertEquals(LNAME, abstractUser.getLastName()),
            () -> assertEquals(DEFAULT_USER, abstractUser.getEmail()),
            () -> assertEquals(PASS, abstractUser.getPassword()),
            () -> assertEquals(BIRTHDAY, abstractUser.getBirthday())
        );
        customer.setFirstName("NewName");
        userService.updateCustomer(customer, userService.findUserByEmail(DEFAULT_USER).getUserCode());

        AbstractUser abstractUser1 = userService.findUserByEmail(DEFAULT_USER);
        assertAll(
            () -> assertEquals("NewName", abstractUser1.getFirstName()),
            () -> assertEquals(LNAME, abstractUser1.getLastName()),
            () -> assertEquals(DEFAULT_USER, abstractUser1.getEmail()),
            () -> assertEquals(PASS, abstractUser1.getPassword()),
            () -> assertEquals(BIRTHDAY, abstractUser1.getBirthday())
        );
    }

    @Test
    public void givenCustomer_blockAndUnblock() {
        userService.registerNewCustomer((Customer)customer);

        userService.blockCustomer(userService.findUserByEmail(DEFAULT_USER).getUserCode());
        assertThrows(ServiceException.class,
            () ->   userService.blockCustomer(userService.findUserByEmail(DEFAULT_USER).getUserCode()));

        assertEquals("", userService.unblockUser(userService.findUserByEmail(DEFAULT_USER).getUserCode()));
    }

}

