package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventTypeEnum;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/events")
public class EventEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired
    public EventEndpoint(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/top10")
    @ApiOperation(
        value = "Get Top 10 events",
        notes = "Get Top 10 events without details")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleEventDto>> findTop10EventsOfMonth() {
        LOGGER.info("GET /api/v1/events/top10");
        List<SimpleEventDto> result = eventMapper.eventToSimpleEventDto(eventService.findTop10EventsOfMonth());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/top10/{category}")
    @ApiOperation(
        value = "Get Top 10 events by category",
        notes = "Get Top 10 events without details by category")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleEventDto>> findTop10EventsOfMonthByCategory(@PathVariable String category) {
        LOGGER.info("GET /api/v1/events/top10/" + category);
        List<SimpleEventDto> result = eventMapper.eventToSimpleEventDto(eventService.findTop10EventsOfMonthByCategory(category));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ApiOperation(
        value = "Publish new Event",
        notes = "Publish new Event in system",
        authorizations = {@Authorization(value = "apiKey")})
    public DetailedEventDto create(@Valid @RequestBody DetailedEventDto eventDto) {
        LOGGER.info("POST /api/v1/events body: {}", eventDto);
        return eventMapper.eventToDetailedEventDto(
            eventService.createNewEvent(eventMapper.detailedEventDtoToEvent(eventDto)));
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/eventCategories")
    @ApiOperation(
        value = "Get all event categories",
        notes = "Get all event categories",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Event categories are successfully retrieved")
    })
    public ResponseEntity<List<EventCategoryEnum>> findAllEventCategories() {
        return new ResponseEntity<>(Arrays.asList(EventCategoryEnum.values()), HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/eventTypes")
    @ApiOperation(
        value = "Get all event types",
        notes = "Get all event types",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Event types are successfully retrieved")
    })
    public ResponseEntity<List<EventTypeEnum>> findAllEventTypes() {
        return new ResponseEntity<>(Arrays.asList(EventTypeEnum.values()), HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{eventCode}")
    @ApiOperation(
        value = "Get event by its Code",
        notes = "Get event by its Code")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<DetailedEventDto> findByEventCode(@PathVariable String eventCode) {
        LOGGER.info("GET /api/v1/events/" + eventCode);
        LocalDateTime start = LocalDateTime.now();
        DetailedEventDto result = eventMapper.eventToDetailedEventDto(eventService.findByEventCode(eventCode));
        LocalDateTime end = LocalDateTime.now();
        float runningTime = Duration.between(start, end).toMillis();
        LOGGER.info("Getting Event took " + runningTime/1000.0 + " seconds");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/all")
    @ApiOperation(
        value = "Get all events",
        notes = "Get all events without details",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleEventDto>> findAllEvents(@RequestParam int size) {
        LOGGER.info("GET /api/v1/events/all");
        List<SimpleEventDto> result = eventMapper.eventToSimpleEventDto(eventService.findAllEvents(size));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{eventCode}")
    @ApiOperation(
        value = "Delete event by its Code",
        notes = "Delete event by its Code")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Event is successfully deleted"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<Void> deleteByEventCode(@PathVariable String eventCode) {
        LOGGER.info("DELETE /api/v1/events/" + eventCode);
        eventService.deletebyEventCode(eventCode);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "", params = {"artistId", "size"})
    @ApiOperation(
        value = "Find Events",
        notes = "Find Events by artist id",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleEventDto>> findEventByArtistId(@Valid @RequestParam Long artistId,
                                                                    @Valid @RequestParam int size) {
        LOGGER.info("GET /api/v1/events?artistId=" + artistId);
        List<SimpleEventDto> result = eventMapper.eventToSimpleEventDto(eventService.findEventsByArtistId(artistId, size));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "", params = {"name", "size"})
    @ApiOperation(
        value = "Get all events by name",
        notes = "Get all events by with details",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleEventDto>> findEventByName(@Valid @RequestParam String name,
                                                                @Valid @RequestParam int size) {
        LOGGER.info("GET /api/v1/events?name=" + name);
        List<SimpleEventDto> result = eventMapper.eventToSimpleEventDto(eventService.findEventsByName(name, size));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "", params = {"eventName", "type", "category", "startsAt", "endsAt", "showDuration", "size"})
    @ApiOperation(
        value = "Get all events advanced",
        notes = "Get all events by with details",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Events are found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleEventDto>> findEventsAdvanced(@Valid @RequestParam String eventName,
                                                                  @Valid @RequestParam String type,
                                                                  @Valid @RequestParam String category,
                                                                  @Valid @RequestParam String startsAt,
                                                                  @Valid @RequestParam String endsAt,
                                                                  @Valid @RequestParam String showDuration,
                                                                   @Valid  @RequestParam int size
    ) {
        LOGGER.info("GET /api/v1/events?eventName=" + eventName + "&type=" + type + "&category=" + category + "&startsAt=" + startsAt + "&endsAt=" + endsAt + "&showDuration=" + showDuration
        + "&size=" + size);
        LocalDateTime startsAtParsed = null;
        if (!startsAt.isEmpty()) {
            startsAtParsed = LocalDateTime.parse(startsAt);
        }

        LocalDateTime endsAtParsed = null;
        if (!endsAt.isEmpty()) {
            endsAtParsed = LocalDateTime.parse(endsAt);
        }

        Integer eventTypeEnumOrdinal = null;
        if (!type.isEmpty()) {
            eventTypeEnumOrdinal = EventTypeEnum.valueOf(type).ordinal();
        }

        Integer eventCategoryEnumOrdinal = null;
        if (!category.isEmpty()) {
            eventCategoryEnumOrdinal = EventCategoryEnum.valueOf(category).ordinal();
        }

        Duration showDurationParsed = null;
        if (!showDuration.isEmpty()) {
            showDurationParsed = Duration.parse(showDuration);
        }
        List<SimpleEventDto> result = eventMapper.eventToSimpleEventDto(eventService.findEventsAdvanced(eventName, eventTypeEnumOrdinal, eventCategoryEnumOrdinal, startsAtParsed, endsAtParsed, showDurationParsed, size));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "", params = {"eventCode", "name", "startRange", "endRange", "size"})
    @ApiOperation(
        value = "Get simple events",
        notes = "Get simple events by params",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleEventDto>> findSimpleEventsByParam(@RequestParam String eventCode,
                                                                        @RequestParam String name,
                                                                        @RequestParam String startRange,
                                                                        @RequestParam String endRange,
                                                                        @RequestParam int size
    ) {
        LOGGER.info("GET /api/v1/events?eventCode=" + eventCode + "&name=" + name +  "&startRange=" + startRange + "&endRange=" + endRange
            + "&size=" + size);
        LocalDateTime startRangeDate = LocalDate.parse(startRange, DateTimeFormatter.ofPattern("E MMM dd yyyy")).atStartOfDay();
        LocalDateTime endRangeDate = LocalDate.parse(endRange, DateTimeFormatter.ofPattern("E MMM dd yyyy")).atStartOfDay();
        List<SimpleEventDto> result = eventMapper.eventToSimpleEventDto(eventService.findSimpleEventsByParam(eventCode, name, startRangeDate, endRangeDate, size));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



}
