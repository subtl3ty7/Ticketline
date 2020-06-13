package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
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
@RequestMapping(value = "/api/v1/invoices")
public class InvoiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;

    @Autowired
    public InvoiceEndpoint(InvoiceService invoiceService, InvoiceMapper invoiceMapper) {
        this.invoiceService = invoiceService;
        this.invoiceMapper = invoiceMapper;
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{userCode}")
    @ApiOperation(
        value = "Get all invoices of one user",
        notes = "Get all invoices of one user")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Invoices are successfully retrieved"),
        @ApiResponse(code = 404, message = "No invoice is found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<InvoiceDto> getAllInvoicesOneUser(@PathVariable String userCode) {
        LOGGER.info("GET /api/v1/invoices/" + userCode);

        List<Invoice> invoices = invoiceService.allInvoicesOfUser(userCode);
        return new ResponseEntity(invoiceMapper.invoiceListToInvoiceDtoList(invoices), HttpStatus.OK);
    }
}
