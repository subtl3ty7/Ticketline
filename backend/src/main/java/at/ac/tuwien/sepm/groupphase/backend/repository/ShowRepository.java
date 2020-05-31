package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventTypeEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    /**
     * Find a show by its id.
     *
     * @param id
     * @return a show with corresponding id.
     */
    Show findShowById(Long id);

    /**
     * Find all shows which belong to a single event with event code.
     *
     * @param eventCode
     * @return a list of shows which belong to corresponding event.
     */
    List<Show> findShowsByEventCode(String eventCode);

    List<Show> findShowsByEventLocationOriginalId(Long eventLocationId);

    List<Show> findShowsByEventNameContainingIgnoreCaseAndEventTypeAndEventCategoryAndStartsAtIsGreaterThanEqualAndEndsAtIsLessThanEqualAndDurationLessThanEqualAndPriceLessThanEqual(String name, EventTypeEnum type, EventCategoryEnum category, LocalDateTime startsAt, LocalDateTime endsAt, Duration duration, Integer price);
}
