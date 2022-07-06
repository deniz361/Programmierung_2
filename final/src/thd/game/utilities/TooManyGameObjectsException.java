package thd.game.utilities;


/** Throws a Runtime Exception when there are too many Objects.*/
public class TooManyGameObjectsException extends RuntimeException {
    /** Initializes the Exception.
     * @param message the detail message
     */
    public TooManyGameObjectsException(String message) {
        super(message);
    }
}
