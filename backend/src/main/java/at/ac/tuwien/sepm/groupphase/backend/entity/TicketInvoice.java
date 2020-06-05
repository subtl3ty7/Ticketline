package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@ToString
@DiscriminatorValue("TICKET_INVOICE")
public class TicketInvoice extends AbstractInvoice {

    @ToString.Exclude
    @OneToMany
    private List<Ticket> tickets;

    @NotNull
    @Column(nullable = false,  name = "invoice_type")
    private String invoice_type;

    public TicketInvoice () {}

}
