package com.eurobank.models;

import java.time.LocalDateTime;

public class TransaccionDeposito extends Transaccion {
    private String numeroCuenta;

    public TransaccionDeposito(String id, double monto, LocalDateTime fecha,
                               String idSucursal, String numeroCuenta) {
        super(id, monto, fecha, idSucursal);
        this.numeroCuenta = numeroCuenta;
    }

    public TransaccionDeposito() {
        super();
    }

    @Override
    public String getTipo() {
        return "DEPOSITO";
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}