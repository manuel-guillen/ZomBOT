package data.model;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

import static java.util.Map.entry;

public class PerkACola extends Data {

    private static final Map<String, Color> PERK_COLOR_MAP = Map.ofEntries(
            entry("juggernog", new java.awt.Color(139, 23, 23)),
            entry("quick revive", new java.awt.Color(65, 153, 165)),
            entry("speed cola", new java.awt.Color(5, 141, 39)),
            entry("stamin up", new java.awt.Color(231, 103, 32)),
            entry("deadshot daiquiri", new java.awt.Color(81, 81, 73)),
            entry("mule kick", new java.awt.Color(44, 102, 26)),
            entry("widows wine", new java.awt.Color(50, 40, 40)));

    private int cost;

    public PerkACola() {
        // Needed for deserialization
    }

    public PerkACola(String name, int cost, String description, String iconURL) {
        super(name, description, iconURL);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public void sendAsMessageToChannel(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(name);
        eb.setDescription(description);
        eb.setColor(PERK_COLOR_MAP.get(getSimplifiedName()));
        eb.addField("Cost", Integer.toString(cost), true);

        try {
            File f = new File(this.getClass().getResource(iconURL).toURI());
            eb.setThumbnail("attachment://" + f.getName());
            channel.sendFile(f, f.getName()).embed(eb.build()).queue();
        } catch (URISyntaxException e) {
            channel.sendMessage(eb.build()).queue();
        }
    }
}
