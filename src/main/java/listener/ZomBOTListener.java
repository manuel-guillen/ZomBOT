package listener;

import data.generator.GobblegumDataGenerator;
import data.model.Gobblegum;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ZomBOTListener extends ListenerAdapter {

    public static final String PREFIX = "z/";

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.startsWith(PREFIX)) {
            message = message.substring(PREFIX.length()).trim().toLowerCase();

            Gobblegum g = GobblegumDataGenerator.GOBBLEGUM_MAP.get(message.replaceAll("[^A-Za-z0-9 ]", ""));
            if (g != null) {
                TextChannel channel = event.getChannel();
                EmbedBuilder eb = new EmbedBuilder();
                eb.setThumbnail(g.getImageURL());
                eb.setTitle(g.getName());
                eb.setColor(g.getTrueColor());
                eb.addField("Type",g.getType().toString(),true);
                eb.addField("Activation",g.getActivation(),true);
                eb.setDescription(g.getDescription());

                channel.sendMessage(eb.build()).queue();
            }
        }
    }
}
