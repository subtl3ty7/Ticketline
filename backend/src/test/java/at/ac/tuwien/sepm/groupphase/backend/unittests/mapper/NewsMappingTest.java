package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class NewsMappingTest implements TestData {

    private final News news = News.builder()
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
    private NewsMapper newsMapper;

    @Test
    public void shouldMapNewsToNewsDto() {
        NewsDto newsDto = newsMapper.newsToNewsDto(news);
        assertAll(
            () -> assertEquals(USER_CODE, newsDto.getNewsCode()),
            () -> assertEquals(TEST_NEWS_TITLE, newsDto.getTitle()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, newsDto.getPublishedAt()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, newsDto.getStopsBeingRelevantAt()),
            () -> assertEquals(TEST_NEWS_SUMMARY, newsDto.getSummary()),
            () -> assertEquals(TEST_NEWS_TEXT, newsDto.getText()),
            () -> assertEquals(FNAME, newsDto.getAuthor())
        );
    }

    @Test
    public void shouldMapNewsListToNewsDtoList() {
        List<News> newsList = new ArrayList<>();
        newsList.add(news);
        newsList.add(news);
        List<NewsDto> newsDtos = newsMapper.newsToNewsDto(newsList);
        NewsDto newsDto = newsDtos.get(0);
        assertAll(
            () -> assertEquals(USER_CODE, newsDto.getNewsCode()),
            () -> assertEquals(TEST_NEWS_TITLE, newsDto.getTitle()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, newsDto.getPublishedAt()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, newsDto.getStopsBeingRelevantAt()),
            () -> assertEquals(TEST_NEWS_SUMMARY, newsDto.getSummary()),
            () -> assertEquals(TEST_NEWS_TEXT, newsDto.getText()),
            () -> assertEquals(FNAME, newsDto.getAuthor())
        );
    }

    @Test
    public void shouldMapNewsDtoToNews() {
        NewsDto newsDto = newsMapper.newsToNewsDto(news);
        News news1 = newsMapper.newsDtoToNews(newsDto);
        assertAll(
            () -> assertEquals(USER_CODE, news1.getNewsCode()),
            () -> assertEquals(TEST_NEWS_TITLE, news1.getTitle()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, news1.getPublishedAt()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, news1.getStopsBeingRelevantAt()),
            () -> assertEquals(TEST_NEWS_SUMMARY, news1.getSummary()),
            () -> assertEquals(TEST_NEWS_TEXT, news1.getText()),
            () -> assertEquals(FNAME, news1.getAuthor())
        );
    }

}
