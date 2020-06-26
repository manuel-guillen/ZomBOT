package data.model;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;

public class PerkACola extends Data {

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
        eb.setColor(Color.BLACK);
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
