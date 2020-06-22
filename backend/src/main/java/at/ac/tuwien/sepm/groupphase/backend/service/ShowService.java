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
     * Get all shows that are taking place on some specific event location
     *
     * @param eventLocationId - id of the event location
     * @return a list of all shows that are taking place on the given EventLocation
     */
    List<Show> getShowsByEventLocationId(Long eventLocationId);

    /**
     * Find shows by their name, type, category, start and end date, duration and price
     *
     * @param name - name of the show
     * @param type - type of the show
     * @param category - category of the show
     * @param startDate - start date of the show
     * @param endDate - end date of the show
     * @param showDuration - duration of the show
     * @param price - price of the show
     * @return a list of the shows that match the given criteria
     */
    List<Show> findShowsAdvanced(String name, Integer type, Integer category, LocalDateTime startDate, LocalDateTime endDate, Duration showDuration, Integer price);

    /**
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
}
