package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/news")
public class NewsEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsEndpoint(NewsService newsService, NewsMapper newsMapper) {
        this.newsService = newsService;
        this.newsMapper = newsMapper;
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ApiOperation(
        value = "Publish new News Entry",
        notes = "Publish new News Entry",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Successfully created News entry")
    public NewsDto create(@Valid @RequestBody NewsDto newsDto) {
        LOGGER.info("POST /api/v1/news body: {}", newsDto);

        return newsMapper.newsToNewsDto(
            newsService.createNewNewsEntry(newsMapper.newsDtoToNews(newsDto))
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/{newsCode}")
    @ApiOperation(
        value = "Get News Entry by newsCode",
        notes = "Get News Entry by newsCode",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Successfully retrieved News Entry")
    public NewsDto get(@PathVariable String newsCode) {
        LOGGER.info("GET /api/v1/news/" + newsCode);

        return newsMapper.newsToNewsDto(
            newsService.findByNewsCode(newsCode)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/latest-unseen")
    @ApiOperation(
        value = "Get the latest 6 News Entries for a Customer",
        notes = "This only returns News Entries the Customer has not seen yet",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Successfully retrieved News Entries")
    public List<NewsDto> getLatest6Unseen(Authentication auth) {
        LOGGER.info("GET /api/v1/news/latest6Unseen/" + auth);
        return newsMapper.newsToNewsDto(
            newsService.findLatestUnseen(auth)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/seen")
    @ApiOperation(
        value = "Get all News Entries that this customer has already seen",
        notes = "Get all News Entries that this customer has already seen",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Successfully retrieved News Entries")
    public List<NewsDto> getSeen(Authentication auth) {
        LOGGER.info("GET /api/v1/news/latest6/" + auth);

        return newsMapper.newsToNewsDto(
            newsService.findSeenNews(auth)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/latest")
    @ApiOperation(
        value = "Get the latest 6 News Entries",
        notes = "Get the latest 6 News Entries")
    @ApiResponse(code = 200, message = "Successfully retrieved News Entries")
    public List<NewsDto> getLatest6() {
        LOGGER.info("GET /api/v1/news/latest6/");

        return newsMapper.newsToNewsDto(
            newsService.findLatest()
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/all")
    @ApiOperation(
        value = "Get all News Entries",
        notes = "Get all News Entries",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Successfully retrieved News Entries")
    public List<NewsDto> getAll() {
        LOGGER.info("GET /api/v1/news/all");

        return newsMapper.newsToNewsDto(
            newsService.findAllNews()
        );
    }

}
