package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findSeatById(Long id);
    ArrayList<Seat> findAll();

    @Query(value = "" +
        "SELECT * FROM " +
        "SEAT s " +
        "WHERE EXISTS ( " +
        "SELECT t.ID " +
        "FROM SECTION t JOIN EVENT_LOCATION e JOIN SHOW sh " +
        "ON sh.ID=:showId AND s.SECTION_ID=t.ID AND t.EVENT_LOCATION_ID=e.ID AND sh.EVENT_LOCATION_ID=e.ID " +
        ") AND NOT EXISTS ( " +
        "SELECT * " +
        "FROM SHOW_TAKEN_SEATS st " +
        "WHERE st.SHOW_ID = :showId AND st.SEAT_ID  = s.ID " +
        ") LIMIT 1", nativeQuery = true)
    Seat findFreeSeatBy(@Param("showId") Long showId);
}
