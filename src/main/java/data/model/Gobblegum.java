package data.model;

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
}
