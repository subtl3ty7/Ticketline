package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.MessageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

@Component
@Profile("generateData")
public class UserDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_CUSTOMERS_TO_GENERATE = 3;
    private static final int NUMBER_OF_ADMINISTRATORS_TO_GENERATE = 2;
    private static final String TEST_USER_FIRST_NAME = "Name";
    private static final String TEST_USER_LAST_NAME = "Surname";
    private static final String TEST_CUSTOMER_EMAIL = "@customer.com";
    private static final String TEST_ADMIN_EMAIL = "@admin.com";
    private static final String TEST_USER_PASSWORD = "Password";
    private static final String TEST_USER_CODE = "U123X";

    private final UserRepository userRepository;
    private final UserAttemptsRepository userAttemptsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataGenerator(UserRepository userRepository, UserAttemptsRepository userAttemptsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userAttemptsRepository = userAttemptsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void generateCustomer() {
        if (userRepository.findAll().size() > 0) {
            LOGGER.debug("customers already generated");
        } else {
            LOGGER.debug("generating {} customers", NUMBER_OF_CUSTOMERS_TO_GENERATE);
            for (int i = 0; i < NUMBER_OF_CUSTOMERS_TO_GENERATE; i++) {
                AbstractUser customer = Customer.CustomerBuilder.aCustomer()
                    .withFirstName(TEST_USER_FIRST_NAME  + i)
                    .withLastName(TEST_USER_LAST_NAME  + i)
                    .withEmail( "e" + i + TEST_CUSTOMER_EMAIL)
                    .withPassword(passwordEncoder.encode(TEST_USER_PASSWORD + i))
                    .withUserCode(TEST_USER_CODE + i)
                    .withBirthday(LocalDateTime.now().minusMonths(i))
                    .withIsLogged(false)
                    .withCreatedAt(LocalDateTime.now())
                    .withUpdatedAt(LocalDateTime.now())
                    .withPoints(i)
                    .withIsBlocked(false)
                    .build();
                UserAttempts attempts = UserAttempts.UserAttemptsBuilder.aAttempts()
                    .withAttempts(0)
                    .withEmail("e" + i + TEST_CUSTOMER_EMAIL)
                    .build();
                LOGGER.debug("saving customer " + customer.getEmail() + " with attempts " + attempts.getAttempts() );
                userRepository.save(customer);
                userAttemptsRepository.save(attempts);
            }
        }
    }

    @PostConstruct
    private void generateAdministrator() {
        if (userRepository.findAll().size() > 3) {
            LOGGER.debug("administrators already generated");
        } else {
            LOGGER.debug("generating {} administrators", NUMBER_OF_ADMINISTRATORS_TO_GENERATE);
            for (int i = 0; i < NUMBER_OF_ADMINISTRATORS_TO_GENERATE; i++) {
                AbstractUser administrator = Administrator.AdministratorBuilder.aAdministrator()
                    .withFirstName(TEST_USER_FIRST_NAME  + i)
                    .withLastName(TEST_USER_LAST_NAME  + i)
                    .withEmail( "e" + i + TEST_ADMIN_EMAIL)
                    .withPassword(passwordEncoder.encode(TEST_USER_PASSWORD + i))
                    .withUserCode(TEST_USER_CODE + (9-i))
                    .withBirthday(LocalDateTime.now().minusMonths(i))
                    .withIsLogged(false)
                    .withCreatedAt(LocalDateTime.now())
                    .withUpdatedAt(LocalDateTime.now())
                    .build();
                LOGGER.debug("saving customer " + administrator.getEmail() );
                userRepository.save(administrator);
            }
        }
    }
}
