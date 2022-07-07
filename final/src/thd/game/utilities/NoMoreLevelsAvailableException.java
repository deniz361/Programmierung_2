package thd.game.utilities;


/** Wenn keine Level mehr frei existieren, man aber trotzdem in das nächste Level springen möchte, wird diese Exception ausgeführt. */
public class NoMoreLevelsAvailableException extends RuntimeException {

    /**
     * Der Konstruktor.
     * @param message Die Nachricht, die angezeigt wird, wenn die Exception auftritt.
     */
    public NoMoreLevelsAvailableException(String message) {
        super(message);
    }
}
