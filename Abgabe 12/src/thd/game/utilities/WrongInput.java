package thd.game.utilities;

/** Creates a new Runtime Exception when someone inputs the wrong String. */
public class WrongInput extends RuntimeException{

    /** Initializes the Exception.
     * @param message the detail message
     */
    public WrongInput(String message) {
        super(message);
    }
}
