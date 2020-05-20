package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
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
    private final UserService userService;
    private final ResourceLoader resourceLoader;

    public NewsDataGenerator(NewsService newsService,
                             UserService userService,
                             ResourceLoader resourceLoader) {
        this.newsService = newsService;
        this.userService = userService;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    private void generate() {
        if(newsService.findAllNews().size() > 0) {
            LOGGER.debug("News Test Data already generated");
        } else {
            generateNews();
        }
    }

    private void generateNews() {
        LOGGER.debug("\n\n\n\n\n GENERATING NEWS \n\n\n\n\n\n");

        News newsEntry = News.builder()
            .author("J.K. Rowling")
            .photo(getImage("event_img0.jpg"))
            .publishedAt(LocalDateTime.now())
            .stopsBeingRelevantAt(LocalDateTime.of(2021, 5, 5, 5, 5))
            .title("These are the hottest events in May")
            .summary("You should check it out")
            .text("All of them are good, actually")
            .seenBy(this.getCustomers())
            .build();

        newsService.createNewNewsEntry(newsEntry);
    }

    private List<Customer> getCustomers() {
        List<AbstractUser> users = userService.loadAllUsers();
        List<Customer> customers = new ArrayList<>();
        for(AbstractUser user: users) {
            if(user.getClass() == Customer.class) {
                customers.add((Customer) user);
            }
        }
        return customers.subList(0,customers.size()/2);
    }


    private String getImage(String imgName) {
        try {
            InputStream inputStream = resourceLoader.getResource("classpath:" + imgName).getInputStream();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String encodedString = Base64.getEncoder().encodeToString(inputStream.readAllBytes());
            //String contents = reader.lines()
            //   .collect(Collectors.joining(System.lineSeparator()));
            encodedString = "data:image/" + getFileExtension(imgName) + ";base64," + encodedString;
            return encodedString;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load Image File for EventDataGenerator.", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Couldn't load Image File for EventDataGenerator.", e);
        }
    }

    private String getFileExtension(String imgName) {
        if(!imgName.contains(".")) {
            throw new RuntimeException("Could not retrieve file extension of filename ." + imgName);
        }
        int dotIndex = imgName.indexOf('.');
        String fileExtension = imgName.substring(dotIndex+1);
        return fileExtension;
    }
}
