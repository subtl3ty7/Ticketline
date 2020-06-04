package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SimpleNewsDto {
    private String newsCode;
    private String title;
    private LocalDateTime publishedAt;
    private String author;
}
