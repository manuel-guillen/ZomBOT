package data.model;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.util.Map;
import java.util.Set;

public class ZombiesMap extends Data {

    private static final Color ZOMBIES_MAP_COLOR = new Color(52, 90, 104);
    private static final Map<String, Set<String>> MAPS_TO_REACT = Map.of(
            RADIO_REACTION, Set.of("nacht der untoten"),
            MUSIC_NOTE_REACTION, Set.of("nacht der untoten")
    );

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

    @Override
    protected void messageSentCallback(Message m) {
        MAPS_TO_REACT.forEach((react, maps) -> {
            if (maps.contains(name.toLowerCase()))
                m.addReaction(react).queue();
        });
    }
}
