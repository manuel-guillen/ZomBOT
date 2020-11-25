package listener;

import data.model.Data;
import data.sources.*;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ZomBOTListener extends ListenerAdapter {

    public static final String PREFIX = "z/";

    private static final List<Set<Data>> SOURCES = List.of(
            GobblegumDataSource.getInstance().getDataSet(),
            PerkAColaDataSource.getInstance().getDataSet(),
            PowerUpDataSource.getInstance().getDataSet(),
            ZombiesMapDataSource.getInstance().getDataSet(),
            GuideInfoSource.getInstance().getDataSet());

    private static final Set<Data> DATABASE = SOURCES.stream().collect(HashSet::new, HashSet::addAll, HashSet::addAll);

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.startsWith(PREFIX)) {
            String command = message.substring(PREFIX.length()).trim().toLowerCase().replaceAll(Data.IGNORE_REGEX, "");

            DATABASE.stream().filter(d -> d.matchesAlias(command)).forEach(d -> d.sendAsMessageToChannel(event.getChannel()));
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
                if (embed.isEmpty()) return;

                MessageEmbed em = embed.get();
                String name = em.getTitle(),
                        description = em.getDescription();

                Optional<Data> dataPoint = DATABASE.stream().filter(d -> d.matchesDescriptors(name, description)).findFirst();
                if (dataPoint.isEmpty()) return;

                String responseId = dataPoint.get().reactResponseStrId(event.getReactionEmote().getName());

                DATABASE.stream().filter(d -> d.getId_str().equals(responseId)).forEach(d -> {
                    d.sendAsMessageToChannel(channel);
                    event.getReaction().removeReaction(user).queue();
                });
            });
        }
    }

}
