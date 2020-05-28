package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<Event> findTop10EventsOfMonth();

    List<Event> findTop10EventsOfMonthByCategory(String category);

    List<Event> findAllEvents();

    Event createNewEvent(Event event);

    Event findByEventCode(String eventCode);

    void deletebyEventCode(String eventCode);

    List<Event> findEventsByArtistId(Long artistId);

    List<Event> findEventsByName(String name);

    List<Event> findEventsAdvanced(String name, String type, String category, LocalDateTime startsAt, LocalDateTime endsAt, String showLength, Long price);
}
