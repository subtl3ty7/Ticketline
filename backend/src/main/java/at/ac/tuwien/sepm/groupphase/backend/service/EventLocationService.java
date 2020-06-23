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

    /**
     *  Find the event location by its id
     *
     * @param id - id of the event location to look for
     * @return the EventLocation with the given id
     */
    EventLocation getEventLocationById(long id);

    /**
     * Search event locations by their name, city, street, country, plz and description
     *
     * @param searchEventLocation - object that needs to be searched for
     * @param size - number of event location entities on the same page
     * @return a list of all the event locations that match the given criteria
     */
    List<EventLocation> findAllFilteredEventLocations(EventLocation searchEventLocation, int size);
}
