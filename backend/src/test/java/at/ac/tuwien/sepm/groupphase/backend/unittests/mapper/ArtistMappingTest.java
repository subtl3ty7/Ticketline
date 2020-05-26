package at.ac.tuwien.sepm.groupphase.backend.unittests.mapper;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ArtistMappingTest implements TestData {

    private final Artist artist = Artist.builder()
        .id(ID)
        .firstName(FNAME)
        .lastName(LNAME)
        .build();

    @Autowired
    private ArtistMapper artistMapper;

    @Test
    public void shouldMapArtistToArtistDto() {
        ArtistDto artistDto = artistMapper.artistToArtistDto(artist);
        assertAll(
            () -> assertEquals(ID, artistDto.getId()),
            () -> assertEquals(FNAME, artistDto.getFirstName()),
            () -> assertEquals(LNAME, artistDto.getLastName())
        );
    }

    @Test
    public void shouldMapArtistListToArtistDtoList() {
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        artists.add(artist);
        List<ArtistDto> artistDtos = artistMapper.artistsToArtistDtos(artists);
        ArtistDto artistDto = artistDtos.get(0);
        assertAll(
            () -> assertEquals(ID, artistDto.getId()),
            () -> assertEquals(FNAME, artistDto.getFirstName()),
            () -> assertEquals(LNAME, artistDto.getLastName())
        );
    }
}
