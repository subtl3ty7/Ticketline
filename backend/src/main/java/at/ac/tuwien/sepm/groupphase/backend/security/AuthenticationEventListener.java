package at.ac.tuwien.sepm.groupphase.backend.security;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserAttempts;
import at.ac.tuwien.sepm.groupphase.backend.repository.ResetPasswordRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;

@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    UserAttemptsRepository userAttemptsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ResetPasswordRepository resetPasswordRepository;

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        String email = "";
        Authentication auth = event.getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal != null) {
            if(principal instanceof User) {
                User user = (User) principal;
                email = user.getUsername();
            }
            if(principal instanceof String) {
                email = (String) principal;
            }
            // IF USER HAS SUCCESSFULLY LOGGED IN
            if (event instanceof AuthenticationSuccessEvent) {
                AbstractUser user = userRepository.findAbstractUserByEmail(email);
                user.setLogged(true);
                userRepository.save(user);

                if(user instanceof Customer) {
                    UserAttempts userAttempts = userAttemptsRepository.findUserAttemptsByEmail(email);
                    userAttempts.setAttempts(0);
                    userAttemptsRepository.save(userAttempts);
                }
                ResetPassword resetPassword = resetPasswordRepository.findByEmail(email);
                if (resetPassword != null) {
                    resetPasswordRepository.delete(resetPassword);
                }
            }

            // IF USER HAS SUCCESSFULLY LOGGED OUT
            if (event instanceof LogoutSuccessEvent) {
                AbstractUser user = userRepository.findAbstractUserByEmail(email);
                user.setLogged(false);
                userRepository.save(user);
            }

            // IF USER HAS MADE AN UNSUCCESSFUL LOGIN ATTEMPT
            if (event instanceof AuthenticationFailureBadCredentialsEvent) {
                LOGGER.info("Unsuccessful Login Attempt for User " + email);
                AbstractUser user = userRepository.findAbstractUserByEmail(email);
                if (user instanceof Customer) {
                    UserAttempts userAttempts = userAttemptsRepository.findUserAttemptsByEmail(email);
                    int newAttempts = userAttempts.getAttempts() + 1;
                    userAttempts.setAttempts(newAttempts);
                    userAttemptsRepository.save(userAttempts);
                }
            }
        }
    }

}
