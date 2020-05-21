package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.EventValidator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.NewsValidator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class NewsValidatorImpl implements NewsValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final EventLocationRepository eventLocationRepository;

    @Autowired
    public NewsValidatorImpl(EventRepository eventRepository, UserRepository userRepository, UserValidator userValidator, EventLocationRepository eventLocationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.eventLocationRepository = eventLocationRepository;
    }

    @Override
    public Constraints validateNewsCode(String newsCode) {
        Constraints constraints = new Constraints();
        constraints.add("newsCode_unique", eventRepository.findEventByEventCode(newsCode) == null);
        return constraints;
    }

    @Override
    public Constraints validate(News news) {
        Constraints constraints = new Constraints();
        constraints.add("news_notNull", news != null);
        if(!constraints.isViolated()) {
            constraints.add(AccesoryValidator.validateJavaxConstraints(news));
            constraints.add(validateNewsCode(news.getNewsCode()));
        }
        return constraints;
    }

    @Override
    public Constraints validateUserCode(String userCode) {
        Constraints constraints = new Constraints();
        constraints.add("userCode_notNull", userCode != null);
        if(!constraints.isViolated()) {
            AbstractUser user = userRepository.findAbstractUserByUserCode(userCode);
            constraints.add("user_exists", user != null);
            if(!constraints.isViolated()) {
                constraints.add("user_isLogged", user.isLogged());
                constraints.add("user_isCustomer", user instanceof Customer);
                constraints.add("user_isSelf", userValidator.validateUserIdentityWithGivenEmail(user.getEmail()));
            }
        }
        return constraints;
    }
}
