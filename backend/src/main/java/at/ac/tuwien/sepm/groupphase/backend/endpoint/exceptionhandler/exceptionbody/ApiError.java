package at.ac.tuwien.sepm.groupphase.backend.endpoint.exceptionhandler.exceptionbody;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ApiError {
    private HttpStatus status;
    List<String> messages;

    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public ApiError(HttpStatus status, List<String> messages) {
        this.status = status;
        this.messages = messages;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
