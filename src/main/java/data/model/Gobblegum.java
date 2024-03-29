package data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
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

    public Gobblegum(String name, Color color, Type type, String activation, String description, String iconURL, Set<String> aliases) {
        super(name, description, iconURL, aliases);
        this.type = type;
        this.color = color;
        this.activation = activation;
    }

    @Override
    public String toString() {
        return name + " - [" + type + "] (" + activation + ") " + description;
    }

    @Override
    public EmbedBuilder createEmbedMessage() {
        return super.createEmbedMessage()
                .setColor(GOBBLEGUM_COLOR_MAP.get(color))
                .addField("Type", fixEnumString(type.toString()),true)
                .addField("Activation", activation,true);
    }
}
