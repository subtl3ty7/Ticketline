package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.MessageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

@Component("UserDataGenerator")
@Profile("generateData")
public class UserDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_CUSTOMERS_TO_GENERATE = 20;
    private static final int NUMBER_OF_ADMINISTRATORS_TO_GENERATE = 2;
    private static final String TEST_USER_FIRST_NAME = "Name";
    private static final String TEST_USER_LAST_NAME = "Surname";
    private static final String TEST_CUSTOMER_EMAIL = "@customer.com";
    private static final String TEST_ADMIN_EMAIL = "@admin.com";
    private static final String TEST_USER_PASSWORD = "Password";
    private static final String TEST_USER_CODE = "U123X";

    private final UserService userService;
    private final UserAttemptsRepository userAttemptsRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManagerFactory entityManagerFactory;

    public UserDataGenerator(UserService userService, UserAttemptsRepository userAttemptsRepository, PasswordEncoder passwordEncoder, EntityManagerFactory entityManagerFactory) {
        this.userService = userService;
        this.userAttemptsRepository = userAttemptsRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManagerFactory = entityManagerFactory;
    }

    @PostConstruct
    private void generateUsers() {
        if (userService.loadAllUsers().size() > 0) {
            LOGGER.debug("users already generated");
        } else {
            generateCustomer();
            generateAdministrator();
        }
    }

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    private void generateCustomer() {
        LOGGER.debug("generating {} customers", NUMBER_OF_CUSTOMERS_TO_GENERATE);
        for (int i = 0; i < NUMBER_OF_CUSTOMERS_TO_GENERATE; i++) {
            Customer customer = Customer.CustomerBuilder.aCustomer()
                .withFirstName(TEST_USER_FIRST_NAME  + i)
                .withLastName(TEST_USER_LAST_NAME  + i)
                .withEmail( "e" + i + TEST_CUSTOMER_EMAIL)
                .withPassword(passwordEncoder.encode(TEST_USER_PASSWORD + i))
                .withUserCode(TEST_USER_CODE + i)
                .withBirthday(LocalDateTime.now().minusYears(20))
                .withIsLogged(false)
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
                .withPoints(0)
                .withIsBlocked(false)
                .build();
            UserAttempts attempts = UserAttempts.UserAttemptsBuilder.aAttempts()
                .withAttempts(0)
                .withEmail("e" + i + TEST_CUSTOMER_EMAIL)
                .build();
            LOGGER.debug("saving customer " + customer.getEmail() + " with attempts " + attempts.getAttempts() );
            userService.registerNewCustomer(customer);
        }
        Customer customer = Customer.CustomerBuilder.aCustomer()
            .withFirstName("ege")
            .withLastName("demirsoy")
            .withEmail("egedemirsoy@gmail.com")
            .withPassword(passwordEncoder.encode(TEST_USER_PASSWORD))
            .withUserCode("U11111")
            .withBirthday(LocalDateTime.now().minusYears(20))
            .withIsLogged(false)
            .withCreatedAt(LocalDateTime.now())
            .withUpdatedAt(LocalDateTime.now())
            .withPoints(0)
            .withIsBlocked(false)
            .build();
        UserAttempts attempts = UserAttempts.UserAttemptsBuilder.aAttempts()
            .withAttempts(0)
            .withEmail("egedemirsoy@gmail.com")
            .build();
        LOGGER.debug("saving customer " + customer.getEmail() + " with attempts " + attempts.getAttempts() );
        userService.registerNewCustomer(customer);
    }

    private void generateAdministrator() {
        LOGGER.debug("generating {} administrators", NUMBER_OF_ADMINISTRATORS_TO_GENERATE);
        for (int i = 0; i < NUMBER_OF_ADMINISTRATORS_TO_GENERATE; i++) {
            Administrator administrator = Administrator.AdministratorBuilder.aAdministrator()
                .withFirstName(TEST_USER_FIRST_NAME  + i)
                .withLastName(TEST_USER_LAST_NAME  + i)
                .withEmail( "e" + i + TEST_ADMIN_EMAIL)
                .withPassword(passwordEncoder.encode(TEST_USER_PASSWORD + i))
                .withUserCode(TEST_USER_CODE + (9-i))
                .withBirthday(LocalDateTime.now().minusYears(20+i))
                .withIsLogged(false)
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
                .build();
            LOGGER.debug("saving customer " + administrator.getEmail() );
            userService.registerNewAdmin(administrator);
        }
    }
}
