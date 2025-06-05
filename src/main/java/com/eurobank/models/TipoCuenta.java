package com.eurobank.models;

public enum TipoCuenta {
    CORRIENTE("Corriente", 0.1), // Tasa de interés ejemplo
    AHORROS("Ahorros", 0.05),
    EMPRESARIAL("Empresarial", 0.15);

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