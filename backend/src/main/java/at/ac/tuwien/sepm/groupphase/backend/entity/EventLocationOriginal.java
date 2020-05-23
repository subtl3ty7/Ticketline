package at.ac.tuwien.sepm.groupphase.backend.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Entity
public class EventLocationOriginal extends EventLocation {

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "EVENT_LOCATION_ORIGINAL_ID", referencedColumnName = "ID")
    @Fetch(FetchMode.SELECT) //only way to fetch more than two collections with type eager ...
    private List<Show> shows;

    public EventLocationOriginal() {}


}
