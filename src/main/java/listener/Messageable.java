package listener;

import net.dv8tion.jda.api.entities.MessageChannel;

public interface Messageable {

    public void sendAsMessageToChannel(MessageChannel channel);

}
