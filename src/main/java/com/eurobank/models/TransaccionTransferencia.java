package com.eurobank.models;

import java.time.LocalDateTime;

public class TransaccionTransferencia extends Transaccion {
    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;

    public TransaccionTransferencia(String id, double monto, LocalDateTime fecha,
                                    String idSucursal, String numeroCuentaOrigen,
                                    String numeroCuentaDestino) {
        super(id, monto, fecha, idSucursal);
        this.numeroCuentaOrigen = numeroCuentaOrigen;
        this.numeroCuentaDestino = numeroCuentaDestino;
    }

    @Override
    public String getTipo() {
        return "TRANSFERENCIA";
    }

    public String getNumeroCuentaOrigen() {
        return numeroCuentaOrigen;
    }

    public String getNumeroCuentaDestino() {
        return numeroCuentaDestino;
    }

    public void setNumeroCuentaOrigen(String numeroCuentaOrigen) {
        this.numeroCuentaOrigen = numeroCuentaOrigen;
    }

    public void setNumeroCuentaDestino(String numeroCuentaDestino) {
        this.numeroCuentaDestino = numeroCuentaDestino;
    }
}