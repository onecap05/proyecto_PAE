package org.example;

import com.eurobank.models.DAO.EmpleadoDAO;
import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;

import java.io.IOException;
import java.util.List;

public class MainEmpleados {
    public static void main(String[] args) {
        try {
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();

            // Test crearNuevoEmpleado
            Empleado nuevoEmpleado = new Empleado();
            nuevoEmpleado.setRol(RolEmpleado.CAJERO);
            nuevoEmpleado.setNombre("Juan Gomez");
            nuevoEmpleado.setDireccion("123 Calle Falsa");

            nuevoEmpleado.setIdSucursal("SUC-001");
            nuevoEmpleado.setHorarioTrabajo("9:00 AM - 5:00 PM");
            nuevoEmpleado.setNumeroVentanilla(1);
            Empleado empleadoCreado = empleadoDAO.crearNuevoEmpleado(nuevoEmpleado);
            System.out.println("Empleado creado: " + empleadoCreado);

            // Test listarEmpleadosActivos
            List<Empleado> empleadosActivos = empleadoDAO.listarEmpleadosActivos();
            System.out.println("Empleados activos:");
            for (Empleado empleado : empleadosActivos) {
                System.out.println(empleado);
            }

            // Test buscarEmpleadoPorId
            String idEmpleado = empleadoCreado.getId();
            Empleado empleadoEncontrado = empleadoDAO.buscarEmpleadoPorId(idEmpleado);
            System.out.println("Empleado encontrado con ID " + idEmpleado + ": " + empleadoEncontrado);

            // Test filtrarEmpleadosPorRol
            List<Empleado> empleadosPorRol = empleadoDAO.filtrarEmpleadosPorRol(RolEmpleado.CAJERO);
            System.out.println("Empleados con rol CAJERO:");
            for (Empleado empleado : empleadosPorRol) {
                System.out.println(empleado);
            }

            // Test listarEmpleadosPorSucursal
            List<Empleado> empleadosPorSucursal = empleadoDAO.listarEmpleadosPorSucursal("SUC-001");
            System.out.println("Empleados en la sucursal SUC-001:");
            for (Empleado empleado : empleadosPorSucursal) {
                System.out.println(empleado);
            }

            // Test actualizarEmpleado
            nuevoEmpleado.setHorarioTrabajo("10:00 AM - 6:00 PM");
            boolean actualizado = empleadoDAO.actualizarEmpleado(idEmpleado, nuevoEmpleado);
            System.out.println("Empleado actualizado: " + actualizado);

            // Test eliminarEmpleado
            boolean eliminado = empleadoDAO.eliminarEmpleado(idEmpleado);
            System.out.println("Empleado eliminado: " + eliminado);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}