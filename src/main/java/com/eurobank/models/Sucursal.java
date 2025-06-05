package com.eurobank.models;

public class Sucursal {
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String gerente;
    private String contacto;

    public Sucursal(String id, String nombre, String direccion, String telefono, String gerente) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gerente = gerente;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getGerente() { return gerente; }
    // ... otros getters
}