package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;

import java.util.List;

public interface ShowService {
    List<Seat> getAllSeatsByShowId(Long id);
}
