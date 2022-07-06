package thd.gameobjects.base;


import thd.gameview.GameView;

import java.util.Objects;

/**
 * Ändert die Position von den Spielobjekten.
 * {@link GameView}
 * @see GameView
 */
public class Position implements Cloneable, Comparable<Position> {

    /**Die x Koordinate des Spielobjekts.*/
    public double x;
    /**Die y Koordinate des Spielobjekts.*/
    public double y;


    /** Der Konstruktor mit Parameter.
     * @param x Individuelle x Koordinate
     * @param y Individuelle y Koordinate*/
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Konstruktor ohne Parameter. */
    public Position() {
        this(0, 0);
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
        y -= 1;
    }

    /** Verschiebt das Objekt immer um 1 nach unten.*/
    public void down() {
        y += 1;
    }

    /** Verschiebt das Objekt nach links.
     * @param range Um wie viel das Objekt nach links verschoben werden soll*/
    public void left(double range) {
        x -= range;
    }

    /** Verschiebt das Objekt nach rechts.
     * @param range Um wie viel das Objekt nach rechts verschoben werden soll*/
    public void right(double range) {
        x += range;
    }

    /** Verschiebt das Objekt nach oben.
     * @param range Um wie viel das Objekt nach oben verschoben werden soll*/
    public void up(double range) {
        y -= range;
    }

    /** Verschiebt das Objekt nach unten.
     * @param range Um wie viel das Objekt nach unten verschoben werden soll*/
    public void down(double range) {
        y += range;
    }

    /**
     * Abstand zur Zielposition mit Pythagoras.
     * @param other Die Position des anderen Objekts
     * @return gibt den direkten Weg von einem Objekt zu dem anderen Objekt zurück*/
    public double distance(Position other) {
        var distanceInX = Math.pow(this.x - other.x, 2);
        var distanceInY = Math.pow(this.y - other.y, 2);

        return Math.sqrt(distanceInX + distanceInY);
    }


    /** Checks if 2 positions are the same.
     * @return returns true, if the position is the same and false if not
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Position other = (Position) o;
        //return Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0; //Von Prof Berl
        return x == other.x && y == other.y;  //von Paul
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Position (");
        stringBuilder.append((int) Math.round(x)).append(", ").append((int) Math.round(y)).append(")");

        return stringBuilder.toString();
    }


    @Override
    public Position clone() {
        Position clone = null;
        try {
            clone = (Position) super.clone();

        } catch (CloneNotSupportedException ignored) {
        }
        return clone;
    }


    /**
     * Compares the Position depending on their distance to the Position(0,0).
     * @param o the other object to be comared
     * @return x < 0 if o is nearer to (0,0),
     * x > 0 if o is further away from (0,0),
     * x = 0 if they have the same distance
     */
    @Override
    public int compareTo(Position o) {
        Position comparator = new Position();
        return Double.compare(this.distance(comparator), o.distance(comparator));
    }
}
