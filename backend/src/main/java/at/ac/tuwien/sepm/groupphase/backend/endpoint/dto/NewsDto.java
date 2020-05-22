package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Show;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class NewsDto {
    private String newsCode;
    private String title;
    private LocalDateTime publishedAt;
    private LocalDateTime stopsBeingRelevantAt;
    private String summary;
    private String text;
    private String author;
    private String photo;
}
