package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;

import java.util.List;

public interface EventLocationService {
    List<Seat> getAllSeatsByEventLocationId(Long id);
    List<EventLocation> getAllEventLocations();
    EventLocation save(EventLocation eventLocation);
    EventLocation getEventLocationById(long id);
    List<EventLocation> findAllFilteredEventLocations(EventLocation searchEventLocation);
}
