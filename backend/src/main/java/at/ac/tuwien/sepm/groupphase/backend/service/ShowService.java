package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;

import java.util.List;

public interface ShowService {
    List<Seat> getAllSeatsByShowId(Long id);
    Show findShowByShowId(Long id);
}
