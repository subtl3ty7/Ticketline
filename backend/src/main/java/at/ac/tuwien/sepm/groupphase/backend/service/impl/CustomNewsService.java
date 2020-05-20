package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
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
    private final NewsValidator validator;


    @Autowired
    public CustomNewsService(NewsRepository newsRepository, NewsValidator validator) {
        this.newsRepository = newsRepository;
        this.validator = validator;
    }

    @Override
    public List<News> find6UnseenNewsByCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<News> findAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News createNewNewsEntry(News news) {
        news.setNewsCode(this.getNewNewsCode());
        return newsRepository.save(news);
    }

    @Override
    public News findByNewsCode(String eventCode) {
        return null;
    }

    @Override
    public void deleteByNewsCode(String eventCode) {

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