package listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

public interface Messageable {

    public EmbedBuilder createPrebuiltEmbedMessage();

    public default void sendAsMessageToChannel(MessageChannel channel) {
        channel.sendMessage(createPrebuiltEmbedMessage().build()).queue();
    }

}
