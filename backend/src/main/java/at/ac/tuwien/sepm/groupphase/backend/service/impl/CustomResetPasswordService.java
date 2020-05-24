package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ResetPasswordRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EmailService;
import at.ac.tuwien.sepm.groupphase.backend.service.ResetPasswordService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomResetPasswordService implements ResetPasswordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ResetPasswordRepository resetPasswordRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserValidator validator;

    @Autowired
    public CustomResetPasswordService(ResetPasswordRepository resetPasswordRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder,
                                      EmailService emailService,
                                      UserValidator validator) {

        this.resetPasswordRepository = resetPasswordRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.validator = validator;
    }

    @Override
    public void resetPasswordRequest(String email) {
        LOGGER.info("Validating reset password request");
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
            throw new NotFoundException("Can not find reset password code!");
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

    @Override
    public String generateRandomPasswordFor(String email) {
        String password = generateRandomPassword();
        AbstractUser user = this.userRepository.findAbstractUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return password;
    }

    private String generateRandomPassword(){
        String allCharactersAndDigits = "0123456789ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnpqrstuvwxyz";
        StringBuilder password = new StringBuilder(12);
        SecureRandom random = new SecureRandom();
        random.setSeed(System.nanoTime());
        for (int i = 0; i < 12; i++) {
            int position = random.nextInt(allCharactersAndDigits.length());
            password.append(allCharactersAndDigits.charAt(position));
        }
        return password.toString();
    }
}
