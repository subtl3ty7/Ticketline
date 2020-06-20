package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.hibernate.annotations.LazyCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    List<Event> findTop10ByStartsAtAfterOrderByTotalTicketsSoldDesc(LocalDateTime time);

    Page<Event> findAll(Pageable pageable);

    /**
     * Find all events which are after a certain time, matches a category and ordered by total tickets sold descending.
     *
     * @param time
     * @param category
     * @return a list of event which are after a certain time and matches a category
     */
    List<Event> findAllByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime time, String category);

    List<Event> findTop10ByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime time, String category);

    /**
     * Find an event by event code.
     *
     * @param eventCode
     * @return a single event which has the corresponding event code
     */
    Event findEventByEventCode(String eventCode);

    List<Event> findEventsByArtistsContaining(Artist artist);


    Event findEventById(Long id);

    List<Event> findEventsByNameContainingIgnoreCase(String name);

    List<Event> findAllByEventCodeContainingIgnoreCaseAndNameContainingIgnoreCaseAndStartsAtBetween(String eventCode, String name, LocalDateTime startsAt, LocalDateTime endsAt);

    @Query(value = "" +
        "SELECT * FROM EVENT e " +
        "WHERE ((:name IS NULL) OR (:name IS NOT NULL AND LOWER(e.name)  LIKE LOWER(CONCAT('%',:name,'%')))) " +
        "AND ((:type IS NULL) OR (:type IS NOT NULL AND e.type = :type)) " +
        "AND ((:category IS NULL) OR (:category IS NOT NULL AND e.category = :category))  " +
        "AND ((:startsAt IS NULL) OR (:startsAt IS NOT NULL AND e.start_datetime > :startsAt))  " +
        "AND ((:endsAt IS NULL) OR (:endsAt IS NOT NULL AND e.end_datetime < :endsAt))  " +
        "AND ((:duration IS NULL) OR (:duration IS NOT NULL AND e.duration <= :duration))", nativeQuery = true)
    List<Event> findEventsByNameContainingIgnoreCaseAndTypeContainingAndCategoryContainingAndStartsAtIsGreaterThanEqualAndEndsAtIsLessThanEqualAndShowsDurationLessThanEqual(@Param("name") String name, @Param("type") Integer type, @Param("category") Integer category, @Param("startsAt") LocalDateTime startsAt, @Param("endsAt") LocalDateTime endsAt, @Param("duration") Duration duration);

    /**
     * Find x events where x is defined by pabgeable
     * @param pageable
     * @return x events
     */
    List<Event> findAllBy(Pageable pageable);
}
