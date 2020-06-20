package at.ac.tuwien.sepm.groupphase.backend.endpoint.exceptionhandler;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ErrorResponseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Register all your Java exceptions here to map them into meaningful HTTP exceptions
 * If you have special cases which are only important for specific endpoints, use ResponseStatusExceptions
 * https://www.baeldung.com/exception-handling-for-rest-with-spring#responsestatusexception
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ObjectMapper objectMapper;

    @Autowired
    public GlobalExceptionHandler() {
        objectMapper = new ObjectMapper();
    }

    private void log(Exception e) {
        LOGGER.info("Handling Exception: " + e.getClass().getName());
        LOGGER.info("Exceptionmessage: " + e.getMessage());
        e.printStackTrace();
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
        this.log(exception);
        return new ResponseEntity(
            new ErrorResponseDto(
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
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAny(Exception e) {
        this.log(e);

        return new ResponseEntity(
            new ErrorResponseDto(
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
    public ResponseEntity<Object> handleDataAccess(DataAccessException e) {
        this.log(e);
        return new ResponseEntity(
            new ErrorResponseDto(
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
    public ResponseEntity<Object> handleValidation(ValidationException e) {
        this.log(e);
        ResponseEntity<Object> responseEntity = new ResponseEntity(
            new ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getValidationMessage().getMessages()
            ),
            HttpStatus.BAD_REQUEST
        );
        LOGGER.info("Sending ResponseEntity(body=" + responseEntity.getBody() + ", statusCode=" + responseEntity.getStatusCode() + ")");
        return responseEntity;
    }

    /**
     * Handler for NotFoundExceptions
     */
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFound(NotFoundException e) {
        this.log(e);
        return new ResponseEntity(
            new ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                List.of("The requested Entity or Entities were not found")
            ),
            HttpStatus.NOT_FOUND
        );
    }

    /**
     * Handler for AuthenticationException (When Login fails)
     */
    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ErrorResponseDto> handleAuthentication(AuthenticationException e, HttpServletResponse response) {
        this.log(e);
        String errorMsg = "";
        if(e instanceof BadCredentialsException) {
            errorMsg = "Email wurde nicht gefunden oder das Passwort ist falsch";
        } else if (e instanceof LockedException) {
            errorMsg = "Dieser Account ist blockiert!";
        } else {
            errorMsg = "Login war nicht erfolgreich.";
        }
        return this.handleUnauthorized(e, response, errorMsg);
    }

    /**
     * Handler for AuthorizationException (e.g. when invalid Token)
     */
    public ResponseEntity<ErrorResponseDto> handleAuthorization(Exception e, HttpServletResponse response) {
        this.log(e);
        return this.handleUnauthorized(e, response, e.getMessage());
    }

    private ResponseEntity<ErrorResponseDto> handleUnauthorized(Exception e, HttpServletResponse response, String errorMsg) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            LocalDateTime.now(),
            HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            List.of(errorMsg)
        );
        response.addHeader("Content-Type", "application/json");
        response.setCharacterEncoding(null);
        response.setStatus(errorResponseDto.getStatus());
        try {
            response.getOutputStream().println(objectMapper.writeValueAsString(errorResponseDto));
        } catch (IOException ioexception) {
            throw new RuntimeException("Something went wrong while writing to Outputstream", ioexception);
        }

        return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
    }


}

