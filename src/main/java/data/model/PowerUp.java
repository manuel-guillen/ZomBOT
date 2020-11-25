package data.model;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.Color;

public class PowerUp extends Data {

    private static final Color POWER_UP_COLOR = new Color(115, 166, 88);

    public PowerUp() {
        // Needed for deserialization
    }

    @Override
    public EmbedBuilder createEmbedMessage() {
        return super.createEmbedMessage()
                .setColor(POWER_UP_COLOR)
                .setFooter("Power-Up");
    }

}
