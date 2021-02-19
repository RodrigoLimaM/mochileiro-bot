package br.com.mochileirobot.util;

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
        return message.replaceAll("(?i)!additem ", "")
                .replaceAll("(?i)!adicionaritem ", "").split(ARGS_SEPARATOR);
    }

    public String[] getRemoveItemsArgs(String message) {
        return message.replaceAll("(?i)!removeitem ", "")
                .replaceAll("(?i)!removeritem ", "").split(ARGS_SEPARATOR);
    }

    public String[] getShowItemsByNameArgs(String message) {
        return message.replaceAll("(?i)!player ", "")
                .replaceAll("(?i)!jogador ", "").split(ARGS_SEPARATOR);
    }
}
