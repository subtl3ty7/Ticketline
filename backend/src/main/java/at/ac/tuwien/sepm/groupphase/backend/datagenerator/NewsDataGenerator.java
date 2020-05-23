package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.util.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Profile("generateData")
@Component("NewsDataGenerator")
@DependsOn({"UserDataGenerator"})
public class NewsDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsService newsService;
    private final NewsRepository newsRepository;
    private final UserService userService;
    private final Resources resources;

    private static final int NUMBER_OF_NEWS = 10;

    @Autowired
    public NewsDataGenerator(NewsService newsService, NewsRepository newsRepository, UserService userService, Resources resources) {
        this.newsService = newsService;
        this.newsRepository = newsRepository;
        this.userService = userService;
        this.resources = resources;
    }

    @PostConstruct
    private void generate() {
        if(newsService.findLatest(null).size() > 0) {
            LOGGER.info("News Test Data already generated");
        } else {
            LOGGER.info("Generating News Test Data");
            LocalDateTime start = LocalDateTime.now();
            generateNews();
            LocalDateTime end = LocalDateTime.now();
            float runningTime = Duration.between(start, end).toMillis();
            LOGGER.info("Generating News Test Data took " + runningTime/1000.0 + " seconds");
        }
    }

    private void generateNews() {
        List<Customer> customers = getCustomers();
        int splitUp = customers.size()/NUMBER_OF_NEWS;


        for(int i=0; i<NUMBER_OF_NEWS; i++) {

            String imgName = "news_img" + i + ".jpg";

            News newsEntry = News.builder()
                .author("J.K. Rowling")
                .photo(resources.getImageEncoded(imgName))
                .publishedAt(null)
                .stopsBeingRelevantAt(LocalDateTime.now().plusWeeks(i))
                .title("News " + i + resources.getText("news_title.txt"))
                .summary(resources.getText("news_summary.txt"))
                .text(resources.getText("news_text.txt"))
                .seenBy(customers.subList(i*splitUp, (i+1)*splitUp))
                .build();
            newsService.createNewNewsEntry(newsEntry);
            newsEntry.setPublishedAt(LocalDateTime.now().minusWeeks(i));
            newsRepository.save(newsEntry);
        }

    }

    private List<Customer> getCustomers() {
        List<AbstractUser> users = userService.loadAllUsers();
        List<Customer> customers = new ArrayList<>();
        for(AbstractUser user: users) {
            if(user.getClass() == Customer.class) {
                customers.add((Customer) user);
            }
        }
        return customers;
    }
}
