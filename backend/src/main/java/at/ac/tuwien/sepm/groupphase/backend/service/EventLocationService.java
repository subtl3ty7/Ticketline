package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocationOriginal;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;

import java.util.List;

public interface EventLocationService {
    List<Seat> getAllSeatsByEventLocationId(Long id);
    List<EventLocationOriginal> getAllEventLocations();
    EventLocationOriginal save(EventLocationOriginal eventLocation);

    List<EventLocationOriginal> findAllFilteredEventLocations(EventLocationOriginal searchEventLocation);
}
