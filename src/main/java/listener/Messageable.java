package listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

public interface Messageable {

    EmbedBuilder createEmbedMessage();

    default void sendAsMessageToChannel(MessageChannel channel) {
        channel.sendMessage(createEmbedMessage().build()).queue();
    }

}
