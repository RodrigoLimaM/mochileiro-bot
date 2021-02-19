package br.com.mochileirobot.commands;

import br.com.mochileirobot.service.PlayerService;
import br.com.mochileirobot.util.MessageUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddItem extends ListenerAdapter {

    MessageUtils messageUtils = new MessageUtils();

    @Autowired
    PlayerService playerService;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();

        if(!event.getAuthor().isBot()
                && messageUtils.isCommandMessage(message, "ADDITEM", "ADICIONARITEM")) {

            String[] args = messageUtils.getAddItemsArgs(message);

            if(args.length != 3) {
                channel.sendMessage("Algo deu errado :frowning2:").queue();
                return;
            }

            String playerName = null;
            String item = null;
            int quantity = 0;

            try {
                playerName = args[0].trim().toUpperCase();
                item = args[1].trim().toUpperCase();
                quantity = Integer.parseInt(args[2].trim());
            } catch (NumberFormatException ex) {
                channel.sendMessage("Algo deu errado :frowning2:").queue();
            }

            playerService.addItem(playerName, item, quantity);

            channel.sendMessage("Adicionado :school_satchel:").queue();
        }

    }
}
