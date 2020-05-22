package at.ac.tuwien.sepm.groupphase.backend.util.validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class EventValidatorImpl implements EventValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;

    @Autowired
    public EventValidatorImpl(EventRepository eventRepository, EventLocationRepository eventLocationRepository) {
        this.eventRepository = eventRepository;
        this.eventLocationRepository = eventLocationRepository;
    }


    @Override
    public Constraints validate(Event event) {
        Constraints constraints = new Constraints();
        constraints.add("event_notNull", event != null);
        if(!constraints.isViolated()) {
            constraints.add(AccesoryValidator.validateJavaxConstraints(event));
            constraints.add(validateEventCode(event.getEventCode()));
            constraints.add("prices_exist", event.getPrices() != null && event.getPrices().size() > 0);
            constraints.add("shows_exist", event.getShows() != null && event.getShows().size() > 0);
        }
        if(!constraints.isViolated()) {
            constraints.add(validateShows(event.getShows()));
        }
        return constraints;
    }

    @Override
    public Constraints validateExists(String eventCode) {
        Constraints constraints = new Constraints();
        constraints.add("event_exists", eventRepository.findEventByEventCode(eventCode) != null);
        return constraints;
    }

    public Constraints validateEventCode(String eventCode) {
        Constraints constraints = new Constraints();
        constraints.add("eventCode_unique", eventRepository.findEventByEventCode(eventCode) == null);
        return constraints;
    }

    private Constraints validateShows(List<Show> shows) {
        Constraints constraints = new Constraints();
        for (Show show : shows) {
            constraints.add(validate(show));
        }
        return constraints;
    }

    private Constraints validate(Show show) {
        Constraints constraints = new Constraints();
        constraints.add(AccesoryValidator.validateJavaxConstraints(show));
        constraints.add("show_idNull", show.getId()==null);
        constraints.add( "eventLocation_given", show.getEventLocation() != null);
        if(!constraints.isViolated()) {
            constraints.add("eventLocation_given", show.getEventLocation().size() > 0);
            constraints.add("eventLocation_onlyOne", show.getEventLocation().size() <= 1);
            constraints.add("eventLocation_exists", show.getEventLocation().size() == 1 && show.getEventLocation().get(0) != null && show.getEventLocation().get(0).getId() != null);
        }
        if(!constraints.isViolated()) {
            EventLocation eventLocation = eventLocationRepository.findEventLocationById(show.getEventLocation().get(0).getId());
            constraints.add("eventLocation_exists", eventLocation != null);
            if(!constraints.isViolated()) {
                constraints.add("eventLocation_unassigned", eventLocation.getShowId() == null);
            }
        }
        return constraints;
    }

    @Override
    public Constraints validate(EventLocation eventLocation) {
        Constraints constraints = new Constraints();
        constraints.add(AccesoryValidator.validateJavaxConstraints(eventLocation));
        if(eventLocation.getSections() != null) {
            constraints.add("eventLocation_capacity", eventLocation.getCapacity() >= getCapacitySum(eventLocation));
            constraints.add(validateSections(eventLocation.getSections()));
        }
        return constraints;
    }

    private Constraints validateSections(List<Section> sections) {
        Constraints constraints = new Constraints();
        for(Section section: sections) {
            constraints.add(validate(section));
        }
        return constraints;
    }

    private Constraints validate(Section section) {
        Constraints constraints = new Constraints();
        constraints.add(AccesoryValidator.validateJavaxConstraints(section));
        constraints.add("section_capacity", section.getCapacity() >= section.getSeats().size());
        if(section.getSeats() != null) {
            constraints.add(validateSeats(section.getSeats()));
        }
        return constraints;
    }

    private Constraints validateSeats(List<Seat> seats) {
        Constraints constraints = new Constraints();
        for(Seat seat: seats) {
            constraints.add(validate(seat));
        }
        return constraints;
    }

    private Constraints validate(Seat seat) {
        Constraints constraints = new Constraints();
        constraints.add(AccesoryValidator.validateJavaxConstraints(seat));
        return constraints;
    }

    private int getCapacitySum(EventLocation eventLocation) {
        int sum = 0;
        for(Section section: eventLocation.getSections()) {
            sum += section.getCapacity();
        }
        return sum;
    }
}
