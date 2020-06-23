package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;

public interface SeatService {

    /**
     * Save a seat in the database
     *
     * @param seat - Seat object that needs to be saved
     * @return a single, newly-created seat
     */
    Seat saveSeat(Seat seat);
}
