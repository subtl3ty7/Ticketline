package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Find six News entries the customer has not seen yet
     * @param customer the customer
     * @return A list of six News
     */
    List<News> findAllBySeenByNotContainsOrderByPublishedAtDesc(Customer customer);

    /**
     * Find a News entry by newsCode.
     *
     * @param newsCode
     * @return a single News entry which has the corresponding event code
     */
    News findByNewsCode(String newsCode);
}
