package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ShowMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventCategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventTypeEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.ShowService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/shows")
public class ShowEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ShowService showService;
    private final ShowMapper showMapper;
    private final EventMapper eventMapper;
    private final EventService eventService;

    public ShowEndpoint(ShowService showService, ShowMapper showMapper, EventMapper eventMapper, EventService eventService) {
        this.showService = showService;
        this.showMapper = showMapper;
        this.eventMapper = eventMapper;
        this.eventService = eventService;
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "", params = "eventLocationId")
    @ApiOperation(
        value = "Get Shows by event location name"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Shows are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Show is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<ShowDto>> findShowsByEventLocationName(@RequestParam Long eventLocationId) {
        LOGGER.info("GET /api/v1/shows?eventLocationId=" + eventLocationId);
        List<Show> showList = showService.getShowsByEventLocationId(eventLocationId);
        List<ShowDto> shows = showMapper.showToShowDto(showList);
        for(ShowDto show: shows) {
            show.setEvent(eventMapper.eventToSimpleEventDto(eventService.findByEventCode(show.getEventCode())));
        }
        return new ResponseEntity<>(shows, HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "", params = {"eventName", "type", "category", "startsAt", "endsAt", "duration", "price"})
    @ApiOperation(
        value = "Get all events by name",
        notes = "Get all events by with details",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Events are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Event is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<ShowDto>> findShowsAdvanced(@Valid @RequestParam String eventName,
                                                                   @Valid @RequestParam String type,
                                                                   @Valid @RequestParam String category,
                                                                   @Valid @RequestParam String startsAt,
                                                                   @Valid @RequestParam String endsAt,
                                                                   @Valid @RequestParam String duration,
                                                                   @Valid @RequestParam String price
    ) {
        LOGGER.info("GET /api/v1/shows?eventName=" + eventName + "&type=" + type + "&category=" + category + "&startsAt=" + startsAt + "&endsAt=" + endsAt + "&duration=" + duration + "&price=" + price);
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

        Duration durationParsed = null;
        if (!duration.isEmpty()) {
            durationParsed = Duration.parse(duration);
        }

        Integer priceInteger = null;
        if (!price.isEmpty()) {
            priceInteger = Integer.parseInt(price);
        }

        List<ShowDto> shows = showMapper.showToShowDto(showService.findShowsAdvanced(eventName, eventTypeEnumOrdinal, eventCategoryEnumOrdinal, startsAtParsed, endsAtParsed, durationParsed, priceInteger));

        return new ResponseEntity<>(shows, HttpStatus.OK);
    }
}
