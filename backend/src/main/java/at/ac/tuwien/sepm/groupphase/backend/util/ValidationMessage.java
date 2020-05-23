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

    private static final String defaultConstraintMessage = "Input violates an(other) unknown constraint.";
    /**
     * maps constraint name to  a user-friendly hint on wrong input (should not reveal any implementation details)
     */
    private static final Map<String, String> constraintHintMap = new HashMap<>() {
        {
            //user
            put("user_id", "Id has to be unique and not null.");
            put("user_userCode", "User Code had to be 6 characters long.");
            put("user_email","Email cannot be empty or longer than 100 characters.");
            put("user_firstName",  "First name cannot be empty or longer than 30 characters.");
            put("user_lastName","Last name cannot be empty or longer than 30 characters.");
            put("user_password", "Password has to be between 8 and 30 characters.");
            put("user_createdAt", "Created-At field cannot be empty.");
            put("user_updatedAt", "Updated-At field cannot be empty.");
            put("user_birthday","Birthday field cannot be empty.");
            put("points_zero", "Points have to be zero on Registration.");
            put("password_encoded", "Password has to be bCrypt encoded.");
            put("birthday_16yo", "Needs to be at least 16 years old.");
            put("userCode_unique", "User Code is already present in database.");
            put("userCode_notNull", "User Code cannot be null.");
            put("isLogged_false", "Logged-In field cannot be true on Registration.");
            put("isBlocked_false", "Is-Blocked field cannot be true on Registration.");
            put("email_notNull", "EMail can't be null.");
            put("email_unique", "Email is already present in database.");
            put("email_valid", "Email does not look like a valid EMail Address.");
            put("user_isLogged", "Needs to be logged in.");
            put("user_exists", "User does not exist.");
            put("user_notAdmin", "User can't be an Admin.");
            put("user_keepsRole", "Can't change role.");
            put("user_isCustomer", "Need to be a Customer.");
            put("user_isUnblocked", "Needs to be unblocked to get blocked.");
            put("user_iBlocked", "Needs to be blocked to get unblocked.");
            put("user_isSelf", "User is not the self");


            //seat
            put("seat_seatRow", "Row cannot be empty.");
            put("seat_seatColumn", "Column cannot be empty.");

            //section
            put("section_sectionName", "Name cannot be empty or longer than 100 characters.");
            put("section_sectionDescription", "Description cannot be empty or longer than 1000 characters.");
            put("section_seats", "List of seats cannot be null.");
            put("section_capacity", "Section capacity cannot be smaller than the number of section seats.");

            //eventLocation
            put("eventLocation_eventLocationName", "Name cannot be empty or longer than 100 characters.");
            put("eventLocation_eventLocationDescription", "Description cannot be empty or longer than 1000 characters.");
            put("eventLocation_sections", "List of sections cannot be null.");
            put("eventLocation_capacity", "Event Location capacity cannot be smaller than the capacity sum of its sections.");

            //show
            put("show_startsAt", "Show needs a start-At field.");
            put("show_endsAt", "Show needs an ends-At field.");
            put("show_eventLocation", "List of Event Locations cannot be null.");
            put("eventLocation_given", "Event Location has to be given.");
            put("eventLocation_onlyOne", "Only one Event Location allowed in list.");
            put("eventLocation_exists", "Event Location does not exist in database.");
            put("eventLocation_unassigned", "Event Location is already assigned to a show.");
            put("show_idNull", "Show ID should be null.");

            //event
            put("event_eventCode", "Event Code cannot be empty and has to be 6 characters long.");
            put("event_name", "Name cannot be empty and cannot be longer than 100 characters.");
            put("event_description", "Description cannot be empty and cannot be longer than 1000 characters.");
            put("event_category", "Category cannot be empty and cannot be longer than 100 characters.");
            put("event_type", "type cannot be empty and cannot be longer than 100 characters.");
            put("event_startsAt", "Event needs a start-At field.");
            put("event_endsAt", "Event needs an ends-At field.");
            put("event_photo", "Event needs an image.");
            put("event_shows", "Show list cannot be null.");
            put("event_artists", "Artist list cannot be null.");
            put("event_prices", "Prices list cannot be null.");
            put("eventCode_unique", "Event Code is already present in database.");
            put("event_exists", "Event was not found in the database.");

            //ticket
            put("ticketCode_unique", "Ticket code is already present in the database.");
            put("userCode_exists", "User was not found in the database.");
            put("show_exists", "Show was not found in the database.");
            put("seat_notFree", "The seat is already taken.");
            put("seat_exists", "The seat was not found in the database.");
            put("tickets_sold", "This show is sold out.");
            put("no_tickets", "This user has no tickets.");
            put("ticket_exists", "This ticket is already saved in the database.");
            put("admin_purchase", "Admin can not reserve or purchase tickets.");
            put("price_exists", "Price has to be inputed.");
            put("price_zero", "Price can not be zero.");
            put("seat_zero", "Seat id can not be zero.");
            put("show_zero", "Show id can not be zero.");
            put("seat_null", "Seat id can not be null.");
            put("show_null", "Show id can not be null.");
            put("price_null", "Price can not be null.");
            put("ticket_notExist", "Ticket is not found in the database.");
            put("ticket_purchased", "Ticket is not purchased yet.");
            put("ticket_reserved", "Ticket is already purchased.");

            //news
            put("newsCode_unique", "News code is already present in the database.");
            put("news_newsCode", "News code has to be 6 characters long.");
            put("news_title", "News title cannot be empty or longer than 100 characters.");
            put("news_publishedAt", "Published Date has to be given.");
            put("news_stopsBeingRelevantAt", " Relevancy Date has to be given.");
            put("news_summary", "News summary cannot be empty or longer than 1000 characters.");
            put("news_text", "News text cannot be empty or longer than 100.000 characters.");
            put("news_author", "News author has to be given and cannot be longer than 100 characters.");
            put("news_photo", "News image has to be given.");
            put("seenBy_notNull", "SeenBy List cannot be null.");
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
        for(Map.Entry<String, Integer> entry: constraints.getViolated().entrySet()) {
            String message = mapConstraintToAdvice(entry.getKey());
            if(entry.getValue() > 1) {
                message += " (Appeared " + entry.getValue() + " times)";
            }
            messages.add(message);
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