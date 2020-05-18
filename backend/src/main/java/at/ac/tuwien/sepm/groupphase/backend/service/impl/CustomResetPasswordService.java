package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import at.ac.tuwien.sepm.groupphase.backend.repository.ResetPasswordRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EmailService;
import at.ac.tuwien.sepm.groupphase.backend.service.ResetPasswordService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomResetPasswordService implements ResetPasswordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ResetPasswordRepository resetPasswordRepository;
    private final EmailService emailService;
    private final UserValidator validator;

    @Autowired
    public CustomResetPasswordService(ResetPasswordRepository resetPasswordRepository,
                             EmailService emailService,
                             UserValidator validator) {

        this.resetPasswordRepository = resetPasswordRepository;
        this.emailService = emailService;
        this.validator = validator;
    }

    @Override
    public void resetPasswordRequest(String email) {
        validator.validateEmail(email).throwIfViolated();
        String resetPasswordCode = CodeGenerator.generateResetPasswordCode();
        ResetPassword resetPassword = new ResetPassword(email, resetPasswordCode, LocalDateTime.now());
        ResetPassword resetPasswordOld = resetPasswordRepository.findByEmail(email);
        if(resetPasswordOld != null) {
            resetPasswordRepository.delete(resetPasswordOld);
        }
        resetPasswordRepository.save(resetPassword);
        emailService.sendResetPasswordEmail(resetPassword);
    }

    @Override
    public String getResetPasswordEmailWithCode(String resetPasswordCode) {
        ResetPassword resetPassword = resetPasswordRepository.findByResetPasswordCode(resetPasswordCode);
        if(resetPassword == null) {
            throw new NullPointerException("Can not find reset password code!");
        }
        return resetPassword.getEmail();
    }

    @Override
    public void deleteExpiredResetPasswordCodes(){
        List<ResetPassword> expired = resetPasswordRepository.findAllByCreatedAtBefore(LocalDateTime.now().minusMinutes(30));
        if (expired != null && !expired.isEmpty()) {
            for (ResetPassword resetPassword: expired) {
                resetPasswordRepository.delete(resetPassword);
            }
        }
    }
}
