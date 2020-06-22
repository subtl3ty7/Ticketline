package at.ac.tuwien.sepm.groupphase.backend.unittests.service;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Table;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArtistServiceTest implements TestData {

    @Autowired
    private ArtistService artistService;

    private Artist artist = Artist.builder()
        .id(ID)
        .firstName(FNAME)
        .lastName(LNAME)
        .build();

    @Test
    @Order(1)
    public void whenSaveArtist_thenArtistSavedWithProperties() {
        Artist artist1 = artistService.saveNewArtist(artist);
        assertAll(
            () -> assertEquals(FNAME, artist1.getFirstName()),
            () -> assertEquals(LNAME, artist1.getLastName())
        );
    }

    @Test
    @Order(2)
    public void givenArtist_whenGetAll_thenListWithElementsAndArtistWithProperties() {
        List<Artist> artists = artistService.getAllArtists();
        Artist artist1 = artists.get(artists.size()-1);
        assertAll(
            () -> assertEquals(FNAME, artist1.getFirstName()),
            () -> assertEquals(LNAME, artist1.getLastName())
        );
    }

    @Test
    @Order(3)
    public void givenArtist_whenFindByFirstAndLastName_thenListWith1ElementAndArtistWithProperties() {
        List<Artist> artists = artistService.findArtistsByFirstAndLastName(FNAME, LNAME, 0);
        Artist artist1 = artists.get(0);
        assertAll(
            () -> assertEquals(FNAME, artist1.getFirstName()),
            () -> assertEquals(LNAME, artist1.getLastName())
        );
    }
}
