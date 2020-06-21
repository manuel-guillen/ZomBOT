package data.model;

import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class Gobblegum {

    public enum Type {
        Default, Normal, Whimsical, Mega, RareMega, UltraRareMega
    }

    public enum Color {
        Blue, Orange, Green, Purple
    }

    public static final Map<Color, java.awt.Color> GOBBLEGUM_COLOR_MAP = Map.ofEntries(
            entry(Gobblegum.Color.Blue, new java.awt.Color(85, 185, 230)),
            entry(Gobblegum.Color.Green, new java.awt.Color(75, 215, 75)),
            entry(Gobblegum.Color.Purple, new java.awt.Color(155, 90, 190)),
            entry(Gobblegum.Color.Orange, new java.awt.Color(255, 160, 75)));

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

    public java.awt.Color getTrueColor() {
        return GOBBLEGUM_COLOR_MAP.get(color);
    }
}
