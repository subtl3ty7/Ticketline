package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractInvoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(nullable = false, name = "user_code", length = 6)
    private String userCode;

    @NotNull
    @Column(nullable = false,  name = "purchased_at")
    private LocalDateTime purchased_at;

    @NotNull
    @Column(nullable = false,  name = "payment_method")
    private String payment_method;

    @NotNull
    @Column(nullable = false,  name = "receipt_number")
    private Integer receipt_number;

    public AbstractInvoice () {}
}
