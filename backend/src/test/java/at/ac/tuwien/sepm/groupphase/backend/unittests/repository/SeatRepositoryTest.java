package at.ac.tuwien.sepm.groupphase.backend.unittests.repository;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
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
public class SeatRepositoryTest implements TestData {

    @Autowired
    private SeatRepository seatRepository;

    @Test
    public void givenNothing_whenSaveSeat_thenFindListWithOneElementAndFindSeatById() {
        Seat seat = Seat.builder()
            .id(ID)
            .seatColumn(COLUMN)
            .seatRow(ROW)
            .build();

        seatRepository.save(seat);

        assertAll(
            () -> assertEquals(1, seatRepository.findAll().size()),
            () -> assertNotNull(seatRepository.findSeatById(seat.getId()))
        );
    }
}
