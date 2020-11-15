package data.model;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

public class PerkACola extends Data {

    private static final Map<String, Color> PERK_COLOR_MAP = Map.ofEntries(
            entry("juggernog", new java.awt.Color(139, 23, 23)),
            entry("quick revive", new java.awt.Color(65, 153, 165)),
            entry("speed cola", new java.awt.Color(5, 141, 39)),
            entry("stamin-up", new java.awt.Color(231, 103, 32)),
            entry("deadshot daiquiri", new java.awt.Color(81, 81, 73)),
            entry("mule kick", new java.awt.Color(44, 102, 26)),
            entry("widows wine", new java.awt.Color(50, 40, 40)),
            entry("double tap root beer 1.0", new java.awt.Color(210, 174, 35)),
            entry("double tap root beer 2.0", new java.awt.Color(210, 174, 35)),
            entry("ph.d flopper", new java.awt.Color(100, 73, 120))
    );

    private int cost;

    public PerkACola() {
        // Needed for deserialization
    }

    public PerkACola(String name, int cost, String description, String iconURL, Set<String> aliases) {
        super(name, description, iconURL, aliases);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public EmbedBuilder createEmbedMessage() {
        return super.createEmbedMessage()
                .setColor(PERK_COLOR_MAP.get(getSimplifiedName()))
                .addField("Cost", Integer.toString(cost), true)
                .setFooter("Perk-a-Cola");
    }

}
