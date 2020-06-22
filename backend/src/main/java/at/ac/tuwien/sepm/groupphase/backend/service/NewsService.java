package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsService {

    /**
     * Find the latest unseen news
     *
     * @param auth
     * @param limit
     * @return
     */
    List<News> findLatestUnseen(Authentication auth, Integer limit);

    /**
     * Find all seen news
     *
     * @param auth
     * @param limit
     * @return
     */
    List<News> findSeenNews(Authentication auth, Integer limit);

    /**
     * Find the latest news
     *
     * @param limit
     * @return
     */
    List<News> findLatest(Integer limit);

    /**
     * Create a new news entry
     *
     * @param news - News object that should be created
     * @return a single, newly-created News object
     */
    News createNewNewsEntry(News news);

    /**
     * Find news by news code
     *
     * @param eventCode
     * @param auth
     * @return
     */
    News findByNewsCode(String eventCode, Authentication auth);

    /**
     * Find all news entries
     * @return a list of all news entries
     */
    List<News> findAll();

    /**
     * Find news entries by their newsCode, title, author, start and end range date
     *
     * @param newsCode - news code to look for
     * @param title - title to look for
     * @param author - author to look for
     * @param startRangeDate - start range date to look for
     * @param endRangeDate - end range date to look for
     * @return a list of news entries that match the given criteria
     */
    List<News> findSimpleNewsByParam(String newsCode, String title, String author, LocalDateTime startRangeDate, LocalDateTime endRangeDate);
}
