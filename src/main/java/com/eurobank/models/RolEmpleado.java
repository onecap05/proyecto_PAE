package com.eurobank.models;

public enum RolEmpleado {
    ADMINISTRADOR("ADMINISTRADOR", "Acceso completo al sistema"),
    GERENTE("GERENTE", "Acceso a gestión de sucursal"),
    EJECUTIVO_CUENTA("EJECUTIVO_CUENTA", "Gestión de clientes y cuentas"),
    CAJERO("CAJERO", "Realización de transacciones");

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