package com.eurobank.models;

import java.time.LocalDate;

public class Empleado {
    private String id;
    private String nombre;
    private String direccion; // Nuevo campo
    private LocalDate fechaNacimiento; // Nuevo campo
    private String genero; // Nuevo campo (podría ser ENUM)
    private double salario; // Nuevo campo
    private RolEmpleado rol;
    private String usuario;
    private String password;
    private boolean estadoActivo;

    public Empleado(String id, String nombre, String direccion, LocalDate fechaNacimiento,
                    String genero, double salario, RolEmpleado rol, String usuario, String password) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.salario = salario;
        this.rol = rol;
        this.usuario = usuario;
        this.password = password;
        this.estadoActivo = true;
    }

    private String horarioTrabajo; // Para cajeros
    private Integer numeroVentanilla; // Para cajeros
    private Integer clientesAsignados; // Para ejecutivos
    private String especializacion; // Para ejecutivos (PYMES, corporativo)
    private String nivelAcceso; // Para gerentes (sucursal, regional, nacional)
    private Integer anosExperiencia; // Para gerentes
    // Getters y setters básicos

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public RolEmpleado getRol() { return rol; }
    public void setRol(RolEmpleado rol) { this.rol = rol; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Getters y setters específicos por rol
    public String getHorarioTrabajo() { return horarioTrabajo; }
    public void setHorarioTrabajo(String horarioTrabajo) {
        if(this.rol == RolEmpleado.CAJERO) {
            this.horarioTrabajo = horarioTrabajo;
        }
    }

    public Integer getNumeroVentanilla() { return numeroVentanilla; }
    public void setNumeroVentanilla(Integer numeroVentanilla) {
        if(this.rol == RolEmpleado.CAJERO) {
            this.numeroVentanilla = numeroVentanilla;
        }
    }

    public Integer getClientesAsignados() { return clientesAsignados; }
    public void setClientesAsignados(Integer clientesAsignados) {
        if(this.rol == RolEmpleado.EJECUTIVO_CUENTA) {
            this.clientesAsignados = clientesAsignados;
        }
    }

    public String getEspecializacion() { return especializacion; }
    public void setEspecializacion(String especializacion) {
        if(this.rol == RolEmpleado.EJECUTIVO_CUENTA) {
            this.especializacion = especializacion;
        }
    }

    public String getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(String nivelAcceso) {
        if(this.rol == RolEmpleado.GERENTE) {
            this.nivelAcceso = nivelAcceso;
        }
    }

    public Integer getAnosExperiencia() { return anosExperiencia; }
    public void setAnosExperiencia(Integer anosExperiencia) {
        if(this.rol == RolEmpleado.GERENTE) {
            this.anosExperiencia = anosExperiencia;
        }
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