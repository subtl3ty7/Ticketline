package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "invoice")
@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "INVOICE_CATEGORY", discriminatorType= DiscriminatorType.STRING)
public abstract class AbstractInvoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(nullable = false, name = "user_code", length = 6)
    private String userCode;

    @NotNull
    @Column(nullable = false,  name = "generated_at")
    private LocalDateTime generatedAt;

    @Column(name = "payment_method")
    private String payment_method;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(nullable = false, name = "invoice_number", length = 6)
    private String invoice_number;

    public AbstractInvoice () {}
}
