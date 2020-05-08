package at.ac.tuwien.sepm.groupphase.backend.endpoint.exceptionhandler.exceptionbody;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ApiError {
    List<String> messages;
    private HttpStatus status;
}
