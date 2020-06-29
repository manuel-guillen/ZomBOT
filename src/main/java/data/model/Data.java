package data.model;

import listener.Messageable;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Data implements Messageable {

    public static final String IGNORE_REGEX = "[^ .\\w-]";

    protected String name;
    protected String description;
    protected String iconURL;
    protected Set<String> aliases;

    public Data() {
        // Needed for deserialization
    }

    protected Data(String name, String description, String iconURL, Set<String> aliases) {
        this.name = name;
        this.description = description;
        this.iconURL = iconURL;
        this.aliases = new HashSet<>(aliases);
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

    public Set<String> getAliases() {
        return aliases;
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
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }

    protected String getSimplifiedName() {
        return name.replaceAll(IGNORE_REGEX,"").toLowerCase();
    }

    public boolean matchesAlias(String alias) {
        return alias.equals(getSimplifiedName()) || aliases.contains(alias);
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
