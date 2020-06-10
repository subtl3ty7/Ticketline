package at.ac.tuwien.sepm.groupphase.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.*;

/**
 *  allows building a user-friendly error message from unfulfilled Constraints
 */
public class ValidationMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String defaultConstraintMessage = "Input violates an(other) unknown constraint.";
    /**
     * maps constraint name to  a user-friendly message on wrong input (should not reveal any implementation details)
     */
    private static final Map<String, String> constraintMessageMap = new HashMap<>() {
        {
            //user
            put("user_id", "Id muss eindeutig und nicht null sein.");
            put("user_userCode", "User Code muss 6 Zeichnen lang sein.");
            put("user_email","Email darf nicht leer oder länger als 100 Zeichnen sein.");
            put("user_firstName",  "Vorname darf nicht leer oder länger als 30 Zeichnen sein.");
            put("user_lastName","Nachname darf nicht leer oder länger als 30 Zeichnen sein.");
            put("user_password", "Passwort muss zwischen 8 und 30 Zeichnen lang sein.");
            put("user_createdAt", "Created-At Feld darf nicht leer sein.");
            put("user_updatedAt", "Updated-At Feld darf nicht leer sein.");
            put("user_birthday","Geburtsdatum Feld darf nicht leer sein.");
            put("points_zero", "Punktestand muss bei der Registirerung 0 sein.");
            put("password_encoded", "Passwort muss bCrypt codiert sein.");
            put("birthday_16yo", "UserIn muss mindestens 16 Jahr alt sein.");
            put("userCode_unique", "User Code ist bereits in der Datenbank vorhanden.");
            put("userCode_notNull", "User Code darf nicht null sein.");
            put("isLogged_false", "Logged-In Feld darf nicht 'true' bei der Registrierung sein.");
            put("isBlocked_false", "Is-Blocked Feld darf nicht 'true' bei der Registrierung sein.");
            put("email_notNull", "EMail darf nicht null sein.");
            put("email_unique", "Email ist beretis in der Datenbank vorhanden.");
            put("email_valid", "EMail sieht nicht wie eine gültige Email-Adresse aus.");
            put("user_isLogged", "User muss eingeloggt sein.");
            put("user_exists", "User existiert nicht.");
            put("user_notAdmin", "User darf nicht Admin sein.");
            put("user_keepsRole", "User darf nicht die Rolle wechseln.");
            put("user_isCustomer", "User muss Kunde/Kundin sein.");
            put("user_isUnblocked", "User muss entsperrt sein, um gesperrt zu werden.");
            put("user_iBlocked", "User muss gesperrt sein, um entsperrt zu werden.");
            put("user_isSelf", "User ist nicht selbst.");
            put("user_isSelf_or_auth_isAdmin", "Entweder ist user nicht selbst oder User hat nicht Recht für diese Aktion.");


            //seat
            put("seat_seatRow", "Reihe darf nicht leer sein.");
            put("seat_seatColumn", "Säule darf nicht leer sein.");

            //section
            put("section_sectionName", "Name darf nicht leer oder länger als 100 Zeichnen sein.");
            put("section_sectionDescription", "Beschreibung darf nicht leer oder länger als 1000 Zeichnen sein.");
            put("section_seats", "Sitzliste darf nicht null sein.");
            put("section_capacity", "Die Sektionsskapazität darf nicht kleiner sein als die Anzahl der Sektionssplätze.");

            //eventLocation
            put("eventLocation_eventLocationName", "Name darf nicht leer oder länger als 100 Zeichnen sein.");
            put("eventLocation_eventLocationDescription", "Beschreibung darf nicht leer oder länger als 1000 Zeichnen sein.");
            put("eventLocation_sections", "Sektionsliste darf nicht null sein.");
            put("eventLocation_capacity", "Veranstaltungsortskapazität darf nicht kleiner sein als die Kapazitätssumme seiner Sektionen.");

            //show
            put("show_startsAt", "Aufführung braucht start-At Feld.");
            put("show_endsAt", "Aufführung braucht ends-At Feld.");
            put("show_eventLocation", "Veranstaltungsortliste darf nicht null sein.");
            put("eventLocation_given", "Veranstaltungsort muss vorhanden sein.");
            put("eventLocation_onlyOne", "In der Liste ist nur ein Veranstaltungsort zulässig.");
            put("eventLocation_exists", "Veranstaltungsort existiert nicht in der Datenbank.");
            put("eventLocation_unassigned", "Der Veranstaltungsort ist bereits einer Aufführung zugeordnet.");
            put("show_idNull", "Aufführung ID sollte null sein.");

            //event
            put("event_eventCode", "Event Code darf nicht leer sein und muss 6 Zeichnen lang sein.");
            put("event_name", "Name darf nicht leer oder länger als 100 Zeichnen sein.");
            put("event_description", "Beschreibung darf nicht leer oder länger als 1000 Zeichnen sein.");
            put("event_category", "Kategorie darf nicht leer oder länger als 100 Zeichnen sein.");
            put("event_type", "Typ darf nicht leer oder länger als 100 Zeichnen sein.");
            put("event_startsAt", "Veranstaltung braucht start-At Feld.");
            put("event_endsAt", "Veranstaltung braucht ends-At Feld.");
            put("event_photo", "Veranstaltung muss ein Foto beinhalten.");
            put("event_shows", "Aufführungliste darf nicht null sein.");
            put("event_artists", "KünstlerInnenliste darf nicht null sein.");
            put("event_prices", "Preisliste darf nicht null sein.");
            put("eventCode_unique", "Veranstaltungscode ist bereits in der Datenbank vorhanden.");
            put("event_exists", "Veranstaltung wurde nicht in der Datenbank gefunden.");

            //ticket
            put("ticketCode_unique", "Ticket code ist schon in der Datenbank vorhanden.");
            put("userCode_exists", "User wurde nicht in der Datenbank gefunden.");
            put("show_exists", "SAufführung wurde nicht in der Datenbank gefunden.");
            put("seat_notFree", "Platz ist bereits besetzt.");
            put("seat_exists", "Platz wurde nicht in der Datenbank gefunden.");
            put("tickets_sold", "Diese Aufführung ist ausverkauft.");
            put("no_tickets", "Dieser User hat keine Tickets.");
            put("ticket_exists", "Dieses Ticket ist schon in der Datenbank gespeichert.");
            put("admin_purchase", "Admin kann nicht die Tickets kaufen oder reservieren.");
            put("price_exists", "Preis muss eingegeben werden.");
            put("price_zero", "Preis darf nicht 0 sein.");
            put("seat_zero", "Platz id darf nicht 0 sein.");
            put("show_zero", "Aufführung id darf nicht 0 sein.");
            put("seat_null", "Platz id darf nicht null sein.");
            put("show_null", "Aufführung id darf nicht null sein.");
            put("price_null", "Preis darf nicht null sein.");
            put("ticket_notExist", "Ticket wurde nicht in der Datenbank gefunden.");
            put("ticket_purchased", "Ticket wurde noch nicht gekauft.");
            put("ticket_reserved", "Ticket wurde schon gekauft.");

            //news
            put("newsCode_unique", "News code ist schon in der Datenbank vorhanden.");
            put("news_newsCode", "News code muss 6 Zeichen lang sein.");
            put("news_title", "News Titel darf nicht leer oder länger als 100 Zeichnen sein.");
            put("news_publishedAt", "Veröffentlichungsdatum muss vorhanden sein.");
            put("news_stopsBeingRelevantAt", "Relevanzdatum muss vorhanden sein.");
            put("news_summary", "News Zusammenfassung darf nicht leer oder länger als 1000 Zeichnen sein.");
            put("news_text", "News text darf nicht leer oder länger als 100.000 Zeichnen sein.");
            put("news_author", "News autor muss vorhanden und nicht länger als 100 Zeichnen sein.");
            put("news_photo", "News Foto muss vorhanden sein.");
            put("seenBy_notNull", "SeenBy Liste darf nicht null sein.");

            //merchandise
            put("merchandise_premium", "Merchandise ist keine Prämie und kann nicht mit Prämienpunkten gekauft werden.");
            put("merchandise_outOfStock", "Merchandise ist ausverkauft.");
            put("merchandise_exists", "Merchandise wurde in der Datenbank nicht gefunden.");
            put("user_noPoints", "User hat nicht genügend Premium-Punkte, um dieses Produkt zu kaufen.");
        }
    };

    private Map<String, String> constraintMessages;

    public ValidationMessage(Constraints constraints) {
        this.constraintMessages = new HashMap<>();
        this.add(constraints);
    }

    private void add(Constraints constraints) {
        for(Map.Entry<String, Integer> entry: constraints.getViolated().entrySet()) {
            String message = mapConstraintToMessage(entry.getKey());
            if(entry.getValue() > 1) {
                message += " (Appeared " + entry.getValue() + " times)";
            }
            constraintMessages.put(entry.getKey(), message);
        }
    }

    private String mapConstraintToMessage(String constraint) {
        if(constraintMessageMap.get(constraint)!=null) {
            return constraintMessageMap.get(constraint);
        } else {
            return defaultConstraintMessage;
        }
    }

    public List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        for(String message: constraintMessages.values()) {
            messages.add(message);
        }
        return messages;
    }

    public Map<String, String> getConstraintMessages() {
        return constraintMessages;
    }

    public String getMessage() {
        String message = "";
        for(String m: getMessages()) {
            message += m + "\n";
        }
        return message;
    }
}