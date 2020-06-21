package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.hibernate.FetchMode;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "artist")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Transactional
@EqualsAndHashCode
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "artists", fetch = FetchType.LAZY)
    private List<Event> performsAt;

    @NotNull
    @Size(min=1, max=100)
    @Column(nullable = false, length = 100, name = "first_name")
    private String firstName;

    @NotNull
    @Size(max=100)
    @Column(length = 100, name = "last_name")
    private String lastName;
}
