package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import at.ac.tuwien.sepm.groupphase.backend.repository.ShowRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ShowService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomShowService implements ShowService {

    private final ShowRepository showRepository;

    @Autowired
    public CustomShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Transactional
    @Override
    public List<Seat> getAllSeatsByShowId(Long id) {
        List<Seat> seats = new ArrayList<>();
        Show show = showRepository.findShowById(id);
        for(Section section: show.getEventLocation().getSections() ) {
            seats.addAll(section.getSeats());
        }
        return seats;
    }

    @Override
    public List<Show> getShowsByEventLocationId(Long eventLocationId) {
        return showRepository.findShowsByEventLocationId(eventLocationId);
    }

    @Override
    public List<Show> findShowsAdvanced(String name, Integer type, Integer category, LocalDateTime startsAt, LocalDateTime endsAt, Duration showDuration, Integer price) {
        return showRepository.findShowsByEventNameContainingIgnoreCaseAndEventTypeOrEventTypeIsNullAndEventCategoryOrEventCategoryIsNullAndStartsAtIsGreaterThanEqualAndEndsAtIsLessThanEqualAndDurationLessThanEqualAndPriceLessThanEqualOrPriceIsNull(name, type, category, startsAt, endsAt, showDuration, price);
    }

    @Override
    @Transactional
    public Show findShowById(Long id, boolean initEventLocation) {
        Show show = showRepository.findShowById(id);
        if(initEventLocation) {
            for(Section section: show.getEventLocation().getSections()) {
                Hibernate.initialize(section.getSeats());
            }
        }
        Hibernate.initialize(show.getTakenSeats());
        return show;
    }

    @Override
    @Transactional
    public boolean isSeatFree(Show show, Seat seat) {
        show = this.findShowById(show.getId(), false);
        List<Seat> takenSeats = show.getTakenSeats();
        boolean doesContain = takenSeats.contains(seat);

        return !doesContain;
    }

    @Transactional
    @Override
    public Seat findFreeSeat(Show show) {
        show = showRepository.findShowById(show.getId());
        List<Seat> seats = getAllSeatsByShowId(show.getId());
        for(Seat seat: seats) {
            if(!show.getTakenSeats().contains(seat)) {
                return seat;
            }
        }
        return null;
    }
}
