package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest implements TestData {

    @Autowired
    UserRepository userRepository;

    @Test
    public void givenNothing_whenSaveUser_thenFindListWithOneElementAndFindListUserById() {
        AbstractUser abstractUser = Customer.CustomerBuilder.aCustomer()
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

        userRepository.save(abstractUser);

        assertAll(
            () -> assertEquals(1, userRepository.findAll().size()),
            () -> assertNotNull(userRepository.findById(abstractUser.getId()))
        );
    }


}
