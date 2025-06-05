package com.eurobank.models;

public class Cliente {
    private String id;
    private String nombre;
    private String rfc;

    // Constructor, getters y setters
    public Cliente() {
        this.id = "";
        this.nombre = "";
        this.rfc = "";
    }

    public Cliente(String id, String nombre, String rfc) {
        this.id = id;
        this.nombre = nombre;
        this.rfc = rfc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
}