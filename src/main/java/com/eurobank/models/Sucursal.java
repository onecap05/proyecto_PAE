package com.eurobank.models;

public class Sucursal {
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo; // Nuevo campo
    private String idGerente; // Cambiado de 'gerente' a 'idGerente'
    private String contacto;
    private boolean estadoActivo;

    public Sucursal(String id, String nombre, String direccion, String telefono,
                    String correo, String idGerente) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.idGerente = idGerente;
        this.estadoActivo = true;
    }

    // Getters y setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIdGerente() {
        return idGerente;
    }

    public void setIdGerente(String idGerente) {
        this.idGerente = idGerente;
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