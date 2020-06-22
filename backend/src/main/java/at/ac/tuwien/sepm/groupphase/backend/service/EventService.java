package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<Event> findTop10EventsOfMonth();

    List<Event> findTop10EventsOfMonthByCategory(String category);

    List<Event> findAllEvents(int size);

    Event createNewEvent(Event event);

    Event findByEventCode(String eventCode);

    void deletebyEventCode(String eventCode);

    List<Event> findEventsByArtistId(Long artistId, int size);

    List<Event> findEventsByName(String name, int size);

    List<Event> findEventsAdvanced(String name, Integer type, Integer category, LocalDateTime startsAt, LocalDateTime endsAt, Duration showDuration, int size);

    List<Event> findSimpleEventsByParam(String eventCode, String name, LocalDateTime startRange, LocalDateTime endRange, int size);

    List<Event> findNumberOfEvents(int number);
}
