package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "artist")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Transactional
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "artists")
    private List<Event> performsAt;

    @NotNull
    @Size(min=1, max=100)
    @Column(nullable = false, length = 100, name = "first_name")
    private String firstName;

    @NotNull
    @Size(min=1, max=100)
    @Column(nullable = false, length = 100, name = "last_name")
    private String lastName;

    public static final class ArtistBuilder {
        private Long id;
        private String firstName;
        private String lastName;

        public ArtistBuilder() {
        }

        public static Artist.ArtistBuilder anArtist() {
            return new Artist.ArtistBuilder();
        }

        public Artist.ArtistBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Artist.ArtistBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
    }

    public Artist build() {
        Artist artist = new Artist();
        artist.setId(id);
        artist.setFirstName(firstName);
        artist.setLastName(lastName);
        return artist;
    }
}
