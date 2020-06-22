package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    /**
     * Find all artist entries ordered alphabetically by last name and first name.
     *
     * @return ordered list of all artist entries
     */
    List<Artist> findAllByOrderByLastNameAscFirstNameAsc();

    Page<Artist> findArtistsByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName, Pageable pageable);

    Artist findArtistById(Long id);

    List<Artist> findArtistsByFirstNameAndLastName(String firstName, String lastName);

}
