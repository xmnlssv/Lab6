package model;

import java.util.Objects;

/**
 * A class representing a location with a name and 3D coordinates.
 */
public class Location {
    /**
     * The x-coordinate of the location. Cannot be null.
     */
    private Float x; // The field cannot be null

    /**
     * The y-coordinate of the location. Cannot be null.
     */
    private Long y; // The field cannot be null

    /**
     * The z-coordinate of the location. Cannot be null.
     */
    private Integer z;

    public Location() {
    }

    /**
     * Sets the location's x and y and z.
     *
     * @param x the x-coordinate of the location.
     * @param y the y-coordinate of the location.
     * @param z the z-coordinate of the location.
     */
    public Location(Float x, long y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the x-coordinate of the location.
     *
     * @return the x-coordinate of the location
     */
    public Float getX() {
        return x;
    }

    /**
     * Set the x-coordinate of the location
     *
     * @param x the x-coordinate of the location (must not be null)
     */
    public void setX(Float x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the location.
     *
     * @return the y-coordinate of the location
     */
    public Long getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the location
     *
     * @param y the y-coordinate of the location (must not be null)
     */
    public void set(Long y) {
        this.y = y;
    }

    /**
     * Returns the z-coordinate of the location.
     *
     * @return the z-coordinate of the location
     */
    public Integer getZ() {
        return z;
    }

    /**
     * Sets the z-coordinate of the location
     *
     * @param z the z-location of the location
     */
    public void setZ(Integer z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (z != location.z) return false;
        if (!Objects.equals(x, location.x)) return false;
        return Objects.equals(y, location.y);
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        result = (int) (31 * result + z);
        return result;
    }

    /**
     * Returns a string representation of the location.
     *
     * @return a string representation of the location
     */
    @Override
    public String toString() {
        return "model.Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
