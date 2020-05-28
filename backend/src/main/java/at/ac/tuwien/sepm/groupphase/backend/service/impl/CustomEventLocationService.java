package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocationOriginal;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.EventValidator;
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
        EventLocationOriginal eventLocation = eventLocationRepository.findEventLocationById(id);
        List<Seat> seats = new ArrayList<>();
        for(Section section: eventLocation.getSections()) {
            seats.addAll(section.getSeats());
        }
        return seats;
    }

    @Override
    public List<EventLocationOriginal> getAllEventLocations() {
        List<EventLocationOriginal> eventLocations = eventLocationRepository.findAll();
        return eventLocations;
    }

    @Override
    public EventLocationOriginal save(EventLocationOriginal eventLocation) {
        eventValidator.validate(eventLocation).throwIfViolated();
        eventLocation.setShows(new ArrayList<>());
        return eventLocationRepository.save(eventLocation);
    }


    @Override
    public List<EventLocationOriginal> findAllFilteredEventLocations(EventLocationOriginal searchEventLocation) {
        return eventLocationRepository.findAllByNameAndCityAndStreetAndCountryAndPlzAndEventLocationDescription(
            searchEventLocation.getName(),
            searchEventLocation.getCity(),
            searchEventLocation.getStreet(),
            searchEventLocation.getCountry(),
            searchEventLocation.getPlz(),
            searchEventLocation.getEventLocationDescription()
        );
    }
}
