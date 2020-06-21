package data.model;

import java.util.Objects;

public class Gobblegum {

    public enum Type {
        Default, Normal, Whimsical, Mega, RareMega, UltraRareMega
    }

    public enum Color {
        Blue, Orange, Green, Purple
    }

    private String name;
    private Color color;
    private Type type;
    private String activation;
    private String description;
    private String imageURL;

    public Gobblegum(String name, Color color, Type type, String activation, String description, String imageURL) {
        this.name = name;
        this.color = color;
        this.type = type;
        this.activation = activation;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public String getActivation() {
        return activation;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gobblegum gobblegum = (Gobblegum) o;
        return name.equals(gobblegum.name) &&
                color == gobblegum.color &&
                type == gobblegum.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, type);
    }

    @Override
    public String toString() {
        return name + " - [" + type + "] (" + activation + ") " + description;
    }
}
