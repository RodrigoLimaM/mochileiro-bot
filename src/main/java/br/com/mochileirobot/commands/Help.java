package br.com.mochileirobot.commands;

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

import java.awt.*;

@Service
public class Help extends ListenerAdapter {

    private MessageUtils messageUtils = new MessageUtils();

    @Autowired
    PlayerService playerService;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();

        if (!event.getAuthor().isBot()
                && messageUtils.isCommandMessage(message, Commands.HELP.name(), Commands.AJUDA.name())) {

            MessageEmbed messageEmbed = new EmbedBuilder()
                    .setColor(new Color(0x8b008b))
                    .setDescription(
                    "**!additem, !adicionaritem** ex: player1-espada-5 - Adiciona item para um player específico; \n"
                            +"**!removeitem, !removerritem** ex: player1-espada-5 - Remove item de um player específico; \n"
                            +"**!playeritems, !jogadoritens** ex: player1 - Exibe itens de um jogador especifico; \n"
                            +"**!items, !itens** - Exibe itens de todos os jogadores; \n"
                            +"**!addstats, !adicionarstats** ex: player1-carisma-20 - Adiciona atributos para um player específico; \n"
                            +"**!removestats, !removerstats** ex: player1-hp-30 - Remove atributos de um player específico; \n"
                            +"**!playerstats, !jogadorstats** ex: player1 - Exibe atributos de um player específico; \n"
                            +"**!stats, !stats** - Exibe atributos de todos os players. \n"
                    )
                    .setTitle(":robot: Comandos do Mochileiro :robot:")
                    .setFooter("https://github.com/RodrigoLimaM/mochileiro-bot", "https://bots.ondiscord.xyz/favicon/android-chrome-256x256.png")
                    .build();

            channel.sendMessage(messageEmbed).queue();
        }
    }
}
