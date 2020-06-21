package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=6, max=6)
    @Column(nullable = false, length = 6, unique = true)
    private String newsCode;

    @NotNull
    @Size(min=1, max=1000)
    @Column(nullable = false, length = 1000)
    private String title;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime publishedAt;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime stopsBeingRelevantAt;

    @NotNull
    @Size(min=1, max=10000)
    @Column(nullable = false, length = 10000)
    private String summary;

    @ToString.Exclude
    @NotNull
    @Size(min=1, max=100000)
    @Column(nullable = false, length = 100000)
    private String text;

    @NotNull
    @Size(min=1, max=100)
    @Column(nullable = false, length = 100)
    private String author;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Customer> seenBy;

    @ToString.Exclude
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Image photo;
}