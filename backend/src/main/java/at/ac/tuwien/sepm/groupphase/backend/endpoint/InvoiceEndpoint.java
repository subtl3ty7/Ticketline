package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/invoices")
public class InvoiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;
    private final TicketMapper ticketMapper;

    @Autowired
    public InvoiceEndpoint(InvoiceService invoiceService, InvoiceMapper invoiceMapper, TicketMapper ticketMapper) {
        this.invoiceService = invoiceService;
        this.invoiceMapper = invoiceMapper;
        this.ticketMapper = ticketMapper;
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{userCode}")
    @ApiOperation(
        value = "Get all invoices of one user",
        notes = "Get all invoices of one user",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Invoices are successfully retrieved")
    public ResponseEntity<InvoiceDto> getAllInvoicesOneUser(@PathVariable String userCode) {
        LOGGER.info("GET /api/v1/invoices/" + userCode);

        List<Invoice> invoices = invoiceService.allInvoicesOfUser(userCode);
        return new ResponseEntity(invoiceMapper.invoiceListToInvoiceDtoList(invoices), HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "")
    @ApiOperation(
        value = "Get invoice by given ticket",
        notes = "Get invoice by given ticket",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponse(code = 200, message = "Invoice is successfully retrieved")
    public ResponseEntity<InvoiceDto> getInvoiceByTicket(DetailedTicketDto searchTicketDto) {
        LOGGER.info("GET /api/v1/invoices/byTicket" + searchTicketDto);

        Invoice invoice = invoiceService.findInvoiceByTicket(ticketMapper.detailedTicketDtoToTicket(searchTicketDto));
        return new ResponseEntity(invoiceMapper.invoiceToInvoiceDto(invoice), HttpStatus.OK);
    }
}
