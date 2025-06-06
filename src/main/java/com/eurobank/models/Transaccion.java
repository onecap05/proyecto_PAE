package com.eurobank.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TransaccionDeposito.class, name = "DEPOSITO"),
        @JsonSubTypes.Type(value = TransaccionRetiro.class, name = "RETIRO"),
        @JsonSubTypes.Type(value = TransaccionTransferencia.class, name = "TRANSFERENCIA")
})

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

    public Transaccion() {

    }

    // Método que puede ser sobrescrito por clases hijas
    public String getTipo() {
        return "GENERICA";
    }

    public void setTipo(String tipo) {

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

    @Override
    public String toString() {
        return "Transaccion{" +
                "id='" + id + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", idSucursal='" + idSucursal + '\'' +
                '}';
    }
}