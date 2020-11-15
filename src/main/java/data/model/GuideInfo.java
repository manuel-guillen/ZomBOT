package data.model;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Collections;
import java.util.Set;

public class GuideInfo extends Data {

    private static final Color INFO_COLOR = new Color(66, 92, 105);

    private String id_str;

    public GuideInfo() {
        // Needed for deserialization
    }

    public GuideInfo(String id_str, String name, String description, String imageURL, Set<String> aliases) {
        super(name, description, imageURL, Collections.EMPTY_SET);
        this.id_str = id_str;
    }

    public String getId_str() {
        return id_str;
    }

    @Override
    public EmbedBuilder createEmbedMessage() {
        // TODO: Implement embed message creation.
        return null;
    }

}
