package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
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
public interface ShowRepository extends JpaRepository<Show, Long> {

    /**
     * Find a show by its id.
     *
     * @param id
     * @return a show with corresponding id.
     */
    Show findShowById(Long id);

    /**
     * Find all shows
     *
     * @return a list that contains all shows
     */
    ArrayList<Show> findAll();

    /**
     * Find all shows which belong to a single event with event code.
     *
     * @param eventCode - event code to look for
     * @return a list of shows which belong to corresponding event.
     */
    List<Show> findShowsByEventCode(String eventCode);

    /**
     * Find x shows by their event location, where x is defined by pageable
     *
     * @param eventLocationId - id of the event location to look for
     * @param pageable -  stores information about which page user wants to retrieve
     * @return - x shows that match the given criteria
     */
    Page<Show> findShowsByEventLocationId(Long eventLocationId, Pageable pageable);

    /**
     * Find x shows by their event name, type, category, start and end time, price and duration, where x is defined by pageable
     *
     * @param eventName - name of the event to look for
     * @param type - type to look for
     * @param category - category to look for
     * @param startsAt - start time to look for
     * @param endsAt - end time to look for
     * @param duration - duration to look for
     * @param price - price to look for
     * @param pageable -  stores information about which page user wants to retrieve
     * @return - x shows that match the corresponding criteria
     */
    @Query(value = "" +
        "SELECT * FROM SHOW s " +
        "WHERE ((:eventName IS NULL) OR (:eventName IS NOT NULL AND LOWER(s.event_name) LIKE LOWER(CONCAT('%',:eventName,'%')))) " +
        "AND ((:type IS NULL) OR (:type IS NOT NULL AND s.event_type = :type)) " +
        "AND ((:category IS NULL) OR (:category IS NOT NULL AND s.event_category = :category))  " +
        "AND ((:startsAt IS NULL) OR (:startsAt IS NOT NULL AND s.start_datetime > :startsAt))  " +
        "AND ((:endsAt IS NULL) OR (:endsAt IS NOT NULL AND s.end_datetime < :endsAt))  " +
        "AND ((:price IS NULL) OR (:price IS NOT NULL AND s.price <= :price))  " +
        "AND ((:duration IS NULL) OR (:duration IS NOT NULL AND s.duration <= :duration))", nativeQuery = true)
    Page<Show> findShowsByEventNameContainingIgnoreCaseAndEventTypeOrEventTypeIsNullAndEventCategoryOrEventCategoryIsNullAndStartsAtIsGreaterThanEqualAndEndsAtIsLessThanEqualAndDurationLessThanEqualAndPriceLessThanEqualOrPriceIsNull(@Param("eventName") String eventName, @Param("type") Integer type, @Param("category") Integer category, @Param("startsAt") LocalDateTime startsAt, @Param("endsAt") LocalDateTime endsAt, @Param("duration") Duration duration, @Param("price") Integer price , Pageable pageable);

    @Query(value = "SELECT ID FROM SHOW", nativeQuery = true)
    List<Long> findAllShowIds();
}
