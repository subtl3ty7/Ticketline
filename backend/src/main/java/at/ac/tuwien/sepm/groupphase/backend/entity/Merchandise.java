package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "merchandise")
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Merchandise {

    public Merchandise() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(nullable = false, length = 6, name = "merchandise_code", unique = true)
    private String merchandiseProductCode;

    @NotNull
    @Column(nullable = false, name = "product_name")
    private String merchandiseProductName;

   /* @ToString.Exclude
    @NotNull
    @Lob
    @Column(nullable = false, name = "photo")
    private String photo;
*/

    @NotNull
    @Column(nullable = false, name = "stock_count")
    private int stockCount;

    @NotNull
    @Column(nullable = false, name = "price")
    private int price;


    @NotNull
    @Column(nullable = false, name = "premium_price")
    private int premiumPrice;

    @NotNull
    @Column(nullable = false, name = "premium")
    private boolean premium;

    @NotNull
    @Column(nullable = false, name = "purchased")
    private boolean purchased;



}
