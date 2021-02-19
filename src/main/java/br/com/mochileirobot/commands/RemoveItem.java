package br.com.mochileirobot.commands;

import br.com.mochileirobot.config.NotEnoughItemsException;
import br.com.mochileirobot.service.PlayerService;
import br.com.mochileirobot.util.MessageUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveItem extends ListenerAdapter {

    private MessageUtils messageUtils = new MessageUtils();

    @Autowired
    PlayerService playerService;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();

        if (!event.getAuthor().isBot()
                && messageUtils.isCommandMessage(message, "REMOVEITEM", "REMOVERITEM")) {

            String[] args = messageUtils.getRemoveItemsArgs(message);

            if (args.length != 3) {
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

            try {
                playerService.removeItem(playerName, item, quantity);
                channel.sendMessage("Removido :school_satchel:").queue();
            } catch (NotEnoughItemsException ex) {
                channel.sendMessage("Quantidade insuficiente :frowning2:").queue();
            }

        }
    }
}
