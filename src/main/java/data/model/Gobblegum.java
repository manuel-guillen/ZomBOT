package data.model;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.Map;

import static java.util.Map.entry;

public class Gobblegum extends Data {

    public enum Type {
        Default, Normal, Whimsical, Mega, RareMega, UltraRareMega
    }

    public enum Color {
        Blue, Orange, Green, Purple
    }

    private static final Map<Color, java.awt.Color> GOBBLEGUM_COLOR_MAP = Map.ofEntries(
            entry(Gobblegum.Color.Blue, new java.awt.Color(85, 185, 230)),
            entry(Gobblegum.Color.Green, new java.awt.Color(75, 215, 75)),
            entry(Gobblegum.Color.Purple, new java.awt.Color(155, 90, 190)),
            entry(Gobblegum.Color.Orange, new java.awt.Color(255, 160, 75)));

    private Type type;
    private Color color;
    private String activation;

    public Gobblegum() {
        // Needed for deserialization
    }

    public Gobblegum(String name, Color color, Type type, String activation, String description, String iconURL) {
        super(name, description, iconURL);
        this.type = type;
        this.color = color;
        this.activation = activation;
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public String getActivation() {
        return activation;
    }

    @Override
    public String toString() {
        return name + " - [" + type + "] (" + activation + ") " + description;
    }

    @Override
    public void sendAsMessageToChannel(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(name);
        eb.setThumbnail(iconURL);
        eb.setDescription(description);
        eb.setColor(GOBBLEGUM_COLOR_MAP.get(color));
        eb.addField("Type", type.toString(),true);
        eb.addField("Activation", activation,true);

        channel.sendMessage(eb.build()).queue();
    }
}
