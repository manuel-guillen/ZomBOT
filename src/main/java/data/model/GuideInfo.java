package data.model;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Collections;

public class GuideInfo extends Data {

    private static final Color INFO_COLOR = new Color(66, 92, 105);

    private String id_str;
    private String footer;

    public GuideInfo() {
        // Needed for deserialization
    }

    public GuideInfo(String id_str, String name, String description, String imageURL, String footer) {
        super(name, description, imageURL, Collections.EMPTY_SET);
        this.id_str = id_str;
        this.footer = footer;
    }

    public String getId_str() {
        return id_str;
    }

    public String getFooter() {
        return footer;
    }

    @Override
    public EmbedBuilder createEmbedMessage() {
        return super.createEmbedMessage()
                .setThumbnail(null)
                .setImage(nullifyIfInvalidURL(iconURL))
                .setFooter(footer);
    }

}
