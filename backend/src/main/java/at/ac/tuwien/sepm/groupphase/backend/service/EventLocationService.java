package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;

import java.util.List;

public interface EventLocationService {
    /**
     * Get all seats at some specific event location
     *
     * @param id id of the event location to look for
     * @return a list of all seats that the given event location contains
     */
    List<Seat> getAllSeatsByEventLocationId(Long id);

    /**
     * Find all event locations
     *
     * @return a list of all event locations
     */
    List<EventLocation> getAllEventLocations();

    /**
     * Create a new event location
     *
     * @param eventLocation - EventLocation object that needs to be created
     * @return a single, newly-created EventLocation
     */
    EventLocation save(EventLocation eventLocation);
    EventLocation getEventLocationById(long id);
    List<EventLocation> findAllFilteredEventLocations(EventLocation searchEventLocation, int size);
}
