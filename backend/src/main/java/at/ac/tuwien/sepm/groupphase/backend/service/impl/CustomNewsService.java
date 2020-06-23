package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.NewsValidator;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomNewsService implements NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final NewsValidator validator;

    @Autowired
    public CustomNewsService(NewsRepository newsRepository, UserRepository userRepository, UserService userService, NewsValidator validator) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    public List<News> findLatestUnseen(Authentication auth, Integer limit) {
        AbstractUser user = userService.getAuthenticatedUser(auth);
        validator.validateUser(user).throwIfViolated();
        Customer customer = (Customer) user;
        List<News> news = newsRepository.findAllBySeenByNotContainsOrderByPublishedAtDesc(customer);
        if(limit == null || news.size() <= limit) {
            //return all
            return news;
        } else {
            //return sublist
            return news.subList(0, limit);
        }
    }

    @Override
    public List<News> findSeenNews(Integer page, Integer size, Authentication auth) {
        AbstractUser user = userService.getAuthenticatedUser(auth);
        validator.validateUser(user).throwIfViolated();
        Customer customer = (Customer) user;
        if(page==null) {
            page = 0;
        }
        if(size==null) {
            size = Integer.MAX_VALUE;
        }
        List<News> news = newsRepository.findAllBySeenByContainsOrderByPublishedAtDesc(customer, PageRequest.of(page, size));
        return news;
    }


    @Override
    public List<News> findLatest(Integer page, Integer size) {
        if(page==null) {
            page = 0;
        }
        if(size==null) {
            size = Integer.MAX_VALUE;
        }
        List<News> news = newsRepository.findAllByOrderByPublishedAtDesc(PageRequest.of(page, size));
        return news;
    }

    @Override
    public News createNewNewsEntry(News news) {
        if(news != null) {
            news.setNewsCode(this.getNewNewsCode());
            news.setPublishedAt(LocalDateTime.now());
            news.setSeenBy(new ArrayList<>());
        }

        validator.validate(news).throwIfViolated();
        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public News findByNewsCode(String newsCode, Authentication auth) {
        AbstractUser user = userService.getAuthenticatedUser(auth);
        validator.validateUser(user).throwIfViolated();
        News news = newsRepository.findByNewsCode(newsCode);
        Hibernate.initialize(news.getSeenBy());
        if (news==null) {
            throw new NotFoundException("Could not find this News entry");
        } else {
            if(user instanceof Customer) {
                //if news entry was found, then mark the news entry as seen by the customer
                Customer customer = (Customer) user;
                this.markAsSeen(customer, news);
            }
        }
        return news;
    }

    @Override
    public List<News> findAll(int size) {
        int page = calculateNumberOfPage(size);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<News> news = newsRepository.findAll(pageRequest);
        return news.toList();
    }

    private int calculateNumberOfPage(int size) {
        int result = 0;
        if (size != 0) {
            result = size / 10;
        }
        return result;
    }
    @Override
    public List<News> findSimpleNewsByParam(String newsCode,
                                            String title,
                                            String author,
                                            LocalDateTime startRange,
                                            LocalDateTime endRange,
                                            int size) {
        int page = calculateNumberOfPage(size);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<News> news =  newsRepository.findAllByNewsCodeContainingIgnoreCaseAndTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndPublishedAtBetween(newsCode, title, author, startRange, endRange, pageRequest);
        return news.toList();
    }

    private void markAsSeen(Customer customer, News news) {
        news.getSeenBy().add(customer);
        newsRepository.save(news);
    }

    private String getNewNewsCode() {
        final int maxAttempts = 1000;
        String newsCode = "";
        int i;
        for(i=0; i<maxAttempts; i++) {
            newsCode = CodeGenerator.generateNewsCode();
            if(!validator.validateNewsCode(newsCode).isViolated()) {
                break;
            }
        }
        if(i==1000) {
            throw new ServiceException("Something went wrong while generating newsCode", null);
        }
        return newsCode;
    }
}