package br.com.mochileirobot.config;

public class NonExistentItemException extends RuntimeException {

    public NonExistentItemException() {
        super("Not existent item");
    }
}
