package at.ac.tuwien.sepm.groupphase.backend.security;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.UserAttempts;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Autowired
    UserAttemptsRepository userAttemptsRepository;
    @Autowired
    UserRepository userRepository;

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
            if (event instanceof AuthenticationSuccessEvent) {
                AbstractUser user = userRepository.findAbstractUserByEmail(email);
                UserAttempts userAttempts = userAttemptsRepository.findUserAttemptsByEmail(email);
                user.setLogged(true);
                userAttempts.setAttempts(0);
                userRepository.save(user);
                userAttemptsRepository.save(userAttempts);
            }

            if (event instanceof LogoutSuccessEvent) {
                AbstractUser user = userRepository.findAbstractUserByEmail(email);
                user.setLogged(false);
                userRepository.save(user);
            }

            if (event instanceof AuthenticationFailureBadCredentialsEvent) {

                UserAttempts userAttempts = userAttemptsRepository.findUserAttemptsByEmail(email);
                int newAttempts = userAttempts.getAttempts() + 1;
                userAttempts.setAttempts(newAttempts);
                userAttemptsRepository.save(userAttempts);
            }
        }
    }
}
