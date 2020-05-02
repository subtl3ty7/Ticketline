package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserAttempts;
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
    private static final String TEST_USER_FIRST_NAME = "First Name";
    private static final String TEST_USER_LAST_NAME = "Last Name";
    private static final String TEST_USER_EMAIL = "E-Mail";
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
                    .withFirstName(TEST_USER_FIRST_NAME + " " + i)
                    .withLastName(TEST_USER_LAST_NAME + " " + i)
                    .withEmail(TEST_USER_EMAIL + " " + i)
                    .withPassword(passwordEncoder.encode(TEST_USER_PASSWORD + " " + i))
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
                    .withEmail(TEST_USER_EMAIL + " " + i)
                    .build();
                LOGGER.debug("saving customer " + customer.getFirstName() + " with attempts " + attempts.getAttempts() );
                userRepository.save(customer);
                userAttemptsRepository.save(attempts);
            }
        }
    }

}
