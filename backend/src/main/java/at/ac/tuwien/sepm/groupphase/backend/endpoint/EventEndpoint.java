package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
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

    @CrossOrigin(maxAge = 3600)
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



    @CrossOrigin(maxAge = 3600)
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
        DetailedEventDto result = eventMapper.eventToDetailedEventDto(eventService.findByEventCode(eventCode));
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600)
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
    public ResponseEntity<List<SimpleEventDto>> findAllEvents() {
        LOGGER.info("GET /api/v1/events/all");
        List<SimpleEventDto> result = eventMapper.eventToSimpleEventDto(eventService.findAllEvents());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{eventCode}")
    @ApiOperation(
        value = "Get event by its Code",
        notes = "Get event by its Code")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<Void> deleteByEventCode(@PathVariable String eventCode) {
        LOGGER.info("DELETE /api/v1/events/" + eventCode);
        eventService.deletebyEventCode(eventCode);
        return ResponseEntity.noContent().build();
    }

}
