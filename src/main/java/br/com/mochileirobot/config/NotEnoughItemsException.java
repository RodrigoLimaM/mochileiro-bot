package br.com.mochileirobot.config;

public class NotEnoughItemsException extends RuntimeException {

    public NotEnoughItemsException() {
        super("Not enough items");
    }
}
