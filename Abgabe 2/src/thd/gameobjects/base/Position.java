package thd.gameobjects.base;


import thd.gameview.GameView;

/**
 * Ändert die Position von den Spielobjekten.
 * {@link GameView}
 * @see GameView
 */
public class Position {

    /**
     * Die x Koordinate des Spielobjekts.
     */
    public double x;
    /**
     * Die y Koordinate des Spielobjekts.
     */
    public double y;

    private double distance;
    private double distanceInX;
    private double distanceInY;


    /**
     *
     * @param x Individuelle x Koordinate
     * @param y Individuelle y Koordinate
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        distance = 0;
    }

    /** Konstruktor ohne Parameter. */
    public Position() {
        x = 0.0;
        y = 0.0;
        distance = 0;
    }


    /** Verschiebt das Objekt immer um 1 nach rechts.*/
    public void right() {
        x += 1;
    }

    /** Verschiebt das Objekt immer um 1 nach links.*/
    public void left() {
        x -= 1;
    }

    /** Verschiebt das Objekt immer um 1 nach oben.*/
    public void up() {
        x -= 1;
    }

    /** Verschiebt das Objekt immer um 1 nach unten.*/
    public void down() {
        y += 1;
    }

    /** Verschiebt das Objekt nach links.
     * @param range Um wie viel das Objekt nach links verschoben werden soll
     * */
    public void left(double range) {
        x -= range;
    }

    /** Verschiebt das Objekt nach rechts.
     * @param range Um wie viel das Objekt nach rechts verschoben werden soll
     * */
    public void right(double range) {
        x += range;
    }

    /** Verschiebt das Objekt nach oben.
     * @param range Um wie viel das Objekt nach oben verschoben werden soll
     * */
    public void up(double range) {
        y += range;
    }

    /** Verschiebt das Objekt nach unten.
     * @param range Um wie viel das Objekt nach unten verschoben werden soll
     * */
    public void down(double range) {
        y += range;
    }

    /**
     * Abstand zur Zielposition mit Pythagoras
     * @param other Die Position des anderen Objekts
     * @return gibt den direkten Weg von einem Objekt zu dem anderen Objekt zurück
     */
    public double distance(Position other) {
        distanceInX = Math.pow(this.x - other.x, 2);
        distanceInY = Math.pow(this.y - other.y, 2);

        distance = Math.sqrt(distanceInX + distanceInY);

        return distance;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Position (");
        stringBuilder.append((int) Math.round(x)).append(", ").append((int) Math.round(y)).append(")");

        return stringBuilder.toString();
    }
}
