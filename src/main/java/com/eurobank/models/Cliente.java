package com.eurobank.models;

import java.time.LocalDate;

public class Cliente {
    private String idFiscal;
    private String nombre;
    private String apellidos;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String telefono;
    private String email;
    private boolean estadoActivo; // Nuevo campo

    public Cliente(String idFiscal, String nombre, String apellidos, String nacionalidad,
                   LocalDate fechaNacimiento, String direccion, String telefono, String email) {
        this.idFiscal = idFiscal;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.estadoActivo = true; // Valor por defecto
    }

    public Cliente () {

    }

    // Getters y setters
    public String getIdFiscal() { return idFiscal; }
    public void setIdFiscal(String idFiscal) { this.idFiscal = idFiscal; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    // En Cliente.java
    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public void desactivar() {
        this.estadoActivo = false;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idFiscal='" + idFiscal + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", estadoActivo=" + estadoActivo +
                '}';
    }
}
