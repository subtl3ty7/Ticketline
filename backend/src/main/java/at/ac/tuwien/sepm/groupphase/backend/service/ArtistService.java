package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;

import java.util.List;

public interface ArtistService {
    /**
     * Find artists based on their first and/or last name
     *
     * @param firstName the artist's first name
     * @param lastName the artist's last name
     * @return a list of artist entities
     */
    List<Artist> findArtistsByFirstAndLastName(String firstName, String lastName, int size);

    /**
     * Find all artists in the database
     * @return a list of artist entities
     */
    List<Artist> getAllArtists();

    /**
     * Save an artist in the database
     * @param artist the artist that should be saved
     * @return the artist that was saved
     */
    Artist saveNewArtist(Artist artist);
}
