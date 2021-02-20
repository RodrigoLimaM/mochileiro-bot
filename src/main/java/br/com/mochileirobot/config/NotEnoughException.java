package br.com.mochileirobot.config;

public class NotEnoughException extends RuntimeException {

    public NotEnoughException() {
        super("Not enough items");
    }
}
