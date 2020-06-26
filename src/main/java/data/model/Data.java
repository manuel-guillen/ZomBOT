package data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import listener.Messageable;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Objects;

public abstract class Data implements Messageable {

    protected String name;
    protected String description;
    protected String iconURL;

    public Data() {
        // Needed for deserialization
    }

    protected Data(String name, String description, String iconURL) {
        this.name = name;
        this.description = description;
        this.iconURL = iconURL;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIconURL() {
        return iconURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return name.equals(data.name) &&
                description.equals(data.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }

    @JsonIgnore
    public String getSimplifiedName() {
        return name.replace('-',' ')
                .replaceAll("[^ \\w]","")
                .toLowerCase();
    }

    protected static String nullifyIfInvalidURL(String url) {
        return EmbedBuilder.URL_PATTERN.matcher(url).matches() ? url : null;
    }

    @Override
    public EmbedBuilder createPrebuiltEmbedMessage() {
        return new EmbedBuilder()
                .setTitle(name)
                .setThumbnail(nullifyIfInvalidURL(iconURL))
                .setDescription(description)
                .setFooter(getClass().getSimpleName());
    }
}
