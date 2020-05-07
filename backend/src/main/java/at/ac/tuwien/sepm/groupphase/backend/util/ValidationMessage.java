package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.exception.CustomConstraintViolationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import java.lang.invoke.MethodHandles;
import java.util.*;

/**
 *  allows building a user-friendly error message from unfulfilled Constraints
 */
public class ValidationMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String defaultDataAccessMessage = "Something went wrong while accessing the database.";
    private static final String defaultConstraintMessage = "Input violates an(other) unknown constraint.";
    /**
     * maps constraint name to  a user-friendly hint on wrong input (should not reveal any implementation details)
     */
    private static final Map<String, String> constraintHintMap = new HashMap<>() {
        {
            //user
            put("id", "Id has to be unique and not null.");
            put("userCode", "User Code had to be 6 characters long.");
            put("userCode_unique", "User Code is already present in database.");
            put("email","Email cannot be empty or longer than 100 characters.");
            put("email_unique", "Email is already present in database.");
            put("firstName",  "First name cannot be empty or longer than 30 characters.");
            put("lastName","Last name cannot be empty or longer than 30 characters.");
            put("password", "Password has to be between 8 and 30 characters.");
            put("isLogged_false", "Logged-In field cannot be true on Registration.");
            put("isBlocked_false", "Is-Blocked field cannot be true on Registration.");
            put("createdAt", "Created-At field cannot be empty.");
            put("updatedAt", "Updated-At field cannot be empty.");
            put("birthday","Birthday field cannot be empty.");
            put("points_zero", "Points have to be zero on Registration.");
            put("password_encoded", "Password has to be bCrypt encoded.");
            put("birthday_16yo", "Needs to be at least 16 years old.");


            //seat
            put("seatRow", "Row cannot be empty.");
            put("seatColumn", "Column cannot be empty.");

            //section
            put("sectionName", "Name cannot be empty or longer than 100 characters.");
            put("sectionDescription", "Description cannot be empty or longer than 1000 characters.");
            put("section_capacity", "Section capacity cannot be smaller than the number of section seats.");
            put("seats", "List of seats cannot be null.");

            //eventLocation
            put("eventLocationName", "Name cannot be empty or longer than 100 characters.");
            put("eventLocationDescription", "Description cannot be empty or longer than 1000 characters.");
            put("eventLocation_capacity", "Event Location capacity cannot be smaller than the capacity sum of its sections.");
            put("sections", "List of sections cannot be null.");
        }
    };

    private List<String> messages;

    public ValidationMessage() {
        this.messages = new ArrayList<>();
    }

    public ValidationMessage(Constraints constraints) {
        this.messages = new ArrayList<>();
        this.add(constraints);
    }

    public void add(Constraints constraints) {
        for(String constraint: constraints.getViolated()) {
            messages.add(mapConstraintToAdvice(constraint));
        }
    }

    private String mapConstraintToAdvice(String constraint) {
        if(constraintHintMap.get(constraint)!=null) {
            return constraintHintMap.get(constraint);
        } else {
            return defaultConstraintMessage;
        }
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getMessage() {
        String message = "";
        for(String m: messages) {
            message += m + "\n";
        }
        return message;
    }
}