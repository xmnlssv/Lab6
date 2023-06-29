package model;

import utils.ZonedDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Represents a lab work
 *
 * @author N
 * @see CollectionWorker
 * @since 1.8
 */

@XmlRootElement(name = "labWork")
public class LabWork implements Serializable, Comparable<LabWork> {
    /**
     * The unique identifier for the lab work. Must be greater than 0 and generated automatically and cannot be null.
     */
    private Integer id; // not null, unique, auto-generated, greater then 0

    /**
     * The name of the lab work. Cannot be null or an empty string.
     */
    private String name; // not null, not empty

    /**
     * The coordinates of the lab work's location. Cannot be null.
     */
    private Coordinates coordinates; // not null

    /**
     * The date and time when the lab work was created. Cannot be null and generated automatically.
     */
    private ZonedDateTime creationDate; // not null, auto-generated

    /**
     * The minimal point of the lab work. Must be greater than 0 and cannot be null.
     */
    private double minimalPoint; // greater then 0

    /**
     * The difficulty of the lab work. Cannot be null.
     */
    private Difficulty difficulty; // Field cannot be null

    /**
     * The author of the lab work. Cannot be null.
     */
    private Person author; // Field cannot be null

    public LabWork() {
    }

    public LabWork(Integer id, String name, Coordinates coordinates, ZonedDateTime creationDate, double minimalPoint,
                   Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.difficulty = difficulty;
        this.author = author;
    }

    @XmlElement
    /**
     * Returns the id of the lab work.
     *
     * @return The id of the lab work.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id of the lab work.
     *
     * @param id The id of the lab work.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    /**
     * Returns the name of the lab work.
     *
     * @return The name of the lab work.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the lab work.
     *
     * @param name The name of the lab work.
     */
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    /**
     * Returns the coordinates of the lab work's location.
     *
     * @return The coordinates of the lab work's location.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates of the lab work's location.
     *
     * @param coordinates The coordinates of the lab work's location.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @XmlElement
    /**
     * Returns the creation date of the lab work.
     *
     * @return The creation date of the lab work.
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    /**
     * Sets the creation date of the lab work.
     *
     * @param creationDate The creation date of the lab work.
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @XmlElement
    /**
     * Returns the minimal point of the lab work.
     *
     * @return The creation date of the lab work.
     */
    public double getMinimalPoint() {
        return minimalPoint;
    }

    /**
     * Sets the minimal point of the lab work.
     *
     * @param minimalPoint The minimal point of the lab work.
     */
    public void setMinimalPoint(double minimalPoint) {
        this.minimalPoint = minimalPoint;
    }

    @XmlElement
    /**
     * Returns the difficulty of the lab work.
     *
     * @return the difficulty of the lub work.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty of the lab work.
     *
     * @param difficulty the difficulty of the lab work.
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @XmlElement
    /**
     * Returns the Author of the lab work.
     *
     * @return The author of the lab work.
     */
    public Person getAuthor() {
        return author;
    }

    /**
     * Sets the Author of the lab work.
     *
     * @param author the author of the lab work.
     */
    public void setAuthor(Person author) {
        this.author = author;
    }

    public LabWork withId(Integer id) {
        return new LabWork(
                id,
                name,
                coordinates,
                creationDate,
                minimalPoint,
                difficulty,
                author
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabWork labWork = (LabWork) o;

        if (id != labWork.id) return false;
        if (minimalPoint != labWork.minimalPoint) return false;
        if (!Objects.equals(name, labWork.name)) return false;
        if (!Objects.equals(coordinates, labWork.coordinates)) return false;
        if (!Objects.equals(creationDate, labWork.creationDate)) return false;
        if (difficulty != labWork.difficulty) return false;
        return Objects.equals(author, labWork.author);
    }

    @Override
    public int hashCode() {
        long result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (int) (minimalPoint * (minimalPoint));
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return (int) result;
    }

    /**
     * Returns a string representation of the lab work.
     *
     * @return a string representation of the lab work.
     */
    @Override
    public String toString() {
        return "model.LabWork{" + "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", minimalPoint=" + minimalPoint +
                ", difficulty=" + difficulty +
                ", author=" + author +
                '}';
    }

    @Override
    public int compareTo(LabWork o) {
            return this.getAuthor().compareTo(o.getAuthor());

    }

    public static class LabWorkBuilder {
        // Not null, unique, auto-generated, greater than 0
        private Integer id;
        // Not null, not empty
        private String name;
        // Not null
        private Coordinates coordinates;
        // Not null, auto-generated
        private ZonedDateTime creationDate;
        // Greater than 0
        private double minimalPoint;
        private Difficulty difficulty;
        private Person author;

        public LabWorkBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public LabWorkBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LabWorkBuilder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public LabWorkBuilder creationDate() {
            this.creationDate = ZonedDateTime.now();
            return this;
        }

        public LabWorkBuilder minimalPoint(Double minimalPointBuilder) {
            this.minimalPoint = minimalPointBuilder;
            return this;
        }

        public LabWorkBuilder difficulty(Difficulty difficulty) {
            this.difficulty = difficulty;
            return this;
        }
        public LabWorkBuilder author(Person author) {
            this.author = author;
            return this;
        }
        public LabWork build() {
            return new LabWork(this);
        }
    }

    public LabWork(LabWorkBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.coordinates = builder.coordinates;
        this.creationDate = builder.creationDate;
        this.minimalPoint = builder.minimalPoint;
        this.difficulty = builder.difficulty;
        this.author = builder.author;
    }
}

