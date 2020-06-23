package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.util.Resources;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component("UserDataGenerator")
@Profile("generateData")
public class UserDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_GENERIC_CUSTOMERS = 1000;
    private static final int NUMBER_OF_ADMINISTRATORS = 2;
    private static final String GENERIC_CUSTOMER_EMAIL = "@customer.com";
    private static final String ADMIN_EMAIL = "@admin.com";
    private static final String USER_PASSWORD = "Password0";

    private final UserService userService;
    private final UserAttemptsRepository userAttemptsRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManagerFactory entityManagerFactory;
    private final Resources resources;

    public UserDataGenerator(UserService userService, UserAttemptsRepository userAttemptsRepository, PasswordEncoder passwordEncoder, EntityManagerFactory entityManagerFactory, Resources resources) {
        this.userService = userService;
        this.userAttemptsRepository = userAttemptsRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManagerFactory = entityManagerFactory;
        this.resources = resources;
    }

    @PostConstruct
    private void generate() {
        if (userService.loadAllUsers().size() > 0) {
            LOGGER.info("users already generated");
        } else {
            LOGGER.info("Generating User Test Data...");
            LocalDateTime start = LocalDateTime.now();
            int numberOfEntities = generateUsers();
            LocalDateTime end = LocalDateTime.now();
            float runningTime = Duration.between(start, end).toMillis();
            LOGGER.info("Generating User Test Data (" + numberOfEntities + " Entities) took " + runningTime/1000.0 + " seconds");
        }
    }

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    private int generateUsers() {
        generateGenericCustomers();
        int numberOfEntities = generateCustomers();
        generateAdministrators();
        return NUMBER_OF_ADMINISTRATORS + NUMBER_OF_GENERIC_CUSTOMERS + numberOfEntities;
    }

    private void generateGenericCustomers() {
        List<String> firstnames = Arrays.asList(resources.getObjectFromJson("entities/firstnames.json", String[].class));
        List<String> lastnames = Arrays.asList(resources.getObjectFromJson("entities/lastnames.json", String[].class));

        String encodedPassword = passwordEncoder.encode(USER_PASSWORD);

        for (int i = 0; i < NUMBER_OF_GENERIC_CUSTOMERS; i++) {
            int firstnameIndex = i % firstnames.size();
            int lastnameIndex = i % lastnames.size();

            LocalDateTime birthdate = LocalDateTime.now().minusYears((int)(16+Math.random() * 60)).minusDays((int)(Math.random() * 365)).minusMinutes((int)(Math.random() * 1000));
            LocalDateTime createdAt = LocalDateTime.now().minusDays((int)(Math.random() * 700)).minusMinutes((int)(Math.random() * 1000));

            Customer customer = Customer.CustomerBuilder.aCustomer()
                .withFirstName(firstnames.get(firstnameIndex))
                .withLastName(lastnames.get(lastnameIndex))
                .withEmail( "e" + i + GENERIC_CUSTOMER_EMAIL)
                .withPassword(encodedPassword)
                .withBirthday(birthdate)
                .withIsLogged(false)
                .withCreatedAt(createdAt)
                .withUpdatedAt(createdAt)
                .withPoints(0)
                .withIsBlocked(false)
                .build();
            LOGGER.debug("saving customer " + customer.getEmail());
            userService.registerNewCustomer(customer);
        }
    }

    private int generateCustomers() {
        List<Customer> data = Arrays.asList(resources.getObjectFromJson("entities/customers.json", Customer[].class));
        for (Customer dataEntry: data) {
            LocalDateTime createdAt = LocalDateTime.now();

            Customer customer = Customer.CustomerBuilder.aCustomer()
                .withFirstName(dataEntry.getFirstName())
                .withLastName(dataEntry.getLastName())
                .withEmail( dataEntry.getEmail())
                .withPassword(passwordEncoder.encode(dataEntry.getPassword()))
                .withBirthday(dataEntry.getBirthday())
                .withIsLogged(false)
                .withCreatedAt(createdAt)
                .withUpdatedAt(createdAt)
                .withPoints(0)
                .withIsBlocked(false)
                .build();
            LOGGER.debug("saving customer " + customer.getEmail());
            userService.registerNewCustomer(customer);
        }
        return data.size();
    }

    private void generateAdministrators() {
        List<String> firstnames = Arrays.asList(resources.getObjectFromJson("entities/firstnames.json", String[].class));
        List<String> lastnames = Arrays.asList(resources.getObjectFromJson("entities/lastnames.json", String[].class));

        String encodedPassword = passwordEncoder.encode(USER_PASSWORD);

        for (int i = 0; i < NUMBER_OF_ADMINISTRATORS; i++) {
            int firstnameIndex = i % firstnames.size();
            int lastnameIndex = i % lastnames.size();

            LocalDateTime birthdate = LocalDateTime.now().minusYears((int)(16+Math.random() * 60)).minusDays((int)(Math.random() * 365)).minusMinutes((int)(Math.random() * 1000));
            LocalDateTime createdAt = LocalDateTime.now().minusDays((int)(Math.random() * 700)).minusMinutes((int)(Math.random() * 1000));

            Administrator administrator = Administrator.AdministratorBuilder.aAdministrator()
                .withFirstName(firstnames.get(firstnameIndex))
                .withLastName(lastnames.get(lastnameIndex))
                .withEmail( "e" + i + ADMIN_EMAIL)
                .withPassword(encodedPassword)
                .withBirthday(birthdate)
                .withIsLogged(false)
                .withCreatedAt(createdAt)
                .withUpdatedAt(createdAt)
                .build();
            LOGGER.debug("saving admin " + administrator.getEmail());
            userService.registerNewAdmin(administrator);
        }
    }
}
