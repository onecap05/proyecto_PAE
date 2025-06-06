package com.eurobank.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.regex.Pattern;

public class Empleado {

    private String id;
    private String nombre;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String genero;
    private double salario;
    private RolEmpleado rol;
    private String usuario;
    private String password;
    private boolean estadoActivo = true;
    private String horarioTrabajo;
    private Integer numeroVentanilla;
    private Integer clientesAsignados;
    private String especializacion;
    private String nivelAcceso;
    private Integer anosExperiencia;
    private String idSucursal;

    private static final Pattern HORARIO_PATTERN = Pattern.compile("^([A-Za-z]-[A-Za-z])\\s\\d{1,2}:\\d{2}-\\d{1,2}:\\d{2}$");
    private static final List<String> ESPECIALIZACIONES_VALIDAS = Arrays.asList("PYMES", "corporativo");
    private static final List<String> NIVELES_ACCESO_VALIDOS = Arrays.asList("sucursal", "regional", "nacional");

    public Empleado() {
    }

    public Empleado(String id, String nombre, String direccion, LocalDate fechaNacimiento,
                    String genero, double salario, RolEmpleado rol, String usuario, String password) {
        setId(id);
        setNombre(nombre);
        setDireccion(direccion);
        setFechaNacimiento(fechaNacimiento);
        setGenero(genero);
        setSalario(salario);
        setRol(rol);
        setUsuario(usuario);
        setPassword(password);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.trim();
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion.trim();
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setRol(RolEmpleado rol) {
        this.rol = rol;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHorarioTrabajo(String horarioTrabajo) {
        this.horarioTrabajo = horarioTrabajo;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public void setNivelAcceso(String nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public void setClientesAsignados(Integer clientesAsignados) {
        this.clientesAsignados = clientesAsignados;
    }

    public void setNumeroVentanilla(Integer numeroVentanilla) {
        this.numeroVentanilla = numeroVentanilla;
    }

    public void setIdSucursal(String idSucursal) {
        if (rol == RolEmpleado.GERENTE || rol == RolEmpleado.CAJERO || rol == RolEmpleado.EJECUTIVO_CUENTA) {
            this.idSucursal = idSucursal;
        }
    }

    public void setAnosExperiencia(Integer anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public void desactivar() {
        this.estadoActivo = false;
    }

    public boolean esInvalidoNombre(String nombre) {
        return nombre == null || nombre.trim().split("\\s+").length < 2;
    }

    public boolean esInvalidaDireccion(String direccion) {
        return direccion == null || direccion.trim().split("\\s+").length < 2;
    }

    public List<String> validarFechaNacimiento(LocalDate fecha) {
        List<String> errores = new ArrayList<>();

        if (fecha == null) {
            errores.add("La fecha de nacimiento no puede ir vacía.");
        } else if (fecha.isAfter(LocalDate.now())) {
            errores.add("La fecha de nacimiento debe ser válida.");
        } else if (Period.between(fecha, LocalDate.now()).getYears() < 18) {
            errores.add("Debe ser mayor de 18 años.");
        }

        return errores;
    }

    public boolean esInvalidoSalario(double salario) {
        return salario <= 0;
    }

    public boolean esInvalidoHorario(String horario) {
        return horario == null || !HORARIO_PATTERN.matcher(horario).matches();
    }

    public boolean esInvalidaEspecializacion(String especializacion) {
        return especializacion == null || !ESPECIALIZACIONES_VALIDAS.contains(especializacion);
    }

    public boolean esInvalidoNivelAcceso(String nivelAcceso) {
        return nivelAcceso == null || !NIVELES_ACCESO_VALIDOS.contains(nivelAcceso);
    }

    public boolean esGeneroValido(String genero) {
        if (genero == null || genero.isBlank()) {
            return false;
        }
        String generoNormalizado = genero.trim().toLowerCase();
        return generoNormalizado.equals("masculino") || generoNormalizado.equals("femenino");
    }

    public boolean esInvalidoNumeroVentanilla(Integer numeroVentanilla) {
        return numeroVentanilla == null || numeroVentanilla <= 0;
    }

    public boolean esInvalidoClientesAsignados(Integer clientesAsignados) {
        return clientesAsignados == null || clientesAsignados < 0;
    }
    public boolean esAnosExperienica(Integer anosExperiencia) {
        return anosExperiencia == null || anosExperiencia < 0;
    }

    public List<String> validarCamposBasicos() {
        List<String> errores = new ArrayList<>();

        if (esInvalidoNombre(this.nombre)) {
            errores.add("El nombre debe contener al menos dos palabras.");
        }

        if (esInvalidaDireccion(this.direccion)) {
            errores.add("La dirección debe contener al menos dos palabras.");
        }

        if (!esGeneroValido(this.genero)) {
            errores.add("El genero debe ser masculino o femenino.");
        }

        errores.addAll(validarFechaNacimiento(this.fechaNacimiento));

        if (esInvalidoSalario(this.salario)) {
            errores.add("El salario debe ser mayor a 0.");
        }

        return errores;
    }

    public List<String> validarPorRol() {

        List<String> errores = new ArrayList<>();
        errores.addAll(validarCamposBasicos());

        if (rol == null) {
            errores.add("El rol del empleado no puede estar vacío.");
            return errores;
        }

        switch (rol) {
            case CAJERO:
                if (esInvalidoHorario(horarioTrabajo)) {
                    errores.add("El horario de trabajo no es válido. Formato esperado: L-V 8:00-16:00");
                }
                if (esInvalidoNumeroVentanilla(numeroVentanilla)) {
                    errores.add("El número de ventanilla debe ser mayor a 0.");
                }
                break;

            case EJECUTIVO_CUENTA:
                if (esInvalidoClientesAsignados(clientesAsignados)) {
                    errores.add("El número de clientes asignados debe ser mayor o igual a 0.");
                }
                if (esInvalidaEspecializacion(especializacion)) {
                    errores.add("La especialización debe ser 'PYMES' o 'corporativo'.");
                }
                break;

            case GERENTE:
                if (esInvalidoNivelAcceso(nivelAcceso)) {
                    errores.add("El nivel de acceso debe ser 'sucursal', 'regional' o 'nacional'.");
                }
                if(esAnosExperienica(anosExperiencia)) {
                    errores.add("Los años de experiencia tienen que ser un numero entero positivo");
                }
                break;

            case ADMINISTRADOR:
                break;

            default:
                errores.add("Rol de empleado no reconocido.");
        }

        if (idSucursal == null || idSucursal.isBlank()) {
            errores.add("El ID de la sucursal no puede estar vacío.");
        }

        return errores;
    }


    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getGenero() { return genero; }
    public double getSalario() { return salario; }
    public RolEmpleado getRol() { return rol; }
    public String getUsuario() { return usuario; }
    public String getPassword() { return password; }
    public boolean isEstadoActivo() { return estadoActivo; }
    public String getHorarioTrabajo() { return horarioTrabajo; }
    public Integer getNumeroVentanilla() { return numeroVentanilla; }
    public Integer getClientesAsignados() { return clientesAsignados; }
    public String getEspecializacion() { return especializacion; }
    public String getNivelAcceso() { return nivelAcceso; }
    public Integer getAnosExperiencia() { return anosExperiencia; }
    public String getIdSucursal() { return idSucursal; }

    @Override
    public String toString() {
        return "Empleado{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", genero='" + genero + '\'' +
                ", salario=" + salario +
                ", rol=" + rol +
                ", usuario='" + usuario + '\'' +
                ", estadoActivo=" + estadoActivo +
                '}';
    }
}
