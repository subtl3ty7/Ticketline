package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;

import java.util.List;

public interface NewsService {

    List<News> findLatestSixUnseenNewsByCustomer(String userCode);

    List<News> findLatestSix();

    List<News> findAllNews();

    List<News> findSeenNews(String userCode);

    News createNewNewsEntry(News news);

    News findByNewsCode(String eventCode);
}
