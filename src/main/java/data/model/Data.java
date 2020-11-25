package data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public abstract class Data {

    public static final String IGNORE_REGEX = "[^ .\\w-]";

    public static final Map<String,String> REACTS = Map.of(
            "radio", "\uD83D\uDCFB",
            "music", "\uD83C\uDFB5",
            "barrel", "\uD83D\uDEE2",
            "next", "\u23E9"
    );

    protected String name;
    protected String description;
    protected String iconURL;
    protected Set<String> aliases;

    protected String id_str;
    protected Map<String,String> linkMap = Collections.emptyMap();

    public Data() {
        // Needed for deserialization
    }

    protected Data(String name, String description, String iconURL, Set<String> aliases) {
        this.name = name;
        this.description = description;
        this.iconURL = iconURL;
        this.aliases = new HashSet<>(aliases);
        this.linkMap = Collections.emptyMap();
    }

    public String getId_str() {
        return id_str != null ? id_str : "";
    }

    public String reactResponseStrId(String react) {
        return REACTS.entrySet().stream().filter(e -> e.getValue().equals(react)).map(Map.Entry::getKey).map(key -> linkMap.getOrDefault(key,"")).findFirst().orElse("");
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

    public boolean matchesDescriptors(String name, String description) {
        return this.name.equals(name) && this.description.equals(description);
    }

    protected static String nullifyIfInvalidURL(String url) {
        return (url != null && EmbedBuilder.URL_PATTERN.matcher(url).matches()) ? url : null;
    }

    protected static String fixEnumString(String enumStr) {
        return enumStr.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public EmbedBuilder createEmbedMessage() {
        return new EmbedBuilder()
                .setTitle(name)
                .setThumbnail(nullifyIfInvalidURL(iconURL))
                .setDescription(description)
                .setFooter(getClass().getSimpleName());
    }

    public void sendAsMessageToChannel(MessageChannel channel) {
        if (iconURL == null || iconURL.isEmpty() || iconURL.startsWith("http")) {
            channel.sendMessage(createEmbedMessage().build()).queue(this::messageSentCallback);
        } else {
            EmbedBuilder eb = createEmbedMessage();
            try {
                File f = new File(this.getClass().getResource(iconURL).toURI());
                if (this instanceof GuideInfo) {
                    eb.setImage("attachment://" + f.getName());
                } else {
                    eb.setThumbnail("attachment://" + f.getName());
                }
                channel.sendFile(f, f.getName()).embed(eb.build()).queue(this::messageSentCallback);
            } catch (URISyntaxException e) {
                eb.setThumbnail(null);
                channel.sendMessage(eb.build()).queue(this::messageSentCallback);
            }
        }
    }

    protected void messageSentCallback(Message m) {
        linkMap.keySet().stream()
                .map(reactName -> REACTS.getOrDefault(reactName,""))
                .filter(react -> !react.isEmpty())
                .forEach(react -> m.addReaction(react).queue());
    }

}
