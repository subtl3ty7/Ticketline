package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
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
@RequestMapping(value = "/api/v1/artists")
public class ArtistEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    @Autowired
    public ArtistEndpoint(ArtistService artistService, ArtistMapper artistMapper) {
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "", params = {"firstName", "lastName", "size"})
    @ApiOperation(
        value = "Get artists by first and last name"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Artists are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Artist is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<ArtistDto>> findArtistsByFirstAndLastname(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int size) {
        LOGGER.info("GET /api/v1/artist with firstname '"+ firstName + "' and lastname '" + lastName + "'");
        List<ArtistDto> result = artistMapper.artistsToArtistDtos(artistService.findArtistsByFirstAndLastName(firstName, lastName, size));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
