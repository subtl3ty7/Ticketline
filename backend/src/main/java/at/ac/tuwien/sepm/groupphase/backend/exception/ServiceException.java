package at.ac.tuwien.sepm.groupphase.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class ServiceException extends BaseException {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private List<String> violationMessages;

    public ServiceException(List<String> violationMessages, Exception cause) {
        super("ServiceException thrown: " + violationMessages.toString(), cause);
        this.violationMessages = violationMessages;
    }

    public ServiceException(String violationMessage, Exception cause) {
        super("ServiceException thrown: " + violationMessage, cause);
        this.violationMessages = new ArrayList<>();
        this.violationMessages.add(violationMessage);
    }

    public List<String> getViolationMessages() {
        return violationMessages;
    }
}
