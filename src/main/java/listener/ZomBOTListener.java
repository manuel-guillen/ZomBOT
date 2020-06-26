package listener;

import data.model.Data;
import data.sources.GobblegumDataSource;
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
            String command = message.substring(PREFIX.length()).trim().toLowerCase().replaceAll("[^A-Za-z0-9 ]", "");

            Data g = GobblegumDataSource.getInstance().getDataMap().get(command);
            if (g != null) {
                g.sendAsMessageToChannel(event.getChannel());
                return;
            }

            Data p = PerkAColaDataSource.getInstance().getDataMap().get(command);
            if (p != null) {
                p.sendAsMessageToChannel(event.getChannel());
                return;
            }

        }
    }
}
