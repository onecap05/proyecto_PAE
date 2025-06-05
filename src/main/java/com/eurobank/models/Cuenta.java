package com.eurobank.models;

public class Cuenta {
    private String numero;
    private String tipo; // corriente, ahorros, empresarial
    private double saldo;

    // Constructor, getters y setters
    public Cuenta() {
        this.numero = "";
        this.tipo = "";
        this.saldo = 0.0;
    }

    public Cuenta(String numero, String tipo, double saldo) {
        this.numero = numero;
        this.tipo = tipo;
        this.saldo = saldo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}