package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find all events which are after a certain time and ordered by total tickets sold descending.
     *
     * @param time
     * @return a list of event which are after a certain time
     */
    List<Event> findAllByStartsAtAfterOrderByTotalTicketsSoldDesc(LocalDateTime time);

    /**
     * Find all events which are after a certain time, matches a category and ordered by total tickets sold descending.
     *
     * @param time
     * @param category
     * @return a list of event which are after a certain time and matches a category
     */
    List<Event> findAllByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime time, String category);

    /**
     * Find an event by event code.
     *
     * @param eventCode
     * @return a single event which has the corresponding event code
     */
    Event findEventByEventCode(String eventCode);

    List<Event> findEventsByArtistsContaining(Artist artist);
}
