package com.eurobank.models.DAO;

import com.eurobank.models.Sucursal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SucursalDAO {
    private static final String ARCHIVO_JSON = "src/main/resources/data/sucursales.json";
    private ObjectMapper objectMapper;

    public SucursalDAO() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }


    public void guardarSucursales(List<Sucursal> sucursales) throws IOException {
        objectMapper.writeValue(new File(ARCHIVO_JSON), sucursales);
    }


    public List<Sucursal> cargarSucursales() throws IOException {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(objectMapper.readValue(archivo, Sucursal[].class)));
    }


    public Sucursal buscarSucursalPorId(String idSucursal) throws IOException {
        return cargarSucursales().stream()
                .filter(s -> s.getId().equals(idSucursal))
                .findFirst()
                .orElse(null);
    }

    public List<Sucursal> listarSucursalesActivas() throws IOException {
        return cargarSucursales().stream()
                .filter(Sucursal::isEstadoActivo)
                .toList();
    }

    public boolean actualizarSucursal(String idSucursal, Sucursal sucursalActualizada) throws IOException {
        List<Sucursal> sucursales = cargarSucursales();
        for (int i = 0; i < sucursales.size(); i++) {
            if (sucursales.get(i).getId().equals(idSucursal)) {
                sucursales.set(i, sucursalActualizada);
                guardarSucursales(sucursales);
                return true;
            }
        }
        return false;
    }


    public boolean eliminarSucursal(String idSucursal) throws IOException {
        List<Sucursal> sucursales = cargarSucursales();
        for (Sucursal sucursal : sucursales) {
            if (sucursal.getId().equals(idSucursal)) {
                sucursal.setEstadoActivo(false);
                guardarSucursales(sucursales);
                return true;
            }
        }
        return false;
    }


    private String generarNuevoId() throws IOException {
        List<Sucursal> sucursales = cargarSucursales();
        int maxId = sucursales.stream()
                .map(s -> s.getId().replace("S", ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return "S" + String.format("%03d", maxId + 1);
    }


    public Sucursal crearNuevaSucursal(Sucursal nuevaSucursal) throws IOException {
        List<Sucursal> sucursales = cargarSucursales();
        nuevaSucursal.setId(generarNuevoId());
        nuevaSucursal.setEstadoActivo(true);
        sucursales.add(nuevaSucursal);
        guardarSucursales(sucursales);
        return nuevaSucursal;
    }


    public List<Sucursal> listarSucursales() throws IOException {
        return cargarSucursales();
    }
}