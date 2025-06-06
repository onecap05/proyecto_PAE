package com.eurobank.models.DAO;

import com.eurobank.models.Transaccion;
import com.eurobank.models.TransaccionDeposito;
import com.eurobank.models.TransaccionRetiro;
import com.eurobank.models.TransaccionTransferencia;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransaccionDAO {
    private static final String ARCHIVO_JSON = "src/main/resources/data/transacciones.json";
    private ObjectMapper objectMapper;

    public TransaccionDAO() {
        this.objectMapper = new ObjectMapper();
        // Configuración para Jackson
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Configurar manejo de herencia
        objectMapper.registerSubtypes(
                TransaccionDeposito.class,
                TransaccionRetiro.class,
                TransaccionTransferencia.class
        );
    }

    // Guardar todas las transacciones
    public void guardarTransacciones(List<Transaccion> transacciones) throws IOException {
        validarTransacciones(transacciones); // Validaciones adicionales
        new File("data").mkdirs();
        objectMapper.writeValue(new File(ARCHIVO_JSON), transacciones);
    }

    // Cargar todas las transacciones (reconstruye los tipos correctos)
    public List<Transaccion> cargarTransacciones() throws IOException {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(objectMapper.readValue(archivo, Transaccion[].class)));
    }

    // Métodos de filtrado
    public List<Transaccion> filtrarPorSucursal(String idSucursal) throws IOException {
        return cargarTransacciones().stream()
                .filter(t -> t.getIdSucursal().equals(idSucursal))
                .toList();
    }

    public List<Transaccion> filtrarPorTipo(String tipo) throws IOException {
        return cargarTransacciones().stream()
                .filter(t -> t.getTipo().equalsIgnoreCase(tipo))
                .toList();
    }

    // Validaciones comunes
    private void validarTransacciones(List<Transaccion> transacciones) {
        for (Transaccion t : transacciones) {
            if (t.getMonto() <= 0) {
                throw new IllegalArgumentException("El monto debe ser positivo");
            }
            // Puedes agregar más validaciones aquí
        }
    }
}
