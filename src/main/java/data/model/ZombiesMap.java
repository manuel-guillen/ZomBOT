package data.model;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.util.Set;

public class ZombiesMap extends Data {

    private static final Color ZOMBIES_MAP_COLOR = new Color(66, 92, 105);

    // TODO: Add Easter Egg field

    public ZombiesMap() {
        // Needed for deserialization
    }

    public ZombiesMap(String name, String description, String imageURL, Set<String> aliases) {
        super(name, description, imageURL, aliases);
    }

    @Override
    public EmbedBuilder createEmbedMessage() {
        return super.createEmbedMessage()
                .setImage(iconURL)              // imageUrl stored in super.iconUrl
                .setThumbnail(null)
                .setColor(ZOMBIES_MAP_COLOR)
                .setFooter("Map");
    }

    @Override
    protected void messageSentCallback(Message m) {
        m.addReaction(RADIO).queue();
        m.addReaction(MUSIC_NOTE).queue();
    }
}
