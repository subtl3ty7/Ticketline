package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.EventValidator;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<EventLocation> getAllEventLocations() {
        List<EventLocation> eventLocations = eventLocationRepository.findAll();
        for(EventLocation eventLocation: eventLocations) {
            Hibernate.initialize(eventLocation.getShows());
        }
        return eventLocations;
    }

    @Override
    public EventLocation save(EventLocation eventLocation) {
        eventValidator.validate(eventLocation).throwIfViolated();
        eventLocation.setShows(new ArrayList<>());
        this.setSeatPrices(eventLocation);
        return eventLocationRepository.save(eventLocation);
    }


    @Override
    @Transactional
    public List<EventLocation> findAllFilteredEventLocations(EventLocation searchEventLocation) {
        List<EventLocation> eventLocations = eventLocationRepository.findAllByNameAndCityAndStreetAndCountryAndPlzAndEventLocationDescription(
            searchEventLocation.getName(),
            searchEventLocation.getCity(),
            searchEventLocation.getStreet(),
            searchEventLocation.getCountry(),
            searchEventLocation.getPlz(),
            searchEventLocation.getEventLocationDescription()
        );
        for(EventLocation eventLocation: eventLocations) {
            Hibernate.initialize(eventLocation.getShows());
        }
        return eventLocations;
    }

    private void setSeatPrices(EventLocation eventLocation) {
        for(Section section: eventLocation.getSections()) {
            for(Seat seat: section.getSeats()) {
                seat.setPrice(section.getPrice());
            }
        }
    }
}
