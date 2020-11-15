package data.model;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Set;

public class ZombiesMap extends Data {

    private static final Color ZOMBIES_MAP_COLOR = new Color(66, 92, 105);

    // TODO: Add Easter Egg field

    public ZombiesMap() {
        // Needed for deserialization
    }

    public ZombiesMap(String name, String description, String iconURL, Set<String> aliases) {
        super(name, description, iconURL, aliases);
    }

    @Override
    public EmbedBuilder createPrebuiltEmbedMessage() {
        return super.createPrebuiltEmbedMessage()
                .setImage(iconURL)
                .setThumbnail(null)
                .setColor(ZOMBIES_MAP_COLOR)
                .setFooter("Map");
    }
}
