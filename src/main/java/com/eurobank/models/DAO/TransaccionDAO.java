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
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        objectMapper.registerSubtypes(
                TransaccionDeposito.class,
                TransaccionRetiro.class,
                TransaccionTransferencia.class
        );
    }


    public void guardarTransacciones(List<Transaccion> transacciones) throws IOException {
        new File("data").mkdirs();
        objectMapper.writeValue(new File(ARCHIVO_JSON), transacciones);
    }


    public List<Transaccion> cargarTransacciones() throws IOException {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(objectMapper.readValue(archivo, Transaccion[].class)));
    }


    private String generarNuevoId() throws IOException {
        List<Transaccion> transacciones = cargarTransacciones();
        int maxId = transacciones.stream()
                .map(t -> t.getId().replace("T-", ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return "T-" + (maxId + 1);
    }


    public Transaccion crearNuevaTransaccion(Transaccion nuevaTransaccion) throws IOException {
        List<Transaccion> transacciones = cargarTransacciones();
        nuevaTransaccion.setId(generarNuevoId());
        transacciones.add(nuevaTransaccion);
        guardarTransacciones(transacciones);
        return nuevaTransaccion;
    }


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

    public List<Transaccion> filtrarTransaccionPorFecha(String fecha) throws IOException {
        return cargarTransacciones().stream()
                .filter(t -> t.getFecha().toString().equals(fecha))
                .toList();
    }


}