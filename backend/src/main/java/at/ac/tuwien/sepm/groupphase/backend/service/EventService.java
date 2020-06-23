package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    /**
     * Find top 10 events of the month.
     *
     * @return a list of events that are in top 10 events.
     */
    List<Event> findTop10EventsOfMonth();

    /**
     * Find top 10 events of the month in the given category.
     *
     * @param category - a category to filter the events.
     * @return a list of events that are top 10 events in the given category.
     */
    List<Event> findTop10EventsOfMonthByCategory(String category);

    List<Event> findAllEvents(int size);

    /**
     * Create a new event
     *
     * @param event - event object that needs to be created
     * @return a single, newly-created event
     */
    Event createNewEvent(Event event);

    /**
     * Find event by its eventCode
     *
     * @param eventCode - code of the event to look for
     * @return a single event that matches the given eventCode
     */
    Event findByEventCode(String eventCode);

    /**
     * Find and delete event with the given eventCode
     *
     * @param eventCode - code of the event to look for and to delete
     */
    void deletebyEventCode(String eventCode);

    List<Event> findEventsByArtistId(Long artistId, int size);

    List<Event> findEventsByName(String name, int size);

    List<Event> findEventsAdvanced(String name, Integer type, Integer category, LocalDateTime startsAt, LocalDateTime endsAt, Duration showDuration, int size);

    List<Event> findSimpleEventsByParam(String eventCode, String name, LocalDateTime startRange, LocalDateTime endRange, int size);

    List<Event> findNumberOfEvents(int number);
}
