package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserAttemptsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ArtistEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private Artist artist = Artist.builder()
        .id(ID)
        .firstName(FNAME)
        .lastName(LNAME)
        .build();

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
    public void givenArtist_whenFindByNonExistingName_then200AndEmptyList() throws Exception{
        artistRepository.save(artist);

        MvcResult mvcResult = this.mockMvc.perform(get(ARTIST_BASE_URI)
            .param("firstName", "error").param("lastName", "error")
            .param("size", "10")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<ArtistDto> artistDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            ArtistDto[].class));

        assertEquals(0, artistDtos.size());
    }

    @Test
    public void givenArtist_whenFindByFullName_then200AndListWithSizeOneAndArtistWithAllProperties() throws Exception{
        artistRepository.save(artist);

        MvcResult mvcResult = this.mockMvc.perform(get(ARTIST_BASE_URI)
            .param("firstName", FNAME.toUpperCase()).param("lastName", LNAME.toUpperCase())
            .param("size", "0")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<ArtistDto> artistDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            ArtistDto[].class));

        assertEquals(1, artistDtos.size());
        ArtistDto artistDto = artistDtos.get(0);
        assertAll(
            () -> assertEquals(ID, artistDto.getId()),
            () -> assertEquals(FNAME, artistDto.getFirstName()),
            () -> assertEquals(LNAME, artistDto.getLastName())
        );
    }
}
