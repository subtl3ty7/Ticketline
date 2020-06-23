package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Find latest News entries the customer has not seen yet
     * @param customer the customer
     * @return A list of News
     */
    List<News> findAllBySeenByNotContainsOrderByPublishedAtDesc(Customer customer);

    /**
     * Find latest News entries the customer has already seen
     * @param customer the customer
     * @return A list of News
     */
    List<News> findAllBySeenByContainsOrderByPublishedAtDesc(Customer customer, Pageable pageable);

    /**
     * Find x news where x is defined by pageable
     *
     * @param pageable -  stores information about which page user wants to retrieve
     * @return x news
     */
    Page<News> findAll(Pageable pageable);

    /**
     * Find x latest News entries, where x is defined by pageable
     *
     * @param pageable  -  stores information about which page user wants to retrieve
     * @return A list of x News
     */
    List<News> findAllByOrderByPublishedAtDesc(Pageable pageable);

    /**
     * Find a News entry by newsCode.
     *
     * @param newsCode - news code to look for
     * @return a single News entry which has the corresponding event code
     */
    News findByNewsCode(String newsCode);

    /**
     * Find x news by their newsCode, title, author, start and end date, where x is defined by pageable
     *
     * @param newsCode - code of the news to look for
     * @param title - title to look for
     * @param author - author to look for
     * @param startRangeDate - start date to look for
     * @param endRangeDate - end date to look for
     * @param pageable - stores information about which page user wants to retrieve
     * @return - x news that match the given criteria
     */
    Page<News> findAllByNewsCodeContainingIgnoreCaseAndTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndPublishedAtBetween(String newsCode,
                                                                                                                                   String title,
                                                                                                                                   String author,
                                                                                                                                   LocalDateTime startRangeDate,
                                                                                                                                   LocalDateTime endRangeDate,
                                                                                                                                   Pageable pageable);
}
