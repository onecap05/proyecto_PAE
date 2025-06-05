package com.eurobank.models;

public class Sucursal {
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String gerente;
    private String contacto;
    private boolean estadoActivo; // Nuevo campo

    public Sucursal(String id, String nombre, String direccion, String telefono, String gerente) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gerente = gerente;
        this.estadoActivo = true;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getGerente() {
        return gerente;
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