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
        // Configuración para soportar LocalDate y formato legible
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

    // Guardar empleados en JSON
    public void guardarEmpleados(List<Empleado> empleados) throws IOException {
        validarEmpleados(empleados); // Validar campos por rol
        new File("data").mkdirs(); // Crear directorio si no existe
        objectMapper.writeValue(new File(ARCHIVO_JSON), empleados);
    }

    // Cargar empleados desde JSON
    public List<Empleado> cargarEmpleados() throws IOException {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(objectMapper.readValue(archivo, Empleado[].class)));
    }

    // Validar campos según el rol
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

    // Buscar empleado por ID
    public Empleado buscarEmpleadoPorId(String id) throws IOException {
        return cargarEmpleados().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Filtrar empleados por rol
    public List<Empleado> filtrarEmpleadosPorRol(RolEmpleado rol) throws IOException {
        return cargarEmpleados().stream()
                .filter(e -> e.getRol() == rol)
                .toList();
    }
}
