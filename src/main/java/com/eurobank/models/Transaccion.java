package com.eurobank.models;

import java.time.LocalDateTime;

public class Transaccion {
    private String id;
    private double monto;
    private LocalDateTime fecha;
    private String idSucursal;

    public Transaccion(String id, double monto, LocalDateTime fecha, String idSucursal) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.idSucursal = idSucursal;
    }

    // Método que puede ser sobrescrito por clases hijas
    public String getTipo() {
        return "GENERICA";
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }
}