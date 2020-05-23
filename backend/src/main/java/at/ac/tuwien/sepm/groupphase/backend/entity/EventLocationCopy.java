package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
public class EventLocationCopy extends EventLocation  {
    @Column(name = "PARENT_ID")
    private Long parentId;

    public EventLocationCopy() {}

    public EventLocationCopy(EventLocation eventLocation) {
        this.setParentId(eventLocation.getId());
        this.setCity(eventLocation.getCity());
        this.setCountry(eventLocation.getCountry());
        this.setCapacity(eventLocation.getCapacity());
        this.setPlz(eventLocation.getPlz());
        this.setEventLocationDescription(eventLocation.getEventLocationDescription());
        this.setName(eventLocation.getName());
        this.setStreet(eventLocation.getStreet());
        this.setSections(new ArrayList<>());
        for(Section section: eventLocation.getSections()) {
            this.getSections().add(new Section(section));
        }
    }
    /*
    public EventLocationCopy(EventLocationCopy eventLocation) {
        this.city = eventLocation.getCity();
        this.country = eventLocation.getCountry();
        this.capacity = eventLocation.getCapacity();
        this.plz = eventLocation.getPlz();
        this.eventLocationDescription = eventLocation.getEventLocationDescription();
        this.name = eventLocation.getName();
        this.street = eventLocation.getStreet();
        this.sections = new ArrayList<>();
        for(Section section: eventLocation.getSections()) {
            this.sections.add(new Section(section));
        }
    }
    */
}
