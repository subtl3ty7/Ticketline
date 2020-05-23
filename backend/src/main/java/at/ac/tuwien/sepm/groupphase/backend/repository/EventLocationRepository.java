package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {

    /**
     * Find an event location by id.
     *
     * @param id
     * @return a single event location with id
     */
    EventLocation findEventLocationById(Long id);

    /**
     * Find all event locations with no shows.
     *
     * @return a list of event locations with no shows.
     */
    List<EventLocation> findAllByShowIdIsNull();

    /**
     * Find all event locations containing certain location name.
     *
     * @return a list of event locations with specified location name.
     */
    List<EventLocation> findAllByNameContainingIgnoreCase(String locationName);
}
