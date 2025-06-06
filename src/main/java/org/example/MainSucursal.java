package org.example;

import com.eurobank.models.DAO.SucursalDAO;
import com.eurobank.models.Sucursal;

import java.io.IOException;
import java.util.List;

public class MainSucursal {
    public static void main(String[] args) {
        SucursalDAO sucursalDAO = new SucursalDAO();

        try {
            // Test: Crear nueva sucursal
            Sucursal nuevaSucursal = new Sucursal();
            nuevaSucursal.setNombre("Sucursal Centro");
            nuevaSucursal.setDireccion("Av. Principal 123");
            nuevaSucursal.setTelefono("5551234567");
            Sucursal creada = sucursalDAO.crearNuevaSucursal(nuevaSucursal);
            System.out.println("Sucursal creada: " + creada);

            // Test: Listar todas las sucursales
            List<Sucursal> sucursales = sucursalDAO.listarSucursales();
            System.out.println("Lista de sucursales:");
            sucursales.forEach(System.out::println);

            // Test: Buscar sucursal por ID
            Sucursal buscada = sucursalDAO.buscarSucursalPorId(creada.getId());
            System.out.println("Sucursal buscada: " + buscada);

            // Test: Actualizar sucursal
            buscada.setTelefono("5559876543");
            boolean actualizada = sucursalDAO.actualizarSucursal(buscada.getId(), buscada);
            System.out.println("Sucursal actualizada: " + actualizada);

            // Test: Listar sucursales activas
            List<Sucursal> activas = sucursalDAO.listarSucursalesActivas();
            System.out.println("Sucursales activas:");
            activas.forEach(System.out::println);

            // Test: Eliminar sucursal
            boolean eliminada = sucursalDAO.eliminarSucursal(creada.getId());
            System.out.println("Sucursal eliminada: " + eliminada);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
