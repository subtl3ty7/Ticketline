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
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Autowired
    UserAttemptsRepository userAttemptsRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event){

        String email = (String) event.getAuthentication().getPrincipal();
        if(event instanceof AuthenticationSuccessEvent) {
            AbstractUser user = userRepository.findAbstractUserByEmail(email);
            user.setLogged(true);
            userRepository.save(user);
        }

        if(event instanceof LogoutSuccessEvent) {
            AbstractUser user = userRepository.findAbstractUserByEmail(email);
            user.setLogged(false);
            userRepository.save(user);
        }

        if(event instanceof AuthenticationFailureBadCredentialsEvent) {
            UserAttempts userAttempts = userAttemptsRepository.findUserAttemptsByEmail(email);
            int newAttempts = userAttempts.getAttempts() + 1;
            userAttempts.setAttempts(newAttempts);
            System.out.println("Attempt " + userAttempts.getAttempts());
            userAttemptsRepository.save(userAttempts);
        }
    }
}
