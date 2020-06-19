package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowService {
    List<Seat> getAllSeatsByShowId(Long id);

    List<Show> getShowsByEventLocationId(Long eventLocationId);

    List<Show> findShowsAdvanced(String name, Integer type, Integer category, LocalDateTime startDate, LocalDateTime endDate, Duration showDuration, Integer price);

    Show findShowById(Long id, boolean initEventLocation);

    boolean isSeatFree(Show show, Seat seat);

    public Seat findFreeSeat(Show show);
}
