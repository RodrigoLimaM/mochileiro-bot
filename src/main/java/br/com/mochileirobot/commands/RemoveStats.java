package br.com.mochileirobot.commands;

import br.com.mochileirobot.model.Player;
import br.com.mochileirobot.model.enums.Commands;
import br.com.mochileirobot.service.PlayerService;
import br.com.mochileirobot.util.MessageUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveStats extends ListenerAdapter {

    MessageUtils messageUtils = new MessageUtils();

    @Autowired
    PlayerService playerService;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();

        if(!event.getAuthor().isBot()
                && messageUtils.isCommandMessage(message, Commands.REMOVESTATS.name(), Commands.REMOVERSTATS.name())) {

            String[] args = messageUtils.getRemoveStatsArgs(message);

            if(args.length != 3) {
                channel.sendMessage("Algo deu errado :frowning2:").queue();
                return;
            }

            String playerName = null;
            String stat = null;
            int value = 0;

            try {
                playerName = args[0].trim().toUpperCase();
                stat = args[1].trim().toUpperCase();
                value = Integer.parseInt(args[2].trim());

                if(!playerService.isValidAttribute(stat)) {
                    channel.sendMessage("Atributo não existente :frowning2:").queue();
                    return;
                }

                Player player = playerService.removeStat(playerName, stat, value);
                if (player != null) {
                    channel.sendMessage("Atributo diminuido de " + player.getName() + " :arrow_down:").queue();
                    channel.sendMessage(messageUtils.buildEmbedPlayerStats(player.getStats(), player.getName())).queue();

                } else {
                    channel.sendMessage("Player não existe :frowning2:").queue();
                }

            } catch (NumberFormatException ex) {
                channel.sendMessage("Algo deu errado :frowning2:").queue();
            }

        }
    }
}
