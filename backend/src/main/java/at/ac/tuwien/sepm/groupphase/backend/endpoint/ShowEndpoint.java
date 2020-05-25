package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ShowMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.ShowService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
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

@RestController
@RequestMapping(value = "/api/v1/shows")
public class ShowEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ShowService showService;
    private final ShowMapper showMapper;

    @Autowired
    public ShowEndpoint(ShowService showService, ShowMapper showMapper){
        this.showMapper = showMapper;
        this.showService = showService;
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{showId}")
    @ApiOperation(
        value = "Get show by its Id",
        notes = "Get show by its Id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Show is successfully retrieved"),
        @ApiResponse(code = 404, message = "No show is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<ShowDto> findByShowId(@PathVariable Long showId) {
        LOGGER.info("GET /api/v1/shows/" + showId);
        ShowDto result = showMapper.showToShowDto(showService.findShowByShowId(showId));
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
