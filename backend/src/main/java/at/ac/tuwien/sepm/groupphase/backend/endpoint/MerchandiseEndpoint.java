package at.ac.tuwien.sepm.groupphase.backend.endpoint;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.MerchandiseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.MerchandiseMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.service.MerchandiseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/merchandise")
public class MerchandiseEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final MerchandiseService merchandiseService;
    private final MerchandiseMapper merchandiseMapper;

    public MerchandiseEndpoint(MerchandiseService merchandiseService, MerchandiseMapper merchandiseMapper) {
        this.merchandiseService = merchandiseService;
        this.merchandiseMapper = merchandiseMapper;
    }


    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/all")
    @ApiOperation(
        value = "Get all merchandise products",
        notes = "Get all merchandise products",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Merchandise products are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Merchandise product is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),}
        )
    public ResponseEntity<List<MerchandiseDto>> findAllMerchandiseProducts() {
        LOGGER.info("GET /api/v1/merchandise/all");
        List<MerchandiseDto> result = merchandiseMapper.merchandiseToMerchandiseDto(merchandiseService.findAllMerchandiseProducts());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

  /*  @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/all")
    @ApiOperation(
        value = "Get all merchandise premium products",
        notes = "Get all merchandise premium products",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Merchandise premium products are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Merchandise premium product is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),}
    )
    public ResponseEntity<List<MerchandiseDto>> findAllPremiumMerchandiseProducts() {
        LOGGER.info("GET /api/v1/merchandise/premium");
        List<MerchandiseDto> result = merchandiseMapper.merchandiseToMerchandiseDto(merchandiseService.findAllMerchandiseProductsPremium());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }*/




}
