package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import at.ac.tuwien.sepm.groupphase.backend.repository.ResetPasswordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class ResetPasswordRepositoryTest implements TestData {

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Test
    public void givenNothing_whenSaveResetPassword_thenFindListWithOneElementAndFindRPByCode() {
        ResetPassword resetPassword = new ResetPassword(DEFAULT_USER, USER_CODE, START);

        resetPasswordRepository.save(resetPassword);

        assertAll(
            () -> assertEquals(1, resetPasswordRepository.findAll().size()),
            () -> assertNotNull(resetPasswordRepository.findByResetPasswordCode(USER_CODE))
        );
    }
}
