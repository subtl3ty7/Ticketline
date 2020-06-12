package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.AccessoryValidator;
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
    private final AccessoryValidator accessoryValidator;
    private final NewsRepository newsRepository;

    @Autowired
    public NewsValidatorImpl(EventRepository eventRepository,
                             UserRepository userRepository,
                             UserValidator userValidator,
                             EventLocationRepository eventLocationRepository,
                             AccessoryValidator accessoryValidator,
                             NewsRepository newsRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.eventLocationRepository = eventLocationRepository;
        this.accessoryValidator = accessoryValidator;
        this.newsRepository = newsRepository;
    }

    @Override
    public Constraints validateNewsCode(String newsCode) {
        Constraints constraints = new Constraints();
        constraints.add("newsCode_unique", newsRepository.findByNewsCode(newsCode) == null);
        return constraints;
    }

    @Override
    public Constraints validate(News news) {
        Constraints constraints = new Constraints();
        constraints.add("news_notNull", news != null);
        if(!constraints.isViolated()) {
            constraints.add(accessoryValidator.validateJavaxConstraints(news));
            constraints.add(validateNewsCode(news.getNewsCode()));
            constraints.add("seenBy_notNull", news.getSeenBy() != null);
        }
        return constraints;
    }

    @Override
    public Constraints validateUser(AbstractUser user) {
        Constraints constraints = new Constraints();
        constraints.add("user_notNull", user != null);
        if(!constraints.isViolated()) {
            constraints.add("user_exists", userRepository.findAbstractUserByUserCode(user.getUserCode()) != null);
            if(!constraints.isViolated()) {
                constraints.add("user_isCustomerOrAdmin", user instanceof Customer || user instanceof Administrator);
                constraints.add("user_isSelf", userValidator.validateUserIdentityWithGivenEmail(user.getEmail()));
            }
        }
        return constraints;
    }
}
