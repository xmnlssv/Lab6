package model;

import java.util.Objects;

/**
 * A class representing the coordinates of a point on a two-dimensional plane.
 */
public class Coordinates {
    /**
     * The x-coordinate of the coordinates. The value of the field must be greater than -112, The field cannot be null.
     */
    private Double x; // greater than -112, not null

    /**
     * the y-coordinate of the coordinates. Cannot be null.
     */
    private Long y; // not null

    public Coordinates() {
    }

    /**
     * Sets the x-coordinate and y-point of the point to the given value.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public Coordinates(Double x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the point.
     *
     * @return the x-coordinate
     */
    public Double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the point to the given value (must not be null or <=-112).
     *
     * @param x the new x-coordinate
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the point.
     *
     * @return the y-coordinate
     */
    public Long getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the point to the given value.
     *
     * @param y the new y-coordinate
     */
    public void setY(long y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false; // проверка, что первый объект нормальный, а второй null
        if (getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (y != that.y) return false;
        return Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        long result = x != null ? x.hashCode() : 0;
        result = 31 * result + y;
        return (int) result;
    }

    /**
     * Returns a string representation of the coordinates in the format "model.Coordinates{x=x, y=y}".
     *
     * @return a string representation of the coordinates
     */
    @Override
    public String toString() {
        return "model.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
