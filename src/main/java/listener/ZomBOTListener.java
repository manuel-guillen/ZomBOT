package listener;

import data.model.Data;
import data.model.GuideInfo;
import data.sources.*;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ZomBOTListener extends ListenerAdapter {

    public static final String PREFIX = "z/";
    private static final List<Set<Data>> LOOKUP_SOURCES = List.of(
            GobblegumDataSource.getInstance().getDataSet(),
            PerkAColaDataSource.getInstance().getDataSet(),
            PowerUpDataSource.getInstance().getDataSet(),
            ZombiesMapDataSource.getInstance().getDataSet());
    private static final Set<Data> GUIDE_SOURCE =
            GuideInfoSource.getInstance().getDataSet();

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.startsWith(PREFIX)) {
            String command = message.substring(PREFIX.length()).trim().toLowerCase().replaceAll(Data.IGNORE_REGEX, "");

            for (Set<Data> source : LOOKUP_SOURCES)
                source.stream().filter(d -> d.matchesAlias(command)).forEach(d -> d.sendAsMessageToChannel(event.getChannel()));
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        User user = event.getUser(),
             bot = event.getJDA().getSelfUser();

        if (!user.equals(bot)) {
            TextChannel channel = event.getChannel();
            channel.retrieveMessageById(event.getMessageId()).queue(message -> {
                User author = message.getAuthor();
                if (!author.equals(bot)) return;

                Optional<MessageEmbed> embed = message.getEmbeds().stream().findFirst();
                if (!embed.isPresent()) return;

                MessageEmbed em = embed.get();
                if (!em.getFooter().getText().equals("Map")) return;

                String responseIdStr = mapToReactResponse(em.getTitle(), event.getReaction().getReactionEmote().getName());

                GUIDE_SOURCE.stream().filter(d -> ((GuideInfo)d).getId_str().equals(responseIdStr)).forEach(d -> {
                    d.sendAsMessageToChannel(channel);
                    event.getReaction().removeReaction(user).queue();
                });
            });
        }
    }

    private static String mapToReactResponse(String mapName, String reaction) {
        String mapId = mapName.replaceAll("\"", "")
                                    .replaceAll(" ", "-")
                                    .toLowerCase();
        switch(reaction) {
            case Data.RADIO_REACTION:       return mapId + "-radio";
            case Data.MUSIC_NOTE_REACTION:  return mapId + "-music";
            default:                        return null;
        }
    }

}
