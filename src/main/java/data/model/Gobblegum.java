package data.model;

import java.util.Optional;

public class Gobblegum {

    public enum Type {
        Classic, Whimsical, Mega, RareMega, UltraRareMega
    }

    public enum Color {
        Blue, Orange, Green, Purple
    }

    private String name;
    private Type type;
    private Color color;
    private String description;
    private String imageURL;
    private String unlockCriteria;

}
