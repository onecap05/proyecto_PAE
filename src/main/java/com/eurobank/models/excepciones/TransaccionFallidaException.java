package com.eurobank.models.excepciones;

public class TransaccionFallidaException extends RuntimeException {

    public TransaccionFallidaException(String message) {
        super(message);
    }
}
