package at.ac.tuwien.sepm.groupphase.backend.exception;


public class NotFoundException extends BaseException {

    public NotFoundException(String message, Exception cause) {
        super(message, cause);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
