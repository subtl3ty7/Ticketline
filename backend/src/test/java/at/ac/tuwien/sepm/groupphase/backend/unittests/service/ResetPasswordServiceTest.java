package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ResetPasswordRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ResetPasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ResetPasswordServiceTest implements TestData {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    private ResetPassword resetPassword = new ResetPassword(DEFAULT_USER, USER_CODE, TEST_NEWS_PUBLISHED_AT);

    @BeforeEach
    public void beforeEach() {
        resetPasswordRepository.deleteAll();
        resetPassword = new ResetPassword(DEFAULT_USER, USER_CODE, TEST_NEWS_PUBLISHED_AT);
    }

    @Test
    public void whenResetPasswordByNonExistingEmail_thenValidationException() {

        assertThrows(ValidationException.class,
            () ->   resetPasswordService.resetPasswordRequest("wrongEmail"));
    }

    @Test
    public void givenResetPasswordEntity_whenGetEmailByCode_thenEmail() {
        resetPasswordRepository.save(resetPassword);

        assertEquals(DEFAULT_USER, resetPasswordService.getResetPasswordEmailWithCode(USER_CODE));
    }

    @Test
    public void givenResetPasswordEntity_whenDeleteExpired_thenNoEntities() {
        resetPasswordRepository.save(resetPassword);

        resetPasswordService.deleteExpiredResetPasswordCodes();
        assertEquals(0, resetPasswordRepository.findAll().size());
    }
}
