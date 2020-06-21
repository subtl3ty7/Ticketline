package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class ErrorResponseDto {
    LocalDateTime timestamp;
    int status;
    String error;
    List<String> messages;
}
