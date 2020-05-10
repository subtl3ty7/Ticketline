package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.SeatService;
import org.springframework.stereotype.Service;

@Service
public class CustomSeatService implements SeatService {
    private final SeatRepository seatRepository;

    public CustomSeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public Seat saveSeat(Seat seat) {
        return this.seatRepository.save(seat);
    }
}
