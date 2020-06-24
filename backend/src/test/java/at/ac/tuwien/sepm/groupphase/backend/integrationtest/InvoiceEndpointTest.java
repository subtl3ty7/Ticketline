package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class InvoiceEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private Invoice invoice = Invoice.builder()
        .id(ID)
        .invoice_type(TYP_I)
        .userCode(USER_CODE)
        .payment_method(PAY)
        .generatedAt(GENERATE)
        .invoiceNumber(NUM)
        .invoice_category(CAT_I)
        .build();

    AbstractUser abstractUser = Customer.CustomerBuilder.aCustomer()
        .withId(ID)
        .withUserCode(USER_CODE)
        .withFirstName(FNAME)
        .withLastName(LNAME)
        .withEmail(DEFAULT_USER)
        .withPassword(PASS)
        .withBirthday(BIRTHDAY)
        .withCreatedAt(CRE)
        .withUpdatedAt(UPD)
        .withIsBlocked(false)
        .withIsLogged(false)
        .withPoints(POINTS)
        .build();

    @BeforeEach
    public void beforeEach() {
        invoiceRepository.deleteAll();
    }

    @Test
    public void givenInvoice_whenGetInvoicesByUserCode_then200AndInvoiceListWith1ElementWithProperties() throws Exception {
        userRepository.save(abstractUser);
        invoiceRepository.save(invoice);

        MvcResult mvcResult = this.mockMvc.perform(get(INVOICE_BASE_URI + "/" + invoice.getUserCode())
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        List<InvoiceDto> invoiceDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            InvoiceDto[].class));
        assertEquals(1, invoiceDtos.size());
        InvoiceDto invoiceDto = invoiceDtos.get(0);
        assertAll(
            () -> assertEquals(USER_CODE, invoiceDto.getUserCode()),
            () -> assertEquals(PAY, invoiceDto.getPayment_method()),
            () -> assertEquals(GENERATE, invoiceDto.getGeneratedAt()),
            () -> assertEquals(NUM, invoiceDto.getInvoiceNumber()),
            () -> assertEquals(TYP_I, invoiceDto.getInvoice_type())
        );
    }
}
