package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.EventValidator;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<EventLocation> getAllEventLocations() {
        List<EventLocation> eventLocations = eventLocationRepository.findAll();
        return eventLocations;
    }

    @Override
    @Transactional
    public EventLocation getEventLocationById(long id) {
        EventLocation eventLocation = eventLocationRepository.getOne(id);
        Hibernate.initialize(eventLocation.getShows());
        Hibernate.initialize(eventLocation.getSections());
        for (Section s: eventLocation.getSections()) {
            Hibernate.initialize(s.getSeats());
        }
        return eventLocation;
    }

    @Override
    public EventLocation save(EventLocation eventLocation) {
        eventValidator.validate(eventLocation).throwIfViolated();
        eventLocation.setShows(new ArrayList<>());
        this.setSeatPrices(eventLocation);
        for(Section section: eventLocation.getSections()) {
            //round the price to 2 decimals
            section.setPrice(Math.round(section.getPrice()*100.0)/100.0);
        }
        return eventLocationRepository.save(eventLocation);
    }


    @Override
    @Transactional
    public List<EventLocation> findAllFilteredEventLocations(EventLocation searchEventLocation, int size) {
        int page = calculateNumberOfPage(size);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<EventLocation> eventLocationsPage = eventLocationRepository.findAllByNameAndCityAndStreetAndCountryAndPlzAndEventLocationDescription(
            searchEventLocation.getName(),
            searchEventLocation.getCity(),
            searchEventLocation.getStreet(),
            searchEventLocation.getCountry(),
            searchEventLocation.getPlz(),
            searchEventLocation.getEventLocationDescription(),
            pageRequest
        );
        List<EventLocation> eventLocations = eventLocationsPage.toList();
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

    private int calculateNumberOfPage(int size) {
        int result = 0;
        if (size != 0) {
            result = size / 10;
        }
        return result;
    }
}
