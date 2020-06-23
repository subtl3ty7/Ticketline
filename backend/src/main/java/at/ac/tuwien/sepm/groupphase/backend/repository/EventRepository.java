package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategoryEnum;
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
     * @return a list of event which are taking place after a certain time
     */
    List<Event> findAllByStartsAtAfterOrderByTotalTicketsSoldDesc(LocalDateTime time);

    /**
     * Finds top 10 events which are taking place after a certain time, ordered by number of tickets sold  descending
     *
     * @param time - time
     * @return a list of top 10 events which are taking place after a certain time
     */
    List<Event> findTop10ByStartsAtAfterOrderByTotalTicketsSoldDesc(LocalDateTime time);

    /**
     * Finds x of all existing events, where x is defined by pageable
     *
     * @param pageable - stores information about which page user wants to retrieve
     * @return - all existing events
     */
    Page<Event> findAll(Pageable pageable);

    /**
     * Find all events which are after a certain time, matches a category and ordered by total tickets sold descending.
     *
     * @param time - time to look for
     * @param category - category of the events to look for
     * @return a list of event which are after a certain time and matches a category
     */
    List<Event> findAllByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime time, String category);

    /**
     * Find top 10 events by start time and category, ordering by total number of tickets sold descending
     *
     * @param time - start time to look for
     * @param category - category to look for
     * @return - list of all top 10 events that match the given criteria
     */
    List<Event> findTop10ByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime time, EventCategoryEnum category);

    /**
     * Find an event by event code.
     *
     * @param eventCode
     * @return a single event which has the corresponding event code
     */
    Event findEventByEventCode(String eventCode);

    /**
     * Find x events by some specific artist, where x is defined by pageable
     *
     * @param artist - artist to look for
     * @param pageable - stores information about which page user wants to retrieve
     * @return - events that match the given criteria
     */
    Page<Event> findEventsByArtistsContaining(Artist artist, Pageable pageable);


    /**
     * Find event by its id
     *
     * @param id - id of the event to look for
     * @return a single event which has the corresponding id
     */
    Event findEventById(Long id);

    /**
     * Find x events by their name, ignoring capitalization, where x is defined by pageable
     *
     * @param name - name of the event to look for
     * @param pageable - stores information about which page user wants to retrieve
     * @return events matching the given criteria
     */
    Page<Event> findEventsByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Find x events by their code, name, start and end date, where x is defined by pageable
     *
     * @param eventCode - code of the event to look for
     * @param name - name to look for
     * @param startsAt - start time to look for
     * @param endsAt - end time to look for
     * @param pageable - stores information about which page user wants to retrieve
     * @return - event(s) that match the given criteria
     */
    Page<Event> findAllByEventCodeContainingIgnoreCaseAndNameContainingIgnoreCaseAndStartsAtBetween(String eventCode, String name, LocalDateTime startsAt, LocalDateTime endsAt, Pageable pageable);

    /**
     * Find x events by their name, type, category, start and end time and duration, where x is defined by pageable
     *
     * @param name - name to look for
     * @param type - type to look for
     * @param category - category to look for
     * @param startsAt - start time to look for
     * @param endsAt - end time to look for
     * @param duration - duration to look for
     * @param pageable - stores information about which page user wants to retrieve
     * @return - events that match the given criteria
     */
    @Query(value = "" +
        "SELECT * FROM EVENT e " +
        "WHERE ((:name IS NULL) OR (:name IS NOT NULL AND LOWER(e.name)  LIKE LOWER(CONCAT('%',:name,'%')))) " +
        "AND ((:type IS NULL) OR (:type IS NOT NULL AND e.type = :type)) " +
        "AND ((:category IS NULL) OR (:category IS NOT NULL AND e.category = :category))  " +
        "AND ((:startsAt IS NULL) OR (:startsAt IS NOT NULL AND e.start_datetime > :startsAt))  " +
        "AND ((:endsAt IS NULL) OR (:endsAt IS NOT NULL AND e.end_datetime < :endsAt))  " +
        "AND ((:duration IS NULL) OR (:duration IS NOT NULL AND e.duration <= :duration))", nativeQuery = true)
    Page<Event> findEventsByNameContainingIgnoreCaseAndTypeContainingAndCategoryContainingAndStartsAtIsGreaterThanEqualAndEndsAtIsLessThanEqualAndShowsDurationLessThanEqual(@Param("name") String name, @Param("type") Integer type, @Param("category") Integer category, @Param("startsAt") LocalDateTime startsAt, @Param("endsAt") LocalDateTime endsAt, @Param("duration") Duration duration, Pageable pageable);

    /**
     * Find x events where x is defined by pageable
     * @param pageable - stores information about which page user wants to retrieve
     * @return x events
     */
    List<Event> findAllBy(Pageable pageable);
}
