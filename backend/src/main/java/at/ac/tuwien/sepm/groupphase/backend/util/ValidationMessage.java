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
    private static final Map<String, FriendlyHint> constraintHintMap = new HashMap<>() {
        {
            put("id", new FriendlyHint("ID", "Id has to be unique and not null."));
            put("userCode", new FriendlyHint("User Code", "User Code has to be given and cannot be longer than 6 characters."));
            put("userCode_unique", new FriendlyHint("User Code", "User Code is already present in database."));
            put("email", new FriendlyHint("Email", "Email has to be given and cannot be longer than 100 characters."));
            put("email_unique", new FriendlyHint("Email", "Email is already present in database."));
            put("firstName", new FriendlyHint("First Name", "First name has to be given and cannot be longer than 30 characters."));
            put("lastName", new FriendlyHint("Last Name", "Last name has to be given and cannot be longer than 30 characters."));
            put("password", new FriendlyHint("Password", "Password has to be given and cannot be longer than 30 characters."));
            put("isLogged_false", new FriendlyHint("Logged in", "Logged-In field cannot be true here."));
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
            return constraintHintMap.get(constraint).getHint();
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