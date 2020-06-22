package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CustomArtistService implements ArtistService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ArtistRepository artistRepository;
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public CustomArtistService(ArtistRepository artistRepository, EntityManagerFactory entityManagerFactory) {
        this.artistRepository = artistRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Artist> findArtistsByFirstAndLastName(String firstName, String lastName, int size) {
        LOGGER.debug("Find artists by first/last name");
        int page = calculateNumberOfPage(size);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Artist> artistsPage = artistRepository.findArtistsByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName, pageRequest);
        return artistsPage.toList();
    }

    private int calculateNumberOfPage(int size) {
        int result = 0;
        if (size != 0) {
            result = size / 10;
        }
        return result;
    }

    @Override
    public List<Artist> getAllArtists() {
        LOGGER.debug("Find all artists");
        return artistRepository.findAllByOrderByLastNameAscFirstNameAsc();
    }

    @Override
    public Artist saveNewArtist(Artist artist) {
        LOGGER.debug("Save new artist");
        return artistRepository.save(artist);
    }
}
