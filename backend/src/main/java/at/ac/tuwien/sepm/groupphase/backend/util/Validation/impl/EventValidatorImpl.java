package at.ac.tuwien.sepm.groupphase.backend.util.Validation.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.Constraints;
import at.ac.tuwien.sepm.groupphase.backend.util.Validation.EventValidator;
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

    @Autowired
    public EventValidatorImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Constraints validateEventCode(String eventCode) {
        Constraints constraints = new Constraints();
        constraints.add("eventCode_unique", eventRepository.findEventByEventCode(eventCode) == null);
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
            constraints.add(AccesoryValidator.validateJavaxConstraints(section));
            constraints.add(validate(section));
        }
        return constraints;
    }

    private Constraints validate(Section section) {
        Constraints constraints = new Constraints();
        constraints.add(AccesoryValidator.validateJavaxConstraints(section));
        constraints.add("section_capacity", section.getCapacity() >= section.getSeats().size());
        constraints.add(validateSeats(section.getSeats()));
        return constraints;
    }

    private Constraints validateSeats(List<Seat> seats) {
        Constraints constraints = new Constraints();
        for(Seat seat: seats) {
            constraints.add(AccesoryValidator.validateJavaxConstraints(seat));
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
