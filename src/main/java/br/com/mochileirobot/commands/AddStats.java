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
public class AddStats extends ListenerAdapter {

    MessageUtils messageUtils = new MessageUtils();

    @Autowired
    PlayerService playerService;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();

        if(!event.getAuthor().isBot()
                && messageUtils.isCommandMessage(message, Commands.ADDSTATS.name(), Commands.ADICIONARSTATS.name())) {

            String[] args = messageUtils.getAddStatsArgs(message);

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

                String finalStat = stat;
                if(playerService.isValidAttribute(finalStat)) {
                    Player player = playerService.addStat(playerName, stat, value);
                    channel.sendMessage("Atributo evoluido de " +player.getName() +" :up:").queue();
                    channel.sendMessage(messageUtils.buildEmbedPlayerStats(player.getStats(), player.getName())).queue();
                } else {
                    channel.sendMessage("Atributo não existente :frowning2:").queue();
                }

            } catch (NumberFormatException ex) {
                channel.sendMessage("Algo deu errado :frowning2:").queue();
            }
        }
    }

}
