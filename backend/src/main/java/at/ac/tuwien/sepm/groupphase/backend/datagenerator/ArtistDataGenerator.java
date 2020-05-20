package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class ArtistDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_ARTISTS_TO_GENERATE = 5;
    private static final String TEST_ARTISTS_FIRSTNAME = "Chance";
    private static final String TEST_ARTISTS_LASTNAME = "Bennett";
    private final ArtistRepository artistRepository;

    public ArtistDataGenerator(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @PostConstruct
    private void generateArtist() {
        if (artistRepository.findAllByOrderByLastNameAscFirstNameAsc().size() > 0) {
            LOGGER.debug("artist already generated");
        } else {
            LOGGER.debug("generating {} message entries", NUMBER_OF_ARTISTS_TO_GENERATE);
            for (int i = 0; i < NUMBER_OF_ARTISTS_TO_GENERATE; i++) {
                Artist artist = Artist.ArtistBuilder.anArtist()
                    .withFirstName(TEST_ARTISTS_FIRSTNAME + i)
                    .withLastName(TEST_ARTISTS_LASTNAME + i)
                    .build();
                LOGGER.debug("saving artist {}", artist);
                artistRepository.save(artist);
            }
        }
    }
}
