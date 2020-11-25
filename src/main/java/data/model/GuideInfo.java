package data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class GuideInfo extends Data implements Comparable<GuideInfo> {

    private static final Color INFO_COLOR = new Color(3, 33, 55);

    private String id_str;
    private String footer;
    private boolean hasNext;

    public GuideInfo() {
        // Needed for deserialization
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
                .setFooter(footer)
                .setColor(INFO_COLOR);
    }

    @Override
    protected void messageSentCallback(Message m) {
        if (hasNext) m.addReaction(NEXT_REACTION).queue();
    }

    @Override
    public int compareTo(@NotNull GuideInfo that) {
        int index1 = this.id_str.length();
        while (index1 > 0 && Character.isDigit(this.id_str.charAt(index1-1))) index1--;

        int index2 = that.id_str.length();
        while (index2 > 0 && Character.isDigit(that.id_str.charAt(index2-1))) index2--;

        String s1 = this.id_str.substring(0,index1),
               s2 = that.id_str.substring(0,index2);

        if (s1.equals(s2))
            return Integer.parseInt(this.id_str.substring(index1)) - Integer.parseInt(that.id_str.substring(index2));
        else
            return s1.compareTo(s2);
    }
}
