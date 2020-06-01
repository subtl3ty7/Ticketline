package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
