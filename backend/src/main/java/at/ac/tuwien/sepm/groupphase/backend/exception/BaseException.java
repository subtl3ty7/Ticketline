package at.ac.tuwien.sepm.groupphase.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public abstract class BaseException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public BaseException(String message, Exception cause) {
        super(message);
        initCause(cause);
        LOGGER.error(message);
    }

    public BaseException(String message) {
        super(message);
        LOGGER.error(message);
    }

    public BaseException() {}
}
