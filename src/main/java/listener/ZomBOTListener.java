package listener;

import data.model.Data;
import data.sources.GobblegumDataSource;
import data.sources.PerkAColaDataSource;
import data.sources.PowerUpDataSource;
import data.sources.ZombiesMapDataSource;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public class ZomBOTListener extends ListenerAdapter {

    public static final String PREFIX = "z/";
    private static final List<Set<Data>> SOURCES =  List.of(
            GobblegumDataSource.getInstance().getDataSet(),
            PerkAColaDataSource.getInstance().getDataSet(),
            PowerUpDataSource.getInstance().getDataSet(),
            ZombiesMapDataSource.getInstance().getDataSet());

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.startsWith(PREFIX)) {
            String command = message.substring(PREFIX.length()).trim().toLowerCase().replaceAll(Data.IGNORE_REGEX, "");

            for (Set<Data> source : SOURCES)
                source.stream().filter(d -> d.matchesAlias(command)).forEach(d -> d.sendAsMessageToChannel(event.getChannel()));
        }
    }

}
