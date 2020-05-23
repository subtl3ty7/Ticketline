package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventLocation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Section;

import java.util.List;

public interface EventLocationService {
    List<Seat> getAllSeatsByEventLocationId(Long id);
    List<EventLocation> getAllEventLocations();
    EventLocation save(EventLocation eventLocation);

    List<EventLocation> findEventLocationsByName(String locationName);
}
