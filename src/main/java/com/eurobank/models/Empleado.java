package com.eurobank.models;

public class Empleado {
    private String id;
    private String nombre;
    private RolEmpleado rol;
    private String usuario;
    private String password;
    private boolean estadoActivo;

    // Campos específicos por rol
    private String horarioTrabajo; // Para cajeros
    private Integer numeroVentanilla; // Para cajeros
    private Integer clientesAsignados; // Para ejecutivos
    private String especializacion; // Para ejecutivos (PYMES, corporativo)
    private String nivelAcceso; // Para gerentes (sucursal, regional, nacional)
    private Integer anosExperiencia; // Para gerentes

    public Empleado(String id, String nombre, RolEmpleado rol, String usuario, String password) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.usuario = usuario;
        this.password = password;
        this.estadoActivo = true; // Por defecto está activo
    }

    // Getters y setters básicos
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