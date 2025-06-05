package com.eurobank.models;

public class Cuenta {
    private String numeroCuenta; // Cambiado de 'numero' a 'numeroCuenta'
    private TipoCuenta tipo; // Ahora usa el ENUM
    private double saldo;
    private double limiteCredito;
    private Cliente cliente;
    private boolean estadoActivo;

    public Cuenta(String numeroCuenta, TipoCuenta tipo, double saldo, Cliente cliente) {
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldo = saldo;
        this.cliente = cliente;
        this.estadoActivo = true;
    }

    // Getters y setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public void desactivar() {
        this.estadoActivo = false;
    }
}