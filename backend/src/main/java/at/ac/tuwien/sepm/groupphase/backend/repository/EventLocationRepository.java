package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocationOriginal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventLocationRepository extends JpaRepository<EventLocationOriginal, Long> {

    /**
     * Find an event location by id.
     *
     * @param id
     * @return a single event location with id
     */
    EventLocationOriginal findEventLocationById(Long id);

    /**
     * @return a list of event locations with no shows.
     */
    List<EventLocationOriginal> findAll();


    @Query(value = "" +
        "SELECT * FROM EVENT_LOCATION_ORIGINAL e " +
        "WHERE ((:name IS NULL) OR (:name IS NOT NULL AND e.name = :name)) " +
        "AND ((:city IS NULL) OR (:city IS NOT NULL AND e.city = :city)) " +
        "AND ((:street IS NULL) OR (:street IS NOT NULL AND e.street = :street)) " +
        "AND ((:country IS NULL) OR (:country IS NOT NULL AND e.country = :country))  " +
        "AND ((:plz IS NULL) OR (:plz IS NOT NULL AND e.plz = :plz))  " +
        "AND ((:event_location_description IS NULL) OR (:event_location_description IS NOT NULL AND e.event_location_description = :event_location_description ))", nativeQuery = true)
    List<EventLocationOriginal> findAllByNameAndCityAndStreetAndCountryAndPlzAndEventLocationDescription(@Param("name") String name, @Param("city") String city, @Param("street") String street, @Param("country") String country, @Param("plz") String plz, @Param("event_location_description") String description);

}
