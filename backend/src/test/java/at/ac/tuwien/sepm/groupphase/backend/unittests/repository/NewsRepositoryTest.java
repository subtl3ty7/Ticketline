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

    private News news = News.builder()
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

    @Autowired
    private NewsRepository newsRepository;

    @BeforeEach
    public void beforeEach() {
        newsRepository.deleteAll();
        news = News.builder()
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
    }

    @Test
    public void givenNothing_whenSaveNews_thenFindListWithOneElementAndFindNewsByNewsCode() {
        newsRepository.save(news);

        assertAll(
            () -> assertEquals(1, newsRepository.findAll().size()),
            () -> assertNotNull(newsRepository.findByNewsCode(news.getNewsCode()))
        );
    }

    @Test
    public void givenNothing_whenSave2News_thenFindListWith2ElementsAndFindAllByPublishedAt() {
        news.setId(2L);
        news.setNewsCode("code12");
        newsRepository.save(news);
        news.setId(3L);
        news.setNewsCode("code13");
        news.setPublishedAt(LocalDateTime.of(2019,5,5, 0, 0, 0));
        newsRepository.save(news);

        assertAll(
            () -> assertEquals(2, newsRepository.findAll().size()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, newsRepository.findAllByOrderByPublishedAtDesc(PageRequest.of(0, 1)).get(0).getPublishedAt())
        );
    }

}
