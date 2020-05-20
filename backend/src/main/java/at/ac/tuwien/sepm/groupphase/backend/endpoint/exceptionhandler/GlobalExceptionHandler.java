package at.ac.tuwien.sepm.groupphase.backend.endpoint.exceptionhandler;

import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springfox.documentation.spring.web.json.Json;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Register all your Java exceptions here to map them into meaningful HTTP exceptions
 * If you have special cases which are only important for specific endpoints, use ResponseStatusExceptions
 * https://www.baeldung.com/exception-handling-for-rest-with-spring#responsestatusexception
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private class JsonResponse {
        LocalDateTime timestamp;
        int status;
        String error;
        List<String> messages;
    }

    /**
     * Handles all Spring Internal Exceptions (e.g. when JSON to Dto Mapping fails)
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        Exception exception,
        Object body,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        LOGGER.info("Handling Spring InternalException: " + exception.getMessage());
        return new ResponseEntity(
            new JsonResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                List.of("Input is invalid")
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    /**
     * This method handles all Exceptions for which there is no other handler
     * To make sure we don't reveal implementation details, the returned message should be something generic
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResponse> handleAll(Exception e) {
        LOGGER.info("Handling Exception: " + e.getClass().getName());
        e.printStackTrace();
        return new ResponseEntity(
            new JsonResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                List.of("Something unexpected happened")
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Handler for DataAccessExceptions
     * To make sure we don't reveal implementation details, the returned message should be something generic
     */
    @ExceptionHandler(value = {DataAccessException.class})
    protected ResponseEntity<Object> handleDataAccess(DataAccessException e) {
        LOGGER.info("Handling DataAccessException");
        e.printStackTrace();
        return new ResponseEntity(
            new JsonResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                List.of("Something went wrong while accessing the database.")
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Handler for ValidationExceptions
     * @return a body that contains a list of "messages" with all Validations that were violated
     */
    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<Object> handleValidation(ValidationException e) {
        LOGGER.info("Handling ValidationException");
        return new ResponseEntity(
            new JsonResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getValidationMessage().getMessages()
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handler for NotFoundExceptions
     */
    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<JsonResponse> handleNotFound(NotFoundException e) {
        LOGGER.info("Handling NotFoundException");
        return new ResponseEntity(
            new JsonResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                List.of("The requested Entity or Entities were not found")
            ),
            HttpStatus.NOT_FOUND
        );
    }


}

