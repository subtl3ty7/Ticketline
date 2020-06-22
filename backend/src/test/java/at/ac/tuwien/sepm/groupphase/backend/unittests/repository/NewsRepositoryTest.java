package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class NewsRepositoryTest implements TestData {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void givenNothing_whenSaveNews_thenFindListWithOneElementAndFindNewsByNewsCode() {
        News news = News.builder()
            .id(ID)
            .newsCode(USER_CODE)
            .title(TEST_NEWS_TITLE)
            .publishedAt(TEST_NEWS_PUBLISHED_AT)
            .stopsBeingRelevantAt(TEST_NEWS_PUBLISHED_AT)
            .summary(TEST_NEWS_SUMMARY)
            .text(TEST_NEWS_TEXT)
            .author(FNAME)
            .photo(PHOTO)
            .build();

        newsRepository.save(news);

        assertAll(
            () -> assertEquals(1, newsRepository.findAll().size()),
            () -> assertNotNull(newsRepository.findByNewsCode(news.getNewsCode()))
        );
    }
}
