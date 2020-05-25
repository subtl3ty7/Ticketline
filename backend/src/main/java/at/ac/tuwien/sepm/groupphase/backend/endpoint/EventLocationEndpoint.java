package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventLocationMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
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

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/eventLocations")
public class EventLocationEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventLocationService eventLocationService;
    private final EventLocationMapper eventLocationMapper;
    private final EventMapper eventMapper;

    @Autowired
    public EventLocationEndpoint(EventLocationService eventLocationService, EventLocationMapper eventLocationMapper, EventMapper eventMapper) {
        this.eventLocationService = eventLocationService;
        this.eventLocationMapper = eventLocationMapper;
        this.eventMapper = eventMapper;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/all")
    @ApiOperation(
        value = "All Event Locations",
        notes = "Get all available Event Locations (simplified)",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "EventLocations are successfully retrieved"),
        @ApiResponse(code = 404, message = "No EventLocation is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleEventDto>> requestAllEventLocations() {
        LOGGER.info("GET /api/v1/eventLocations/all");
        List<EventLocationDto> result = eventLocationMapper.eventLocationOriginalToEventLocationDto(eventLocationService.getAllEventLocations());
        return new ResponseEntity(result, HttpStatus.OK);
    }



    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "")
    @ApiOperation(
        value = "All Event Locations with given Name",
        notes = "Get all Event Locations with the given Name",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Event Locations are successfully retrieved"),
        @ApiResponse(code = 404, message = "No EventLocation is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<EventLocationDto>> findEventLocationsByName(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "street", required = false) String street,
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "country", required = false) String country,
        @RequestParam(value = "plz", required = false) String plz) {
        LOGGER.info("GET /api/v1/eventLocations?locationName=" + name + "&description=" + description + "&street=" + street + "&city=" + city + "&country=" + country + "&plz=" + plz);
        List<EventLocationDto> result = eventLocationMapper.eventLocationOriginalToEventLocationDto(eventLocationService.findAllFilteredEventLocations(name, city, street, country, plz, description));
        return new ResponseEntity(result, HttpStatus.OK);
    }


}
