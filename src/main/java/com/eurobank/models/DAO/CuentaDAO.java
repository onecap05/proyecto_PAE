package com.eurobank.models.DAO;

import com.eurobank.models.Cuenta;
import com.eurobank.models.TipoCuenta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CuentaDAO {

    private static final String ARCHIVO_JSON = "src/main/resources/data/cuentas.json";
    private ObjectMapper objectMapper;

    public CuentaDAO() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

    // Guardar cuentas en JSON
    public void guardarCuentas(List<Cuenta> cuentas) throws IOException {
        new File("data").mkdirs(); // Crear carpeta si no existe
        objectMapper.writeValue(new File(ARCHIVO_JSON), cuentas);
    }

    // Cargar cuentas desde JSON
    public List<Cuenta> cargarCuentas() throws IOException {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(objectMapper.readValue(archivo, Cuenta[].class)));
    }

    // Buscar cuenta por número
    public Cuenta buscarCuentaPorNumero(String numeroCuenta) throws IOException {
        return cargarCuentas().stream()
                .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
                .findFirst()
                .orElse(null);
    }

    // Filtrar cuentas por tipo
    public List<Cuenta> filtrarCuentasPorTipo(TipoCuenta tipo) throws IOException {
        return cargarCuentas().stream()
                .filter(c -> c.getTipo() == tipo)
                .toList();
    }
}
