package com.eurobank.models;

import java.time.LocalDateTime;

public class TransaccionRetiro extends Transaccion {
    private String numeroCuenta;

    public TransaccionRetiro(String id, double monto, LocalDateTime fecha,
                             String idSucursal, String numeroCuenta) {
        super(id, monto, fecha, idSucursal);
        this.numeroCuenta = numeroCuenta;
    }

    public TransaccionRetiro() {
        super();
    }

    @Override
    public String getTipo() {
        return "RETIRO";
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}