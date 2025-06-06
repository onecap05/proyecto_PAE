package com.eurobank.models.DAO;

import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmpleadoDAO {
    private static final String ARCHIVO_JSON = "src/main/resources/data/empleados.json";
    private ObjectMapper objectMapper;

    public EmpleadoDAO() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

    public void guardarEmpleados(List<Empleado> empleados) throws IOException {
        validarEmpleados(empleados);
        new File("data").mkdirs();
        objectMapper.writeValue(new File(ARCHIVO_JSON), empleados);
    }

    public List<Empleado> cargarEmpleados() throws IOException {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(objectMapper.readValue(archivo, Empleado[].class)));
    }

    private void validarEmpleados(List<Empleado> empleados) {
        for (Empleado emp : empleados) {
            switch (emp.getRol()) {
                case CAJERO:
                    if (emp.getHorarioTrabajo() == null || emp.getNumeroVentanilla() == null) {
                        throw new IllegalArgumentException("Campos de cajero incompletos: horarioTrabajo y numeroVentanilla son obligatorios");
                    }
                    break;
                case EJECUTIVO_CUENTA:
                    if (emp.getClientesAsignados() == null || emp.getEspecializacion() == null) {
                        throw new IllegalArgumentException("Campos de ejecutivo incompletos: clientesAsignados y especializacion son obligatorios");
                    }
                    break;
                case GERENTE:
                    if (emp.getNivelAcceso() == null || emp.getAnosExperiencia() == null) {
                        throw new IllegalArgumentException("Campos de gerente incompletos: nivelAcceso y anosExperiencia son obligatorios");
                    }
                    break;
            }
            if (emp.getIdSucursal() == null) {
                throw new IllegalArgumentException("idSucursal es obligatorio para roles CAJERO, EJECUTIVO_CUENTA y GERENTE");
            }
        }
    }

    public Empleado buscarEmpleadoPorId(String id) throws IOException {
        return cargarEmpleados().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Empleado> filtrarEmpleadosPorRol(RolEmpleado rol) throws IOException {
        return cargarEmpleados().stream()
                .filter(e -> e.getRol() == rol && e.isEstadoActivo())
                .toList();
    }

    public boolean eliminarEmpleado(String id) throws IOException {
        boolean empleadoEliminado = false;
        List<Empleado> empleados = cargarEmpleados();
        for (Empleado empleado : empleados) {
            if (empleado.getId().equals(id)) {
                empleado.setEstadoActivo(false);
                guardarEmpleados(empleados);
                empleadoEliminado = true;
            }
        }
        return empleadoEliminado;
    }

    public boolean actualizarEmpleado(String id, Empleado empleadoActualizado) throws IOException {
        boolean actualizacionRealizada = false;
        List<Empleado> empleados = cargarEmpleados();
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId().equals(id)) {
                empleados.set(i, empleadoActualizado);
                guardarEmpleados(empleados);
                actualizacionRealizada = true;
            }
        }
        return actualizacionRealizada;
    }

    public List<Empleado> listarEmpleadosActivos() throws IOException {
        return cargarEmpleados().stream()
                .filter(empleado -> empleado.isEstadoActivo())
                .toList();
    }

    public List<Empleado> listarEmpleadosPorSucursal(String idSucursal) throws IOException {
        return cargarEmpleados().stream()
                .filter(e -> e.getIdSucursal().equals(idSucursal) && e.isEstadoActivo())
                .toList();
    }

    private String generarNuevoId() throws IOException {
        List<Empleado> empleados = cargarEmpleados();
        int maxId = empleados.stream()
                .map(e -> e.getId().replace("E-", ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return "E-" + (maxId + 1);
    }

    public Empleado crearNuevoEmpleado(Empleado nuevoEmpleado) throws IOException {
        List<Empleado> empleados = cargarEmpleados();
        nuevoEmpleado.setId(generarNuevoId());
        empleados.add(nuevoEmpleado);
        guardarEmpleados(empleados);
        return nuevoEmpleado;
    }
}