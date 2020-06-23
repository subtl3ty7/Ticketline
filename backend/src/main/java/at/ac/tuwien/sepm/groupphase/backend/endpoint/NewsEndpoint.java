package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{newsCode}")
    @ApiOperation(
        value = "Get News Entry by newsCode",
        notes = "Get News Entry by newsCode",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Successfully retrieved News Entry")
    public NewsDto get(@PathVariable String newsCode, Authentication auth) {
        LOGGER.info("GET /api/v1/news/" + newsCode);

        return newsMapper.newsToNewsDto(
            newsService.findByNewsCode(newsCode, auth)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/unseen")
    @ApiOperation(
        value = "Get the latest News Entries for a Customer",
        notes = "This only returns News Entries the Customer has not seen yet",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Successfully retrieved News Entries")
    public List<NewsDto> getLatestUnseen(Integer limit, Authentication auth) {
        LOGGER.info("GET /api/v1/news/latest-unseen?limit=" + limit);
        return newsMapper.newsToNewsDto(
            newsService.findLatestUnseen(auth, limit)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/seen")
    @ApiOperation(
        value = "Get all News Entries that this customer has already seen",
        notes = "Get all News Entries that this customer has already seen",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Successfully retrieved News Entries")
    public List<NewsDto> getSeen(Integer page, Integer size, Authentication auth ) {
        LOGGER.info("GET /api/v1/news/seen?page=" + page + "&size=" + size);

        return newsMapper.newsToNewsDto(
            newsService.findSeenNews(page, size, auth)
        );
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/latest")
    @ApiOperation(
        value = "Get the latest News Entries",
        notes = "Get the latest News Entries")
    @ApiResponse(code = 200, message = "Successfully retrieved News Entries")
    public List<NewsDto> getLatest(Integer page, Integer size) {
        LOGGER.info("GET /api/v1/news/latest?page=" + page + "&size=" + size);

        return newsMapper.newsToNewsDto(
            newsService.findLatest(page, size)
        );
    }
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all")
    @ApiOperation(
        value = "Get all Simple News Entries",
        notes = "Get all Simple News Entries")
    @ApiResponse(code = 200, message = "Successfully retrieved News Entries")
    public List<SimpleNewsDto> getAllSimpleNews(@RequestParam int size) {
        LOGGER.info("GET /api/v1/news/?size=" + size);

        return newsMapper.newsToSimpleNewsDto(
            newsService.findAll(size)
        );
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "", params = {"newsCode", "title", "author", "startRange", "endRange", "size"})
    @ApiOperation(
        value = "Get simple news",
        notes = "Get simple news by params",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "News are successfully retrieved"),
        @ApiResponse(code = 404, message = "No News is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleNewsDto>> findSimpleNewsByParam(@RequestParam String newsCode,
                                                                     @RequestParam String title,
                                                                     @RequestParam String author,
                                                                     @RequestParam String startRange,
                                                                     @RequestParam String endRange,
                                                                     @RequestParam int size
    ) {
        LOGGER.info("GET /api/v1/news?newsCode=" + newsCode + "&title=" + title + "&author=" + author +  "&startRange=" + startRange + "&endRange=" + endRange + "&size=" + size);
        LocalDateTime startRangeDate = LocalDate.parse(startRange, DateTimeFormatter.ofPattern("E MMM dd yyyy")).atStartOfDay();
        LocalDateTime endRangeDate = LocalDate.parse(endRange, DateTimeFormatter.ofPattern("E MMM dd yyyy")).atStartOfDay();
        List<SimpleNewsDto> result = newsMapper.newsToSimpleNewsDto(newsService.findSimpleNewsByParam(newsCode, title, author, startRangeDate, endRangeDate, size));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
