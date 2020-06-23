package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsServiceTest implements TestData {

    @Autowired
    private NewsService newsService;

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

    @Order(1)
    @Test
    public void givenNews_whenCreateNewsWithSameCode_thenValidationException() {
        News news1 = newsService.createNewNewsEntry(news);
        assertAll(
            () -> assertEquals(TEST_NEWS_TITLE, news1.getTitle()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, news1.getStopsBeingRelevantAt()),
            () -> assertEquals(TEST_NEWS_SUMMARY, news1.getSummary()),
            () -> assertEquals(TEST_NEWS_TEXT, news1.getText()),
            () -> assertEquals(FNAME, news1.getAuthor())
        );
    }

    @Order(2)
    @Test
    public void givenNews_whenGetAll_thenListWith1NewsElementWithProperties() {

        List<News> news = newsService.findAll(0);
        assertEquals(1, news.size());
        News news1 = news.get(0);
        assertAll(
            () -> assertEquals(TEST_NEWS_TITLE, news1.getTitle()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, news1.getStopsBeingRelevantAt()),
            () -> assertEquals(TEST_NEWS_SUMMARY, news1.getSummary()),
            () -> assertEquals(TEST_NEWS_TEXT, news1.getText()),
            () -> assertEquals(FNAME, news1.getAuthor())
        );
    }

    @Order(3)
    @Test
    public void givenNews_whenGetAllWithLimit_thenListWith1NewsElement() {

        List<News> news = newsService.findLatest(null, null);
        assertEquals(1, news.size());
    }

    @Order(4)
    @Test
    public void givenNews_whenGetByWrongParams_thenListWith0NewsElements() {

        List<News> news = newsService.findSimpleNewsByParam(USER_CODE, TEST_NEWS_TITLE, FNAME, TEST_NEWS_PUBLISHED_AT, TEST_NEWS_PUBLISHED_AT, 0);
        assertEquals(0, news.size());
    }

}
