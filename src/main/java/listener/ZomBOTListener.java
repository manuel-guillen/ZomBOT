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
import java.util.stream.Collectors;

public class ZomBOTListener extends ListenerAdapter {

    public static final String PREFIX = "z/";
    private static final List<Set<Data>> LOOKUP_SOURCES = List.of(
            GobblegumDataSource.getInstance().getDataSet(),
            PerkAColaDataSource.getInstance().getDataSet(),
            PowerUpDataSource.getInstance().getDataSet(),
            ZombiesMapDataSource.getInstance().getDataSet());
    private static final Set<GuideInfo> GUIDE_SOURCE =
            GuideInfoSource.getInstance().getDataSet()
                    .stream().map(d -> (GuideInfo)d).collect(Collectors.toSet());

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
                String footer = em.getFooter().getText();

                String responseIdStr;
                if (footer.equals("Map"))
                    responseIdStr = mapReactionResponseId(em.getTitle(), event.getReaction().getReactionEmote().getName());
                else if (footer.contains(">"))
                    responseIdStr = guideReactionResponseId(footer, event.getReaction().getReactionEmote().getName());
                else
                    responseIdStr = null;

                if (responseIdStr == null)
                    return;

                GUIDE_SOURCE.stream().filter(d -> d.getId_str().startsWith(responseIdStr)).sorted().limit(1).forEach(d -> {
                    d.sendAsMessageToChannel(channel);
                    event.getReaction().removeReaction(user).queue();
                });
            });
        }
    }

    private static String mapReactionResponseId(String mapName, String reaction) {
        String mapId = mapName.replaceAll("\"", "")
                              .replaceAll(" ", "-")
                              .toLowerCase();
        switch(reaction) {
            case Data.RADIO_REACTION:       return mapId + "-radio";
            case Data.MUSIC_NOTE_REACTION:  return mapId + "-music";
            default:                        return null;
        }
    }

    private static String guideReactionResponseId(String guideFooter, String reaction) {
        if (!reaction.equals(Data.NEXT_REACTION)) return null;

        String id_str = GUIDE_SOURCE.stream().filter(d -> d.getFooter().equals(guideFooter)).findFirst().get().getId_str();
        int endIndex = id_str.lastIndexOf("-") + 1;
        int step = Integer.parseInt(id_str.substring(endIndex));

        return id_str.substring(0,endIndex) + (step+1);
    }

}
