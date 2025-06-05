package com.eurobank.models;

public enum RolEmpleado {
    ADMINISTRADOR("Administrador", "Acceso completo al sistema"),
    GERENTE("Gerente", "Acceso a gestión de sucursal"),
    EJECUTIVO_CUENTA("Ejecutivo de Cuentas", "Gestión de clientes y cuentas"),
    CAJERO("Cajero", "Realización de transacciones");

    private final String nombre;
    private final String descripcion;

    RolEmpleado(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}