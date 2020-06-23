package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsService {

    /**
     * Find the latest unseen news articles
     *
     * @param auth - stores user authentication data (for example - email)
     * @param limit - how many news should be displayed
     * @return a list of the latest unseen news
     */
    List<News> findLatestUnseen(Authentication auth, Integer limit);

    /**
     * Find the news articles that the user already saw
     *
     * @param page - index from the page
     * @param size - number of news entries on the same page
     * @param auth - stores user authentication data (for example - email)
     * @return - a list of news articles that the customer already saw
     */
    List<News> findSeenNews(Integer page, Integer size, Authentication auth);

    /**
     * Finds the latest news (the news that are recently added)
     *
     * @param page - index from the page
     * @param size - number of news entries on the same page
     * @return - a list of recently added news articles
     */
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

    /**
     * Find all news articles
     *
     * @param size - number of news entries on the same page
     * @return - a list of all the news articles
     */
    List<News> findAll(int size);

    /**
     * Find simple news according to newsCode, title, author, startRangeDate and endRangeDate
     *
     * @param newsCode - a code of the news to look for
     * @param title - title of the news article to look for
     * @param author - author of the news article to look for
     * @param startRangeDate - start range date to look for
     * @param endRangeDate - end range date to look for
     * @param size - number of news entries on the same page
     * @return - a list of news articles that match the given criteria
     */
    List<News> findSimpleNewsByParam(String newsCode, String title, String author, LocalDateTime startRangeDate, LocalDateTime endRangeDate, int size);
}


