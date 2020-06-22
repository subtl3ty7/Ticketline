package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class
ArtistRepositoryTest implements TestData {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    public void givenNothing_whenSaveArtist_thenFindListWithOneElementAndFindArtistById() {
        Artist artist = Artist.builder()
            .id(ID)
            .firstName(FNAME)
            .lastName(LNAME)
            .build();

        artistRepository.save(artist);

        assertAll(
            () -> assertEquals(1, artistRepository.findAll().size()),
            () -> assertNotNull(artistRepository.findArtistById(artist.getId()))
        );
    }
}
