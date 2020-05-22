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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
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
    public List<News> findLatestUnseen(Authentication auth) {
        AbstractUser user = userService.getAuthenticatedUser(auth);
        validator.validateUser(user).throwIfViolated();
        Customer customer = (Customer) user;
        List<News> news = newsRepository.findAllBySeenByNotContainsOrderByPublishedAtDesc(customer);
        if (news.size() > 6) {
            return news.subList(0, 6);
        } else {
            return news;
        }
    }

    @Override
    public List<News> findSeenNews(Authentication auth) {
        AbstractUser user = userService.getAuthenticatedUser(auth);
        validator.validateUser(user).throwIfViolated();
        Customer customer = (Customer) user;
        return newsRepository.findAllBySeenByContainsOrderByPublishedAtDesc(customer);
    }


    @Override
    public List<News> findLatest() {
        List<News> news = newsRepository.findAllByOrderByPublishedAtDesc();
        if (news.size() > 6) {
            return news.subList(0, 6);
        } else {
            return news;
        }
    }

    @Override
    public List<News> findAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News createNewNewsEntry(News news) {
        if(news != null) {
            news.setNewsCode(this.getNewNewsCode());
            news.setPublishedAt(LocalDateTime.now());
        }

        validator.validate(news).throwIfViolated();
        return newsRepository.save(news);
    }

    @Override
    public News findByNewsCode(String newsCode) {
        News news = newsRepository.findByNewsCode(newsCode);
        if (news==null) {
            throw new NotFoundException("Could not find this News entry");
        }
        return news;
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