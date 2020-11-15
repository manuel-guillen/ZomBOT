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

    public ZombiesMap(String name, String description, String iconURL, Set<String> aliases) {
        super(name, description, iconURL, aliases);
    }

    @Override
    public EmbedBuilder createEmbedMessage() {
        return super.createEmbedMessage()
                .setImage(iconURL)
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
