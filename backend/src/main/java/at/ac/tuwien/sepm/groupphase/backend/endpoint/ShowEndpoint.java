package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ShowMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.ShowService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/shows")
public class ShowEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ShowService showService;
    private final ShowMapper showMapper;

    @Autowired
    public ShowEndpoint(ShowService showService, ShowMapper showMapper) {
        this.showService = showService;
        this.showMapper = showMapper;
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
        List<ShowDto> result = showMapper.showToShowDto(showService.getShowsByEventLocationId(eventLocationId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
