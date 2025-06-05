package com.eurobank.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {
    private String id;
    private double monto;
    private LocalDateTime fecha;
    private String tipo;
    private String cuentaOrigen;
    private String cuentaDestino;
    private String sucursal;

    public Transaccion(String id, double monto, LocalDateTime fecha, String tipo,
                       String cuentaOrigen, String cuentaDestino, String sucursal) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.sucursal = sucursal;
    }

    // Getters
    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    // Resto de getters...
    public String getId() { return id; }
    public double getMonto() { return monto; }
    public String getTipo() { return tipo; }
    public String getCuentaOrigen() { return cuentaOrigen; }
    public String getCuentaDestino() { return cuentaDestino; }
    public String getSucursal() { return sucursal; }
}