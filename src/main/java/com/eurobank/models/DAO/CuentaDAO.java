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
    private int contadorId;

    public CuentaDAO() throws IOException {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        inicializarContadorId();
    }

    private void inicializarContadorId() throws IOException {
        List<Cuenta> cuentas = cargarCuentas();
        contadorId = cuentas.stream()
                .mapToInt(cuenta -> Integer.parseInt(cuenta.getNumeroCuenta().replace("CUENTA-", "")))
                .max()
                .orElse(0) + 1;
    }

    public void guardarCuentas(List<Cuenta> cuentas) throws IOException {
        new File("data").mkdirs();
        objectMapper.writeValue(new File(ARCHIVO_JSON), cuentas);
    }

    public List<Cuenta> cargarCuentas() throws IOException {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(objectMapper.readValue(archivo, Cuenta[].class)));
    }

    public Cuenta buscarCuentaPorNumero(String numeroCuenta) throws IOException {
        return cargarCuentas().stream()
                .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
                .findFirst()
                .orElse(null);
    }

    public List<Cuenta> listarCuentasPorCliente(String idCliente) throws IOException {
        return cargarCuentas().stream()
                .filter(c -> c.getIdCliente().equals(idCliente) && c.isEstadoActivo())
                .toList();
    }

    public List<Cuenta> filtrarCuentasPorTipo(TipoCuenta tipo) throws IOException {
        return cargarCuentas().stream()
                .filter(c -> c.getTipo() == tipo && c.isEstadoActivo())
                .toList();
    }

    public List<Cuenta> listarCuentasActivas() throws IOException {
        return cargarCuentas().stream()
                .filter(cuenta -> cuenta.isEstadoActivo())
                .toList();
    }

    public boolean eliminarCuenta(String numeroCuenta) throws IOException {
        boolean cuentaEliminada = false;
        List<Cuenta> cuentas = cargarCuentas();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                cuenta.setEstadoActivo(false);
                guardarCuentas(cuentas);
                cuentaEliminada = true;
            }
        }
        return cuentaEliminada;
    }

    public boolean actualizarCuenta(String numeroCuenta, Cuenta cuentaActualizada) throws IOException {
        boolean actualizacionRealizada = false;
        List<Cuenta> cuentas = cargarCuentas();
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getNumeroCuenta().equals(numeroCuenta)) {
                cuentas.set(i, cuentaActualizada);
                guardarCuentas(cuentas);
                actualizacionRealizada = true;
            }
        }
        return actualizacionRealizada;
    }

    public Cuenta crearCuenta(TipoCuenta tipo, double saldo, double limiteCredito, String idCliente) throws IOException {
        List<Cuenta> cuentas = cargarCuentas();
        String nuevoId = "CUENTA-" + contadorId++;
        Cuenta nuevaCuenta = new Cuenta(nuevoId, tipo, saldo, limiteCredito, idCliente);
        cuentas.add(nuevaCuenta);
        guardarCuentas(cuentas);
        return nuevaCuenta;
    }
}