package data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class GuideInfo extends Data {

    private static final Color INFO_COLOR = new Color(3, 33, 55);

    private String footer;

    public GuideInfo() {
        // Needed for deserialization
    }

    @Override
    public boolean matchesAlias(String alias) {
        return false;       // Guide info is not alias-searchable.
    }

    @Override
    public EmbedBuilder createEmbedMessage() {
        return super.createEmbedMessage()
                .setThumbnail(null)
                .setImage(nullifyIfInvalidURL(iconURL))
                .setFooter(footer)
                .setColor(INFO_COLOR);
    }

}
