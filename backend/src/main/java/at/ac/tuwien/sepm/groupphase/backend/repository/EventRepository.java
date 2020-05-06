package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByStartsAtAfterOrderByTotalTicketsSoldDesc(LocalDateTime time);
    List<Event> findAllByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime time, String category);
    Event findEventByEventCode(String eventCode);
    Event findEventById(Long id);
    void deleteById(Long id);
}