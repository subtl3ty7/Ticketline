package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.EventValidator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.NewsValidator;
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
    private final EventLocationRepository eventLocationRepository;

    @Autowired
    public NewsValidatorImpl(EventRepository eventRepository, EventLocationRepository eventLocationRepository) {
        this.eventRepository = eventRepository;
        this.eventLocationRepository = eventLocationRepository;
    }

    public Constraints validateNewsCode(String newsCode) {
        Constraints constraints = new Constraints();
        constraints.add("newsCode_unique", eventRepository.findEventByEventCode(newsCode) == null);
        return constraints;
    }
}
