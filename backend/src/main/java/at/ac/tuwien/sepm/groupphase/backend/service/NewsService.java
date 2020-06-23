package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsService {

    /**
     * Find the latest unseen news
     *
     * @param auth - stores user authentication data (for example - email)
     * @param limit - how many news should be displayed
     * @return a list of the latest unseen news
     */
    List<News> findLatestUnseen(Authentication auth, Integer limit);

    List<News> findSeenNews(Integer page, Integer size, Authentication auth);

    List<News> findLatest(Integer page, Integer size);

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
     * @param eventCode - code of the news
     * @param auth - stores user authentication data (for example - email)
     * @return - a news entry that maches the given code
     */
    News findByNewsCode(String eventCode, Authentication auth);

    List<News> findAll(int size);

    List<News> findSimpleNewsByParam(String newsCode, String title, String author, LocalDateTime startRangeDate, LocalDateTime endRangeDate, int size);
}


