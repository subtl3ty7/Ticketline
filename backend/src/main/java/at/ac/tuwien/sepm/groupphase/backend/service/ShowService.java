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

    List<Show> getShowsByEventLocationId(Long eventLocationId, int size);

    List<Show> findShowsAdvanced(String name, Integer type, Integer category, LocalDateTime startDate, LocalDateTime endDate, Duration showDuration, Integer price, int size);

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

    public Seat findFreeSeat(Show show);
}
