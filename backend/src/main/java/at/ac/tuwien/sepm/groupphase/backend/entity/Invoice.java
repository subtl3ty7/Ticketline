package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticketCode")
    private List<Ticket> tickets;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(nullable = false, name = "user_code", length = 6)
    private String userCode;

    private String merchandise_code;

    @NotNull
    @Column(nullable = false,  name = "purchased_at")
    private LocalDateTime purchased_at;

    @NotNull
    @Column(nullable = false,  name = "payment_method")
    private String payment_method;

    @NotNull
    @Column(nullable = false,  name = "invoice_type")
    private String invoice_type;

    @NotNull
    @Column(nullable = false,  name = "receipt_number")
    private Integer receipt_number;


}
