package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocationOriginal;
import org.springframework.data.jpa.repository.JpaRepository;
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

    /**
     * Find all event locations containing certain location name.
     *
     * @return a list of event locations with specified location name.
     */
    List<EventLocation> findAllByNameContainingIgnoreCase(String locationName);
}
