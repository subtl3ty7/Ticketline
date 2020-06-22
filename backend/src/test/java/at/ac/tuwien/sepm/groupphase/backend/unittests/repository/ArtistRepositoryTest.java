package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class ArtistRepositoryTest implements TestData {

    private Artist artist = Artist.builder()
        .id(ID)
        .firstName(FNAME)
        .lastName(LNAME)
        .build();

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    public void beforeEach() {
        artistRepository.deleteAll();
        artist = Artist.builder()
            .id(ID)
            .firstName(FNAME)
            .lastName(LNAME)
            .build();
    }

    @Test
    public void givenNothing_whenSaveArtist_thenFindListWithOneElementAndFindArtistById() {
        artistRepository.save(artist);

        assertAll(
            () -> assertEquals(1, artistRepository.findAll().size()),
            () -> assertNotNull(artistRepository.findArtistById(artist.getId()))
        );
    }

    @Test
    public void givenNothing_whenSave2Artists_thenFindListWith2ElementsAndFindArtistOrderedByLName() {
        artist.setId(2L);
        artistRepository.save(artist);
        artist.setId(3L);
        artist.setLastName("NewLastName");
        artistRepository.save(artist);

        assertAll(
            () -> assertEquals(2, artistRepository.findAll().size()),
            () -> assertEquals("NewLastName", artistRepository.findAllByOrderByLastNameAscFirstNameAsc().get(0).getLastName())
        );
    }

    /*@Test
    public void givenNothing_whenSave1Artists_thenFindListWithOneElementAndFindArtistByNameIgnoreCase() {
        artistRepository.save(artist);

        assertAll(
            () -> assertEquals(1, artistRepository.findAll().size()),
            () -> assertEquals(1, artistRepository.findArtistsByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase("NAME", "LASTname").size())
        );
    }*/
}
