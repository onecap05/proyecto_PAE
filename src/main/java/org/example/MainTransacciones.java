package org.example;
import com.eurobank.models.DAO.TransaccionDAO;
import com.eurobank.models.TransaccionDeposito;
import com.eurobank.models.TransaccionTransferencia;
import com.eurobank.models.Transaccion;

import java.io.IOException;
import java.util.List;

public class MainTransacciones {
    public static void main(String[] args) {
        TransaccionDAO transaccionDAO = new TransaccionDAO();

        try {
            // Test: Crear nueva transacción de depósito
            TransaccionDeposito deposito = new TransaccionDeposito();
            deposito.setMonto(1000.0);
            deposito.setIdSucursal("S-001");
            deposito.setNumeroCuenta("CUENTA-123");
            deposito.setTipo("DEPOSITO");
            Transaccion nuevaDeposito = transaccionDAO.crearNuevaTransaccion(deposito);
            System.out.println("Transacción de depósito creada: " + nuevaDeposito);

            // Test: Crear nueva transacción de transferencia
            TransaccionTransferencia transferencia = new TransaccionTransferencia();
            transferencia.setMonto(500.0);
            transferencia.setIdSucursal("S-001");
            transferencia.setNumeroCuentaOrigen("CUENTA-123");
            transferencia.setNumeroCuentaDestino("CUENTA-456");
            transferencia.setTipo("TRANSFERENCIA");
            Transaccion nuevaTransferencia = transaccionDAO.crearNuevaTransaccion(transferencia);
            System.out.println("Transacción de transferencia creada: " + nuevaTransferencia);

            // Test: Listar todas las transacciones
            List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
            System.out.println("Lista de transacciones:");
            transacciones.forEach(System.out::println);

            // Test: Filtrar transacciones por sucursal
            List<Transaccion> transaccionesSucursal = transaccionDAO.filtrarPorSucursal("S-001");
            System.out.println("Transacciones de la sucursal S-001:");
            transaccionesSucursal.forEach(System.out::println);

            // Test: Filtrar transacciones por tipo
            List<Transaccion> transaccionesDeposito = transaccionDAO.filtrarPorTipo("DEPOSITO");
            System.out.println("Transacciones de tipo depósito:");
            transaccionesDeposito.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}