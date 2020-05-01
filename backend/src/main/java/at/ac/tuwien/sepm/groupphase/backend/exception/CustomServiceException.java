package at.ac.tuwien.sepm.groupphase.backend.exception;

public class CustomServiceException extends RuntimeException {

    public CustomServiceException(String message) {
        super(message);
    }
}
