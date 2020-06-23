package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {

    /**
     * Find an event location by id.
     *
     * @param id - id of the event location to look for
     * @return a single event location with id
     */
    EventLocation findEventLocationById(Long id);

    /**
     * @return a list of event locations.
     */
    List<EventLocation> findAll();


    /**
     * Find x of all the event locations that match the given criteria, where x is defined by pageable
     *
     * @param name - name to look for
     * @param city - city to look for
     * @param street - street to look for
     * @param country - country to look for
     * @param plz - plz to look for
     * @param description - description to look for
     * @param pageable - stores information about which page user wants to retrieve
     * @return event locations that match the given criteria
     */
    @Query(value = "" +
        "SELECT * FROM EVENT_LOCATION e " +
        "WHERE ((:name IS NULL) OR (:name IS NOT NULL AND LOWER(e.name) LIKE LOWER(CONCAT('%',:name,'%')))) " +
        "AND ((:city IS NULL) OR (:city IS NOT NULL AND LOWER(e.city) LIKE LOWER(CONCAT('%',:city,'%')))) " +
        "AND ((:street IS NULL) OR (:street IS NOT NULL AND LOWER(e.street) LIKE LOWER(CONCAT('%',:street,'%')))) " +
        "AND ((:country IS NULL) OR (:country IS NOT NULL AND LOWER(e.country) LIKE LOWER(CONCAT('%',:country,'%'))))  " +
        "AND ((:plz IS NULL) OR (:plz IS NOT NULL AND e.plz LIKE %:plz%))  " +
        "AND ((:event_location_description IS NULL) OR (:event_location_description IS NOT NULL AND LOWER(e.event_location_description) LIKE LOWER(CONCAT('%',:event_location_description,'%'))))", nativeQuery = true)
    Page<EventLocation> findAllByNameAndCityAndStreetAndCountryAndPlzAndEventLocationDescription(@Param("name") String name, @Param("city") String city, @Param("street") String street, @Param("country") String country, @Param("plz") String plz, @Param("event_location_description") String description, Pageable pageable);

    /**
     * Find an EventLocation with the given name
     * @param name the name of the eventLocation
     * @return the eventLocation
     */
    List<EventLocation> findByName(String name);
}
