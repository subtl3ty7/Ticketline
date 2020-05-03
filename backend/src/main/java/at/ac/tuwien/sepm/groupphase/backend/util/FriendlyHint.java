package at.ac.tuwien.sepm.groupphase.backend.util;

/**
 * Class that encapsulates two fields:
 * - user-friendly hint on wrong input (should not reveal any implementation details)
 * - property name related to that input (should not reveal any implementation details)
 */
public class FriendlyHint {
    private String propertyName;
    private String hint;

    public FriendlyHint(String propertyName, String hint) {
        this.propertyName = propertyName;
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
