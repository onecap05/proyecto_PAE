package com.eurobank.models.DAO;

import com.eurobank.models.excepciones.ClienteNoEncontradoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.eurobank.models.Cliente;

public class ClienteDAO {

    private static final java.lang.String ARCHIVO_JSON = "src/main/resources/data/clientes.json";
    private ObjectMapper objectMapper;

    public ClienteDAO() {
        this.objectMapper = new ObjectMapper();
        // Configuración para soportar LocalDate y formato legible
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // Guardar lista de clientes en JSON
    public void guardarClientes(List<Cliente> clientes) throws IOException {
        objectMapper.writeValue(new File(ARCHIVO_JSON), clientes);
    }

    // Cargar lista de clientes desde JSON
    public List<Cliente> cargarClientes() throws IOException {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>(); // Si no existe, retorna lista vacía
        }
        Cliente[] clientesArray = objectMapper.readValue(archivo, Cliente[].class);
        return new ArrayList<>(Arrays.asList(clientesArray));
    }

    public Cliente buscarClientePorIdFiscal(String idFiscal) throws IOException {
        return cargarClientes().stream()
                .filter(c -> c.getIdFiscal().equals(idFiscal))
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarCliente (String idFiscal, Cliente clienteActualizado) throws ClienteNoEncontradoException, IOException {
        boolean actualizacionRealizada = false;
        List<Cliente> clientes = cargarClientes();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdFiscal().equals(idFiscal)) {
                clientes.set(i, clienteActualizado);
                guardarClientes(clientes);
                actualizacionRealizada = true;
            }
        }
        return actualizacionRealizada;
    }



}
