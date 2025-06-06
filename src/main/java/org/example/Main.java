package org.example;



import com.eurobank.models.*;
import com.eurobank.models.DAO.ClienteDAO;
import com.eurobank.models.DAO.CuentaDAO;
import com.eurobank.models.DAO.EmpleadoDAO;
import com.eurobank.models.DAO.TransaccionDAO;
import com.eurobank.utils.TransaccionFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Transaccion deposito = TransaccionFactory.crearTransaccion(
                "DEPOSITO", "T-001", 5000.0, LocalDateTime.now(), "S-001", "CUENTA-123"
        );

        Transaccion transferencia = TransaccionFactory.crearTransaccion(
                "TRANSFERENCIA", "T-002", 3000.0, LocalDateTime.now(), "S-001",
                "CUENTA-123", "CUENTA-456"
        );

// Guardar usando el DAO
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        try {
            List<Transaccion> transacciones = new ArrayList<>();
            transacciones.add(deposito);
            transacciones.add(transferencia);
            transaccionDAO.guardarTransacciones(transacciones);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Load transactions from the JSON file
            List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();

            // Display all transactions
            System.out.println("Transacciones cargadas desde el sistema:");
            for (Transaccion t : transacciones) {
                System.out.println(t.toString());
            }
        } catch (IOException e) {
            System.err.println("Error al cargar las transacciones: " + e.getMessage());
        }

        try {

            List <Transaccion> listaTransacciones = transaccionDAO.filtrarPorSucursal("S-002");
            System.out.println("Transacciones filtradas por sucursal S-001:");
            for (Transaccion t : listaTransacciones) {
                System.out.println(t.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Cargar transacciones desde el archivo JSON




    }




}
