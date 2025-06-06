package com.eurobank.models.excepciones;

public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException(String idCliente) {
        super("Cliente con ID " + idCliente + " no registrado");
    }
}