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
     * @param size - number of event entries on the same page
     * @return a list of events that are on the same page
     */
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

    /**
     * Find x events by their artist id, x is defined by size
     *
     * @param artistId id of the artist to look for
     * @param size number of event entries on the same page
     * @return all events for one page that match the given criteria
     */
    List<Event> findEventsByArtistId(Long artistId, int size);

    /**
     * Find x events by their name, x is defined by size
     *
     * @param name name to look for
     * @param size number of event entries on the same page
     * @return all events for one page that match the given criteria
     */
    List<Event> findEventsByName(String name, int size);

    /**
     * Find x events by their name, type, category, start and end date and duration, x is defined by size
     *
     * @param name - name to look for
     * @param type - type to look for
     * @param category - category to look for
     * @param startsAt - start time to look for
     * @param endsAt - end time to look for
     * @param showDuration - duration to look for
     * @param size - number of event entries on the same page
     * @return all events for one page that match the given criteria
     */
    List<Event> findEventsAdvanced(String name, Integer type, Integer category, LocalDateTime startsAt, LocalDateTime endsAt, Duration showDuration, int size);

    /**
     * Find x events by their code, name, start and end time, where x is defined by size
     *
     * @param eventCode - code of the event to look for
     * @param name - name of the event to look for
     * @param startRange - start time to look for
     * @param endRange - end time to look for
     * @param size - number of news entries on the same page
     * @return all events for one page that match the given criteria
     */
    List<Event> findSimpleEventsByParam(String eventCode, String name, LocalDateTime startRange, LocalDateTime endRange, int size);

    /**
     * Find x events, where x is defined by number
     *
     * @param number - number of the events to look for
     * @return x events
     */
    List<Event> findNumberOfEvents(int number);
}
