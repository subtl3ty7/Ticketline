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

    /**
     * Find all events
     *
     * @return list of all events
     */
    List<Event> findAllEvents();

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

    /**
     * Find all events with the same artistId
     *
     * @param artistId id of the artist to look for
     * @return a list of events that have the same, given artistId
     */
    List<Event> findEventsByArtistId(Long artistId);

    /**
     * Find events with the same name
     *
     * @param name - name to look for
     * @return a list of events that have the same, given name
     */
    List<Event> findEventsByName(String name);

    /**
     * Find events by their name, type, category, start and end time and showDuration
     *
     * @param name - name of the event to look for
     * @param type - type of the event to look for
     * @param category - category of the event to look for
     * @param startsAt - start date and time of the event to look for
     * @param endsAt - end date and time of the event to look for
     * @param showDuration - duration of the show to look for
     * @return a list of events that match the given criteria
     */
    List<Event> findEventsAdvanced(String name, Integer type, Integer category, LocalDateTime startsAt, LocalDateTime endsAt, Duration showDuration);

    /**
     * Find events by their eventCode, name, start and end range
     *
     * @param eventCode - code of the event to look for
     * @param name - name of the event to look for
     * @param startRange - start range to look for
     * @param endRange - end range to look for
     * @return a list of events that match the given criteria
     */
    List<Event> findSimpleEventsByParam(String eventCode, String name, LocalDateTime startRange, LocalDateTime endRange);
}
