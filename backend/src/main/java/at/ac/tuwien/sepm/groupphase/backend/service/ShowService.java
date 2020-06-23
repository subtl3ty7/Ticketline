package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowService {

    /**
     * Get all seats for the show
     *
     * @param id id of the show
     * @return a list of all seats for the given show
     */
    List<Seat> getAllSeatsByShowId(Long id);

    /**
     * Find x shows by their event location, where x is defined by size
     *
     * @param eventLocationId - id of the event location to look for
     * @param size - number of show entries on the same page
     * @return a list of shows for one page that match the given criteria
     */
    List<Show> getShowsByEventLocationId(Long eventLocationId, int size);

    /**
     * Find x shows by their name, type, category, start and end date, duration, price, where x is defined by size
     *
     * @param name - name of the show to look for
     * @param type - type of the show to look for
     * @param category - category of the show to look for
     * @param startDate - start date to look for
     * @param endDate - end date to look for
     * @param showDuration - duration to look for
     * @param price - price to look for
     * @param size - number of show entries on the same page
     * @return a list of shows for one page that match the given criteria
     */
    List<Show> findShowsAdvanced(String name, Integer type, Integer category, LocalDateTime startDate, LocalDateTime endDate, Duration showDuration, Integer price, int size);

    /**
     * Find show by its id
     *
     * @param id - id of the show
     * @param initEventLocation - determines if the event location should be initialized
     * @return a show that matches the given id
     */
    Show findShowById(Long id, boolean initEventLocation);

    /**
     * Determines if the seat is free or not
     *
     * @param show - show to which this seat belongs
     * @param seat - seat
     * @return a boolean variable, true if the seat is free, otherwise false
     */
    boolean isSeatFree(Show show, Seat seat);

    /**
     * Find free seat for some specific show
     *
     * @param show - show where a free seat needs to be found
     * @return a free seat for the given show
     */
    public Seat findFreeSeat(Show show);
}
