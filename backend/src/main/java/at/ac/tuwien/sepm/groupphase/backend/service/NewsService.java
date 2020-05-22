package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface NewsService {

    List<News> findLatestUnseen(Authentication auth);

    List<News> findSeenNews(Authentication auth);

    List<News> findLatest();

    List<News> findAllNews();

    News createNewNewsEntry(News news);

    News findByNewsCode(String eventCode);
}
