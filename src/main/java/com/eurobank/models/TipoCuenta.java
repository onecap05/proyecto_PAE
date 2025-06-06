package com.eurobank.models;

public enum TipoCuenta {
    CORRIENTE("CORRIENTE", 0.1), // Tasa de interés ejemplo
    AHORROS("AHORROS", 0.05),
    EMPRESARIAL("EMPRESARIAL", 0.15);

    private final String nombre;
    private final double tasaInteres;

    TipoCuenta(String nombre, double tasaInteres) {
        this.nombre = nombre;
        this.tasaInteres = tasaInteres;
    }

    public String getNombre() {
        return nombre;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    @Override
    public String toString() {
        return nombre;
    }
}