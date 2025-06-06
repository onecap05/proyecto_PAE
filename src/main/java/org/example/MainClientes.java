package org.example;

import com.eurobank.models.Cliente;
import com.eurobank.models.DAO.ClienteDAO;
import com.eurobank.models.excepciones.ClienteNoEncontradoException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MainClientes {

    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();

        try {

            // Create some sample clients
            Cliente nuevoCliente = new Cliente("RFC788", "Angel", "Perez", "mexico",
                    LocalDate.of(1990, 3, 15), "direccion nueva", "1234567890", "angel.perez@email.com");

            clienteDAO.crearCliente(nuevoCliente);

            List<Cliente> clientes = clienteDAO.cargarClientes();
            System.out.println("Clientes cargados:");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }

            // Search for a client by ID
            String idFiscal = "RFC456";
            Cliente cliente = clienteDAO.buscarClientePorIdFiscal(idFiscal);
            if (cliente != null) {
                System.out.println("Cliente encontrado: " + cliente);
            } else {
                System.out.println("Cliente con ID " + idFiscal + " no encontrado.");
            }

            // Update a client
            Cliente clienteActualizado = new Cliente("RFC456", "Nuevo Nombre", "Nuevo Apellido", "mexico", LocalDate.of(1985, 5, 10), "direccion falsa nueva", "2293551529", "email");
            boolean actualizado = clienteDAO.actualizarCliente(idFiscal, clienteActualizado);
            if (actualizado) {
                System.out.println("Cliente actualizado: " + clienteActualizado);
            } else {
                System.out.println("No se pudo actualizar el cliente con ID " + idFiscal);
            }

            // "Delete" a client by setting estadoActivo to false
            boolean eliminado = clienteDAO.eliminarCliente(idFiscal);
            if (eliminado) {
                System.out.println("Cliente con ID " + idFiscal + " eliminado (estadoActivo = false).");
            } else {
                System.out.println("No se pudo eliminar el cliente con ID " + idFiscal);
            }

            List<Cliente> clientesActualizados = clienteDAO.filtrarClientesActivos();
            System.out.println("Clientes activos:");
            for (Cliente c : clientesActualizados) {
                System.out.println(c);
            }

        } catch (IOException | ClienteNoEncontradoException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}