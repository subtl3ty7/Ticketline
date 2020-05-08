package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import at.ac.tuwien.sepm.groupphase.backend.util.Validation.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomEventLocationService implements EventLocationService {

    private final EventLocationRepository eventLocationRepository;
    private final EventValidator eventValidator;

    @Autowired
    public CustomEventLocationService(EventLocationRepository eventLocationRepository, EventValidator eventValidator) {
        this.eventLocationRepository = eventLocationRepository;
        this.eventValidator = eventValidator;
    }

    @Override
    public List<Seat> getAllSeatsByEventLocationId(Long id) {
        EventLocation eventLocation = eventLocationRepository.findEventLocationById(id);
        List<Seat> seats = new ArrayList<>();
        for(Section section: eventLocation.getSections()) {
            seats.addAll(section.getSeats());
        }
        return seats;
    }

    @Override
    public List<EventLocation> getAllEventLocations() {
        List<EventLocation> eventLocations = eventLocationRepository.findAllByShowIdIsNull();
        return eventLocations;
    }

    @Override
    public EventLocation save(EventLocation eventLocation) {
        eventValidator.validate(eventLocation).throwIfViolated();
        return eventLocationRepository.save(eventLocation);
    }
}
