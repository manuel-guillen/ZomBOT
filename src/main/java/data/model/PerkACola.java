package data.model;

import net.dv8tion.jda.api.entities.MessageChannel;

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

    }
}
