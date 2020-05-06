package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.util.CodeGenerator;
import at.ac.tuwien.sepm.groupphase.backend.util.Validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomEventService implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventRepository eventRepository;
    private final Validator validator;


    @Autowired
    public CustomEventService(EventRepository eventRepository, Validator validator) {
        this.eventRepository = eventRepository;
        this.validator = validator;
    }

    @Override
    public List<Event> findTop10EventsOfMonth() {
        List<Event> allEventsFromMonth = eventRepository.findAllByStartsAtAfterOrderByTotalTicketsSoldDesc(LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),1,0,0));
        List<Event> top10 = new ArrayList<>();
        for(int i = 0; i < Math.min(10, allEventsFromMonth.size()); i++) top10.add(allEventsFromMonth.get(i));
        return top10;
    }

    @Override
    public List<Event> findTop10EventsOfMonthByCategory(String category) {
        List<Event> allEventsFromMonth = eventRepository.findAllByStartsAtAfterAndCategoryOrderByTotalTicketsSoldDesc(LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),1,0,0), category);
        List<Event> top10 = new ArrayList<>();
        for(int i = 0; i < Math.min(10, allEventsFromMonth.size()); i++) top10.add(allEventsFromMonth.get(i));
        return top10;
    }

    @Override
    public Event createNewEvent(Event event){
        LOGGER.info("Moving Event Entity through Service Layer: " + event);
        event.setEventCode(getNewEventCode());
        return eventRepository.save(event);
    }

    private String getNewEventCode() {
        final int maxAttempts = 1000;
        String eventCode = "";
        int i;
        for(i=0; i<maxAttempts; i++) {
            eventCode = CodeGenerator.generateEventCode();
            if(!validator.validateEventCode(eventCode).isViolated()) {
                break;
            }
        }
        if(i==1000) {
            throw new ServiceException("Something went wrong while generating EventCode", null);
        }
        return eventCode;
    }

    @Override
    public Event findByEventCode(String eventCode) {
        Event event = eventRepository.findEventByEventCode(eventCode);
        event.getPrices();
        event.getArtists();
        return event;
    }
}
