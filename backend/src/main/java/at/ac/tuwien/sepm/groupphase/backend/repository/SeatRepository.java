package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    /**
     * Find a seat by its id
     *
     * @param id - id to look for
     * @return a seat that matches the given id
     */
    Seat findSeatById(Long id);

    /**
     * Find all existing seats
     *
     * @return - a list that contains all existing seats
     */
    ArrayList<Seat> findAll();
}
