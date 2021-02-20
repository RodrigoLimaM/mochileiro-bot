package br.com.mochileirobot.util;

import br.com.mochileirobot.model.Player;
import br.com.mochileirobot.model.Player.Item;
import br.com.mochileirobot.model.Player.Stat;
import br.com.mochileirobot.model.enums.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class MessageUtils {

    private static final char COMMAND_PREFIX = '!';
    public static final String ARGS_SEPARATOR = "-";

    public boolean isCommandMessage(String message, String ...args) {
        //TODO make null safe
        return isValidCommandType(message, args);
    }

    private boolean isValidCommandType(String message, String[] args) {
        message = message +" ";
        for (String type : args){
            type = type +" ";
            if (message.toUpperCase().startsWith(COMMAND_PREFIX + type))
                return true;
        }
        return false;
    }

    public MessageEmbed buildEmbedPlayerItems(List<Item> items, String playerName) {
        StringBuilder stringBuilder = new StringBuilder();

        items.stream()
                .filter(item -> item.getQuantity() != 0)
                .forEach(item -> {
                    stringBuilder.append("\n" +item.getName());
                    stringBuilder.append(" - " +item.getQuantity());
                });

        return new EmbedBuilder()
                .setTitle(playerName)
                .setDescription(stringBuilder.toString())
                .build();
    }

    public MessageEmbed buildEmbedPlayerStats(List<Stat> stats, String playerName) {

        StringBuilder stringBuilder = new StringBuilder();

        stats.forEach(stat -> {
            stringBuilder.append("\n" +stat.getAttribute());
            stringBuilder.append(" - " +stat.getValue());
        });

        return new EmbedBuilder()
                .setTitle(playerName)
                .setDescription(stringBuilder.toString())
                .build();
    }

    public String[] getAddItemsArgs(String message) {
        return message.replaceAll("(?i)!" +Commands.ADDITEM.name() +" ", "")
                .replaceAll("(?i)!" +Commands.ADICIONARITEM.name() +" ", "").split(ARGS_SEPARATOR);
    }

    public String[] getRemoveItemsArgs(String message) {
        return message.replaceAll("(?i)!" +Commands.REMOVEITEM.name() +" ", "")
                .replaceAll("(?i)!" +Commands.REMOVERITEM.name() +" ", "").split(ARGS_SEPARATOR);
    }

    public String[] getShowItemsByNameArgs(String message) {
        return message.replaceAll("(?i)!" +Commands.PLAYERITEMS.name() +" ", "")
                .replaceAll("(?i)!" +Commands.JOGADORITENS.name() +" ", "").split(ARGS_SEPARATOR);
    }

    public String[] getAddStatsArgs(String message) {
        return message.replaceAll("(?i)!" +Commands.ADDSTATS.name() +" ", "")
                .replaceAll("(?i)!" +Commands.ADICIONARSTATS.name() +" ", "").split(ARGS_SEPARATOR);
    }

    public String[] getRemoveStatsArgs(String message) {
        return message.replaceAll("(?i)!" +Commands.REMOVESTATS.name() +" ", "")
                .replaceAll("(?i)!" +Commands.REMOVERSTATS.name() +" ", "").split(ARGS_SEPARATOR);
    }

    public String[] getShowStatsByNameArgs(String message) {
        return message.replaceAll("(?i)!" +Commands.PLAYERSTATS.name() +" ", "")
                .replaceAll("(?i)!" +Commands.JOGADORSTATS.name() +" ", "").split(ARGS_SEPARATOR);
    }
}
