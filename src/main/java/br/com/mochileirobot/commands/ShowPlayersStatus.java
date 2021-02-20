package br.com.mochileirobot.commands;

import br.com.mochileirobot.model.Player;
import br.com.mochileirobot.model.enums.Commands;
import br.com.mochileirobot.service.PlayerService;
import br.com.mochileirobot.util.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowPlayersStatus extends ListenerAdapter {

    private MessageUtils messageUtils = new MessageUtils();

    @Autowired
    PlayerService playerService;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();

        if (!event.getAuthor().isBot()
                && messageUtils.isCommandMessage(message, Commands.STATS.name())) {

            List<Player> players = playerService.getPlayers();

            players.forEach(player -> {

                StringBuilder stringBuilder = new StringBuilder();
                player.getStats().forEach(stat -> {
                    stringBuilder.append("\n" +stat.getAttribute());
                    stringBuilder.append(" - " +stat.getValue());
                });

                MessageEmbed messageEmbed = new EmbedBuilder()
                        .setTitle(player.getName())
                        .setDescription(stringBuilder.toString())
                        .build();

                channel.sendMessage(messageEmbed).queue();

            });
        }
    }
}
