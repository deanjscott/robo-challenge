package au.com.ioof.exception;

/**
 * An exception class to be used when there is an invalid movement issued to the robot controller
 */
public class InvalidMovementException extends Exception {

    public InvalidMovementException(String message) {
        super(message);
    }
}
