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
    private ObjectMapper objectMapper = new ObjectMapper();

    // Guardar lista de sucursales en JSON
    public void guardarSucursales(List<Sucursal> sucursales) throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Formato legible
        objectMapper.writeValue(new File(ARCHIVO_JSON), sucursales);
    }

    // Cargar lista de sucursales desde JSON
    public List<Sucursal> cargarSucursales() throws IOException {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>(); // Si no existe, retorna lista vacía
        }
        Sucursal[] sucursalesArray = objectMapper.readValue(archivo, Sucursal[].class);
        return new ArrayList<>(Arrays.asList(sucursalesArray));
    }
}