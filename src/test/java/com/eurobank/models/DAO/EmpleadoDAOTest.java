package com.eurobank.models.DAO;

import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoDAOTest {

    private static final String ARCHIVO_EMPLEADOS = "src/main/resources/data/empleados.json";
    private static final String ARCHIVO_EMPLEADOS_BK = "src/main/resources/data/empleados_backup.json";

    private EmpleadoDAO empleadoDAO;

    @BeforeEach
    void setUp() throws IOException {
        if (new File(ARCHIVO_EMPLEADOS).exists()) {
            Files.copy(Paths.get(ARCHIVO_EMPLEADOS), Paths.get(ARCHIVO_EMPLEADOS_BK), StandardCopyOption.REPLACE_EXISTING);
        }
        new File(ARCHIVO_EMPLEADOS).delete();
        empleadoDAO = new EmpleadoDAO();
    }

    @AfterEach
    void tearDown() throws IOException {
        if (new File(ARCHIVO_EMPLEADOS_BK).exists()) {
            Files.copy(Paths.get(ARCHIVO_EMPLEADOS_BK), Paths.get(ARCHIVO_EMPLEADOS), StandardCopyOption.REPLACE_EXISTING);
            new File(ARCHIVO_EMPLEADOS_BK).delete();
        } else if (new File(ARCHIVO_EMPLEADOS).exists()) {
            new File(ARCHIVO_EMPLEADOS).delete();
        }
    }

    private Empleado crearCajeroEjemplo() {
        Empleado cajero = new Empleado(null, "Cajero Uno", "Calle 1", LocalDate.of(1990, 1, 1), "M", 10000, RolEmpleado.CAJERO, "cajero1", "pass");
        cajero.setHorarioTrabajo("09:00-18:00");
        cajero.setNumeroVentanilla(2);
        cajero.setIdSucursal("SUC-1");
        return cajero;
    }

    private Empleado crearEjecutivoEjemplo() {
        Empleado ejecutivo = new Empleado(null, "Ejecutivo Uno", "Calle 2", LocalDate.of(1985, 5, 5), "F", 15000, RolEmpleado.EJECUTIVO_CUENTA, "ejecutivo1", "pass");
        ejecutivo.setClientesAsignados(10);
        ejecutivo.setEspecializacion("PYMES");
        ejecutivo.setIdSucursal("SUC-1");
        return ejecutivo;
    }

    private Empleado crearGerenteEjemplo() {
        Empleado gerente = new Empleado(null, "Gerente Uno", "Calle 3", LocalDate.of(1980, 3, 3), "M", 20000, RolEmpleado.GERENTE, "gerente1", "pass");
        gerente.setNivelAcceso("sucursal");
        gerente.setAnosExperiencia(8);
        gerente.setIdSucursal("SUC-1");
        return gerente;
    }

    @Test
    void guardarYcargarEmpleados() throws IOException {
        Empleado cajero = crearCajeroEjemplo();
        Empleado ejecutivo = crearEjecutivoEjemplo();
        empleadoDAO.guardarEmpleados(List.of(cajero, ejecutivo));
        List<Empleado> empleados = empleadoDAO.cargarEmpleados();
        assertEquals(2, empleados.size());
        assertTrue(empleados.stream().anyMatch(e -> e.getNombre().equals("Cajero Uno")));
        assertTrue(empleados.stream().anyMatch(e -> e.getNombre().equals("Ejecutivo Uno")));
    }

    @Test
    void crearNuevoEmpleado() throws IOException {
        Empleado gerente = crearGerenteEjemplo();
        Empleado creado = empleadoDAO.crearNuevoEmpleado(gerente);
        assertNotNull(creado.getId());
        List<Empleado> empleados = empleadoDAO.cargarEmpleados();
        assertEquals(1, empleados.size());
        assertEquals("Gerente Uno", empleados.get(0).getNombre());
    }

    @Test
    void buscarEmpleadoPorId() throws IOException {
        Empleado cajero = empleadoDAO.crearNuevoEmpleado(crearCajeroEjemplo());
        Empleado encontrado = empleadoDAO.buscarEmpleadoPorId(cajero.getId());
        assertNotNull(encontrado);
        assertEquals("Cajero Uno", encontrado.getNombre());
    }

    @Test
    void filtrarEmpleadosPorRol() throws IOException {
        empleadoDAO.crearNuevoEmpleado(crearCajeroEjemplo());
        empleadoDAO.crearNuevoEmpleado(crearEjecutivoEjemplo());
        List<Empleado> cajeros = empleadoDAO.filtrarEmpleadosPorRol(RolEmpleado.CAJERO);
        assertEquals(1, cajeros.size());
        assertEquals(RolEmpleado.CAJERO, cajeros.get(0).getRol());
    }

    @Test
    void eliminarEmpleado() throws IOException {
        Empleado ejecutivo = empleadoDAO.crearNuevoEmpleado(crearEjecutivoEjemplo());
        boolean eliminado = empleadoDAO.eliminarEmpleado(ejecutivo.getId());
        assertTrue(eliminado);
        Empleado consultado = empleadoDAO.buscarEmpleadoPorId(ejecutivo.getId());
        assertNotNull(consultado);
        assertFalse(consultado.isEstadoActivo());
    }

    @Test
    void actualizarEmpleado() throws IOException {
        Empleado cajero = empleadoDAO.crearNuevoEmpleado(crearCajeroEjemplo());
        cajero.setNombre("Cajero Modificado");
        boolean actualizado = empleadoDAO.actualizarEmpleado(cajero.getId(), cajero);
        assertTrue(actualizado);
        Empleado consultado = empleadoDAO.buscarEmpleadoPorId(cajero.getId());
        assertEquals("Cajero Modificado", consultado.getNombre());
    }

    @Test
    void listarEmpleadosActivos() throws IOException {
        Empleado cajero = empleadoDAO.crearNuevoEmpleado(crearCajeroEjemplo());
        Empleado ejecutivo = empleadoDAO.crearNuevoEmpleado(crearEjecutivoEjemplo());
        empleadoDAO.eliminarEmpleado(ejecutivo.getId());
        List<Empleado> activos = empleadoDAO.listarEmpleadosActivos();
        assertEquals(1, activos.size());
        assertEquals("Cajero Uno", activos.get(0).getNombre());
    }

    @Test
    void listarEmpleadosPorSucursal() throws IOException {
        Empleado cajero = crearCajeroEjemplo();
        cajero.setIdSucursal("SUC-2");
        Empleado ejecutivo = crearEjecutivoEjemplo();
        ejecutivo.setIdSucursal("SUC-2");
        empleadoDAO.guardarEmpleados(List.of(cajero, ejecutivo));
        List<Empleado> empleadosSucursal = empleadoDAO.listarEmpleadosPorSucursal("SUC-2");
        assertEquals(2, empleadosSucursal.size());
    }

    @Test
    void validarCamposObligatoriosPorRol() {
        Empleado cajero = new Empleado(null, "Cajero Inválido", "Calle X", LocalDate.now(), "M", 9000, RolEmpleado.CAJERO, "cajeroX", "pass");
        cajero.setIdSucursal("SUC-1");
        // No se setean horarioTrabajo ni numeroVentanilla
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            empleadoDAO.guardarEmpleados(List.of(cajero));
        });
        assertTrue(ex.getMessage().contains("horarioTrabajo y numeroVentanilla"));
    }

    @Test
    void crearVariosEmpleadosGeneraIdsUnicos() throws IOException {
        Empleado e1 = empleadoDAO.crearNuevoEmpleado(crearCajeroEjemplo());
        Empleado e2 = empleadoDAO.crearNuevoEmpleado(crearEjecutivoEjemplo());
        assertNotEquals(e1.getId(), e2.getId());
    }

    @Test
    void eliminarEmpleadoInexistenteDevuelveFalse() throws IOException {
        boolean eliminado = empleadoDAO.eliminarEmpleado("E-999");
        assertFalse(eliminado);
    }

    @Test
    void actualizarEmpleadoInexistenteDevuelveFalse() throws IOException {
        Empleado e = crearCajeroEjemplo();
        e.setId("E-999");
        boolean actualizado = empleadoDAO.actualizarEmpleado("E-999", e);
        assertFalse(actualizado);
    }
}