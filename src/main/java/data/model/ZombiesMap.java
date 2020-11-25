package data.model;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class ZombiesMap extends Data {

    private static final Color ZOMBIES_MAP_COLOR = new Color(52, 90, 104);

    public ZombiesMap() {
        // Needed for deserialization
    }

    @Override
    public EmbedBuilder createEmbedMessage() {
        return super.createEmbedMessage()
                .setImage(iconURL)              // imageUrl stored in super.iconUrl
                .setThumbnail(null)
                .setColor(ZOMBIES_MAP_COLOR)
                .setFooter("Map");
    }

}
