package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.validation.NewsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CustomNewsService implements NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final NewsValidator validator;

    @Autowired
    public CustomNewsService(NewsRepository newsRepository, UserRepository userRepository, NewsValidator validator) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Override
    public List<News> findLatestSixUnseenNewsByCustomer(String userCode) {
        validator.validateUserCode(userCode).throwIfViolated();
        Customer customer = (Customer) userRepository.findAbstractUserByUserCode(userCode);
        List<News> news = newsRepository.findAllBySeenByNotContainsOrderByPublishedAtDesc(customer);
        if(news.isEmpty()) {
            throw new NotFoundException("Could not find Unseen News");
        }
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
        news.setNewsCode(this.getNewNewsCode());
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

    @Override
    public void deleteByNewsCode(String newsCode) {

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