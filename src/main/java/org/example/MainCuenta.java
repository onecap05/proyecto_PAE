package org.example;

import com.eurobank.models.Cuenta;
import com.eurobank.models.DAO.CuentaDAO;
import com.eurobank.models.TipoCuenta;

import java.io.IOException;
import java.util.List;

public class MainCuenta {
    public static void main(String[] args) {
        try {
            CuentaDAO cuentaDAO = new CuentaDAO();

            // Load all accounts
            List<Cuenta> cuentas = cuentaDAO.cargarCuentas();
            System.out.println("Cuentas cargadas:");
            for (Cuenta cuenta : cuentas) {
                System.out.println(cuenta);
            }

            // Search for an account by number
            String numeroCuenta = "CUENTA-001";
            Cuenta cuenta = cuentaDAO.buscarCuentaPorNumero(numeroCuenta);
            if (cuenta != null) {
                System.out.println("Cuenta encontrada: " + cuenta);
            } else {
                System.out.println("Cuenta con número " + numeroCuenta + " no encontrada.");
            }

            // Create a new account
            Cuenta nuevaCuenta = cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 5000.0, 0.0, "CLIENTE-123");
            System.out.println("Nueva cuenta creada: " + nuevaCuenta);

            // Update an account
            Cuenta cuentaActualizada = new Cuenta("CUENTA-001", TipoCuenta.CORRIENTE, 10000.0, 0.0, "CLIENTE-123");
            boolean actualizada = cuentaDAO.actualizarCuenta("CUENTA-001", cuentaActualizada);
            if (actualizada) {
                System.out.println("Cuenta actualizada: " + cuentaActualizada);
            } else {
                System.out.println("No se pudo actualizar la cuenta con número " + numeroCuenta);
            }

            // Delete an account
            boolean eliminada = cuentaDAO.eliminarCuenta("CUENTA-001");
            if (eliminada) {
                System.out.println("Cuenta con número " + numeroCuenta + " eliminada (estadoActivo = false).");
            } else {
                System.out.println("No se pudo eliminar la cuenta con número " + numeroCuenta);
            }

            String idCliente = "CLIENTE-125";
            List<Cuenta> cuentasPorCliente = cuentaDAO.listarCuentasPorCliente(idCliente);
            System.out.println("Cuentas del cliente con ID " + idCliente + ":");
            for (Cuenta cuenta1 : cuentasPorCliente) {
                System.out.println(cuenta1);
            }

            // Test filtrarCuentasPorTipo
            TipoCuenta tipoCuenta = TipoCuenta.AHORROS;
            List<Cuenta> cuentasPorTipo = cuentaDAO.filtrarCuentasPorTipo(tipoCuenta);
            System.out.println("Cuentas del tipo " + tipoCuenta + ":");
            for (Cuenta cuenta2 : cuentasPorTipo) {
                System.out.println(cuenta2);
            }

            // Test listarCuentasActivas
            List<Cuenta> cuentasActivas = cuentaDAO.listarCuentasActivas();
            System.out.println("Cuentas activas:");
            for (Cuenta cuenta3 : cuentasActivas) {
                System.out.println(cuenta3);
            }



        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}