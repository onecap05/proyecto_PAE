package com.eurobank.models;

public class Cuenta {
    private java.lang.String numeroCuenta; // Cambiado de 'numero' a 'numeroCuenta'
    private TipoCuenta tipo; // Ahora usa el ENUM
    private double saldo;
    private double limiteCredito;
    private String idCliente;
    private boolean estadoActivo;

    public Cuenta(String numeroCuenta, TipoCuenta tipo, double saldo, String cliente) {
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldo = saldo;
        this.idCliente = cliente;
        this.estadoActivo = true;
    }

    public Cuenta(String numeroCuenta, TipoCuenta tipo, double saldo, double limiteCredito, String idCliente) {
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
        this.idCliente = idCliente;
        this.estadoActivo = true;
    }

    public Cuenta() {

    }

    // Getters y setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(java.lang.String numeroCuenta) {
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

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        if (tipo != TipoCuenta.EMPRESARIAL && limiteCredito != 0.0) {
            throw new UnsupportedOperationException("Solo las cuentas empresariales tienen límite de crédito.");
        }
        this.limiteCredito = limiteCredito;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", tipo=" + tipo +
                ", saldo=" + saldo +
                ", limiteCredito=" + limiteCredito +
                ", idCliente='" + idCliente + '\'' +
                ", estadoActivo=" + estadoActivo +
                '}';
    }
}