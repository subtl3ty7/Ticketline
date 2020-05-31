package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventTypeEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowService {
    List<Seat> getAllSeatsByShowId(Long id);

    List<Show> getShowsByEventLocationId(Long eventLocationId);

    List<Show> findShowsAdvanced(String name, EventTypeEnum type, EventCategoryEnum category, LocalDateTime startsAtDate, LocalDateTime endsAtDate, Duration showDuration, Integer price);
}
