package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InvoiceDto {

    private Long id;
    private List<Ticket> tickets;
    private String userCode;
    private LocalDateTime generated_at;
    private String payment_method;
    private String invoice_type;
    private String invoice_number;
}
