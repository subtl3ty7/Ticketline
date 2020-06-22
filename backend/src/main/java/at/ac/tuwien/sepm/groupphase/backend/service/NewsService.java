package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsService {

    List<News> findLatestUnseen(Authentication auth, Integer limit);

    List<News> findSeenNews(Authentication auth, Integer limit);

    List<News> findLatest(Integer limit);

    News createNewNewsEntry(News news);

    News findByNewsCode(String eventCode, Authentication auth);

    List<News> findAll(int size);

    List<News> findSimpleNewsByParam(String newsCode, String title, String author, LocalDateTime startRangeDate, LocalDateTime endRangeDate, int size);
}
