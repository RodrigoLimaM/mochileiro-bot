package br.com.mochileirobot.util;

import br.com.mochileirobot.model.enums.Commands;

public class MessageUtils {

    private static final char COMMAND_PREFIX = '!';
    public static final String ARGS_SEPARATOR = "-";

    public boolean isCommandMessage(String message, String ...args) {
        //TODO make null safe
        return message.charAt(0) == COMMAND_PREFIX
                && isValidCommandType(message, args);
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
