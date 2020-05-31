package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventLocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ShowRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventLocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Seat> getAllSeatsByShowId(Long id) {
        List<Seat> seats = new ArrayList<>();
        Show show = showRepository.findShowById(id);
        for(Section section: show.getEventLocationCopy().getSections() ) {
            seats.addAll(section.getSeats());
        }
        return seats;
    }

    @Override
    public List<Show> getShowsByEventLocationId(Long eventLocationId) {
        return showRepository.findShowsByEventLocationOriginalId(eventLocationId);
    }

    @Override
    public List<Show> findShowsAdvanced(String name, EventTypeEnum type, EventCategoryEnum category, LocalDateTime startsAtDate, LocalDateTime endsAtDate, Duration showDuration, Integer price) {
        return showRepository.findShowsByEventNameContainingIgnoreCaseAndEventTypeAndEventCategoryAndStartsAtIsGreaterThanEqualAndEndsAtIsLessThanEqualAndDurationLessThanEqualAndPriceLessThanEqual(name, type, category, startsAtDate, endsAtDate, showDuration, price);
    }

}
