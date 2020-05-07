package at.ac.tuwien.sepm.groupphase.backend.endpoint.exceptionhandler;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.exceptionhandler.exceptionbody.ApiError;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Register all your Java exceptions here to map them into meaningful HTTP exceptions
 * If you have special cases which are only important for specific endpoints, use ResponseStatusExceptions
 * https://www.baeldung.com/exception-handling-for-rest-with-spring#responsestatusexception
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static HttpStatus constraintViolationStatus = HttpStatus.BAD_REQUEST;

    /**
     * Use the @ExceptionHandler annotation to write handler for custom exceptions
     */
    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        LOGGER.warn(ex.getMessage());
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Override methods from ResponseEntityExceptionHandler to send a customized HTTP response for a know exception
     * from e.g. Spring
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        LOGGER.info("Yea we handle method argument not valid");
        Map<String, Object> body = new LinkedHashMap<>();
        //Get all errors
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(err -> err.getField() + " " + err.getDefaultMessage())
            .collect(Collectors.toList());
        body.put("Validation errors", errors);

        return new ResponseEntity<>(body.toString(), headers, status);

    }

    /**
     * For all Exceptions that are not overridden, this method is called.
     * This is necessary because for all exceptions that are not overridden, the body is null.
     * In this method, we can provide a new body.
     * This is mainly necessary for debugging, we might want to change the body to something less revealing so we
     * dont accidentally reveal implementation details.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        Exception exception,
        Object body,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        LOGGER.warn("CONTROLLER EXCEPTION: " + exception.getMessage());
        return super.handleExceptionInternal(exception, new ApiError(List.of(exception.getMessage()), status), headers, status, request);
    }

    /**
     *
     */
    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<Object> handleConstraintViolation(ServiceException e) {
        LOGGER.debug("Handling Service Exception");
        ResponseEntity<Object> response = new ResponseEntity(
            new ApiError( e.getViolationMessages(), constraintViolationStatus ),
            constraintViolationStatus
        );
        LOGGER.info("Sending API Response: [" + response.getBody() + ", " + response.getStatusCode() + "]");
        return response;
    }
}