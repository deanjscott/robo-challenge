package au.com.ioof.exception;

/**
 * An exception class to be used when there is an invalid command issued to the robot controller
 */
public class InvalidUserCommandException extends Exception {

    public InvalidUserCommandException(String message) {
        super(message);
    }
}
