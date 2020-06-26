package listener;

import data.model.Data;
import data.sources.PerkAColaDataSource;
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
            Data g = PerkAColaDataSource.getData().get(message.replaceAll("[^A-Za-z0-9 ]", ""));
            if (g != null)
                g.sendAsMessageToChannel(event.getChannel());
        }
    }
}
