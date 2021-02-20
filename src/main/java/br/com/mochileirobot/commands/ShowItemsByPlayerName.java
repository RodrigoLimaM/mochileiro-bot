package br.com.mochileirobot.commands;

import br.com.mochileirobot.model.Player.Item;
import br.com.mochileirobot.model.enums.Commands;
import br.com.mochileirobot.service.PlayerService;
import br.com.mochileirobot.util.MessageUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowItemsByPlayerName extends ListenerAdapter {

    private MessageUtils messageUtils = new MessageUtils();

    @Autowired
    PlayerService playerService;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();

        if (!event.getAuthor().isBot()
                && messageUtils.isCommandMessage(message, Commands.PLAYERITEMS.name(), Commands.JOGADORITENS.name())) {

            String[] args = messageUtils.getShowItemsByNameArgs(message);

            if (args.length != 1) {
                channel.sendMessage("Algo deu errado :frowning2:").queue();
                return;
            }

            String playerName = args[0].trim().toUpperCase();

            List<Item> items = playerService.getItemsByName(playerName);

            if(items == null) {
                channel.sendMessage("Player não existe :frowning2:").queue();
                return;
            }

            channel.sendMessage(messageUtils.buildEmbedPlayerItems(items, playerName)).queue();
        }
    }
}
