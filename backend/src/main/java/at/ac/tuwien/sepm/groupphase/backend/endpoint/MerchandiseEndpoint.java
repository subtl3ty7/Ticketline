package at.ac.tuwien.sepm.groupphase.backend.endpoint;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.MerchandiseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.MerchandiseMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.service.MerchandiseService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.event.WindowFocusListener;
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
        notes = "Get all merchandise products"
    )
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

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/premium")
    @ApiOperation(
        value = "Get all merchandise premium products",
        notes = "Get all merchandise premium products"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Merchandise premium products are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Merchandise premium product is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),}
    )
    public ResponseEntity<List<MerchandiseDto>> findAllMerchandisePremiumProducts() {
        LOGGER.info("GET /api/v1/merchandise/premium");
        List<MerchandiseDto> result = merchandiseMapper.merchandiseToMerchandiseDto(merchandiseService.findAllMerchandisePremiumProducts());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/purchasingWithPremiumPoints/{userCode}")
    @ApiOperation(
        value = "Purchasing a merchandise product with premium points.",
        notes = "Purchasing a merchandise product with premium points.",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 201, message = "Product is successfully purchased")
    public MerchandiseDto buyMerchandiseWithPremiumPoints(@RequestBody MerchandiseDto merchandiseDto, @PathVariable String userCode) {
        LOGGER.info("POST " + merchandiseDto);
        Merchandise merchandise = merchandiseService.purchaseWithPremiumPoints(merchandiseMapper.merchandiseDtoToMerchandise(merchandiseDto), userCode);
        return merchandiseMapper.merchandiseToMerchandiseDto(merchandise);
    }

    @PostMapping(value = "/purchasingWithMoney/{userCode}")
    @ApiOperation(
        value = "Purchasing a merchandise product with money.",
        notes = "Purchasing a merchandise product with money.",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 201, message = "Product is successfully purchased")
    public MerchandiseDto buyMerchandiseWithMoney(@RequestBody MerchandiseDto merchandiseDto, @PathVariable String userCode) {
        LOGGER.info("POST " + merchandiseDto);
        Merchandise merchandise = merchandiseService.purchaseWithMoney(merchandiseMapper.merchandiseDtoToMerchandise(merchandiseDto), userCode);
        return merchandiseMapper.merchandiseToMerchandiseDto(merchandise);
    }



    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{merchandiseProductCode}")
    @ApiOperation(
        value = "Find Merchandise Product by its code",
        notes = "Find Merchandise Product by its code"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Merchandise product successfully retrieved"),
        @ApiResponse(code = 404, message = "No Merchandise Product found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<MerchandiseDto> findMerchandiseByMerchandiseProductCode(@PathVariable String merchandiseProductCode) {
        LOGGER.info("GET /api/v1/merchandise/" + merchandiseProductCode);
        MerchandiseDto result = merchandiseMapper.merchandiseToMerchandiseDto(merchandiseService.findMerchandiseByMerchandiseProductCode(merchandiseProductCode));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



}
