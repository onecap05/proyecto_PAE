package com.eurobank.models.DAO;

import com.eurobank.models.Sucursal;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SucursalDAOTest {

    private static final String ARCHIVO_SUCURSALES = "src/main/resources/data/sucursales.json";
    private static final String ARCHIVO_SUCURSALES_BK = "src/main/resources/data/sucursales_backup.json";

    private SucursalDAO sucursalDAO;

    @BeforeEach
    void setUp() throws IOException {
        if (new File(ARCHIVO_SUCURSALES).exists()) {
            Files.copy(Paths.get(ARCHIVO_SUCURSALES), Paths.get(ARCHIVO_SUCURSALES_BK), StandardCopyOption.REPLACE_EXISTING);
        }
        new File(ARCHIVO_SUCURSALES).delete();
        sucursalDAO = new SucursalDAO();
    }

    @AfterEach
    void tearDown() throws IOException {
        if (new File(ARCHIVO_SUCURSALES_BK).exists()) {
            Files.copy(Paths.get(ARCHIVO_SUCURSALES_BK), Paths.get(ARCHIVO_SUCURSALES), StandardCopyOption.REPLACE_EXISTING);
            new File(ARCHIVO_SUCURSALES_BK).delete();
        } else if (new File(ARCHIVO_SUCURSALES).exists()) {
            new File(ARCHIVO_SUCURSALES).delete();
        }
    }

    @Test
    void guardarSucursalesYCargarSucursales() throws IOException {
        Sucursal s1 = new Sucursal(null, "Sucursal Centro", "CDMX", "5551234567", "centro@mail.com", "G001");
        Sucursal s2 = new Sucursal(null, "Sucursal Norte", "Monterrey", "8181234567", "norte@mail.com", "G002");
        sucursalDAO.guardarSucursales(List.of(s1, s2));
        List<Sucursal> sucursales = sucursalDAO.cargarSucursales();
        assertEquals(2, sucursales.size());
        assertTrue(sucursales.stream().anyMatch(s -> s.getNombre().equals("Sucursal Centro")));
        assertEquals("5551234567", sucursales.get(0).getTelefono());
        assertEquals("centro@mail.com", sucursales.get(0).getCorreo());
        assertEquals("G001", sucursales.get(0).getIdGerente());
    }

    @Test
    void crearNuevaSucursal() throws IOException {
        Sucursal nueva = new Sucursal(null, "Sucursal Sur", "Guadalajara", "3331234567", "sur@mail.com", "G003");
        nueva.setContacto("Juan Pérez");
        Sucursal creada = sucursalDAO.crearNuevaSucursal(nueva);
        assertNotNull(creada.getId());
        assertTrue(creada.isEstadoActivo());
        List<Sucursal> sucursales = sucursalDAO.cargarSucursales();
        assertEquals(1, sucursales.size());
        assertEquals("Sucursal Sur", sucursales.get(0).getNombre());
        assertEquals("3331234567", sucursales.get(0).getTelefono());
        assertEquals("sur@mail.com", sucursales.get(0).getCorreo());
        assertEquals("G003", sucursales.get(0).getIdGerente());
        assertEquals("Juan Pérez", sucursales.get(0).getContacto());
    }

    @Test
    void buscarSucursalPorId() throws IOException {
        Sucursal nueva = new Sucursal(null, "Sucursal Este", "Cancún", "9981234567", "este@mail.com", "G004");
        Sucursal creada = sucursalDAO.crearNuevaSucursal(nueva);
        Sucursal encontrada = sucursalDAO.buscarSucursalPorId(creada.getId());
        assertNotNull(encontrada);
        assertEquals("Sucursal Este", encontrada.getNombre());
        assertEquals("9981234567", encontrada.getTelefono());
        assertEquals("este@mail.com", encontrada.getCorreo());
        assertEquals("G004", encontrada.getIdGerente());
    }

    @Test
    void buscarSucursalPorIdInexistenteDevuelveNull() throws IOException {
        Sucursal encontrada = sucursalDAO.buscarSucursalPorId("S999");
        assertNull(encontrada);
    }

    @Test
    void listarSucursalesActivas() throws IOException {
        Sucursal s1 = new Sucursal(null, "Sucursal Activa", "CDMX", "5550000000", "activa@mail.com", "G005");
        Sucursal s2 = new Sucursal(null, "Sucursal Inactiva", "Puebla", "2220000000", "inactiva@mail.com", "G006");
        s2.setEstadoActivo(false);
        sucursalDAO.guardarSucursales(List.of(s1, s2));
        List<Sucursal> activas = sucursalDAO.listarSucursalesActivas();
        assertEquals(1, activas.size());
        assertEquals("Sucursal Activa", activas.get(0).getNombre());
    }

    @Test
    void listarSucursalesInactivas() throws IOException {
        Sucursal s1 = new Sucursal(null, "Sucursal Activa", "CDMX", "5550000000", "activa@mail.com", "G005");
        Sucursal s2 = new Sucursal(null, "Sucursal Inactiva", "Puebla", "2220000000", "inactiva@mail.com", "G006");
        s2.setEstadoActivo(false);
        sucursalDAO.guardarSucursales(List.of(s1, s2));
        List<Sucursal> inactivas = sucursalDAO.cargarSucursales().stream()
                .filter(s -> !s.isEstadoActivo())
                .collect(Collectors.toList());
        assertEquals(1, inactivas.size());
        assertEquals("Sucursal Inactiva", inactivas.get(0).getNombre());
    }

    @Test
    void actualizarSucursal() throws IOException {
        Sucursal s1 = new Sucursal(null, "Sucursal Original", "CDMX", "5551111111", "original@mail.com", "G007");
        Sucursal creada = sucursalDAO.crearNuevaSucursal(s1);
        Sucursal actualizada = new Sucursal(creada.getId(), "Sucursal Modificada", "CDMX", "5552222222", "modificada@mail.com", "G008");
        actualizada.setEstadoActivo(true);
        actualizada.setContacto("Ana López");
        boolean actualizado = sucursalDAO.actualizarSucursal(creada.getId(), actualizada);
        assertTrue(actualizado);
        Sucursal consultada = sucursalDAO.buscarSucursalPorId(creada.getId());
        assertEquals("Sucursal Modificada", consultada.getNombre());
        assertEquals("5552222222", consultada.getTelefono());
        assertEquals("modificada@mail.com", consultada.getCorreo());
        assertEquals("G008", consultada.getIdGerente());
        assertEquals("Ana López", consultada.getContacto());
    }

    @Test
    void actualizarSucursalInexistenteDevuelveFalse() throws IOException {
        Sucursal s = new Sucursal("S999", "No existe", "CDMX", "000", "no@mail.com", "G000");
        boolean actualizado = sucursalDAO.actualizarSucursal("S999", s);
        assertFalse(actualizado);
    }

    @Test
    void eliminarSucursal() throws IOException {
        Sucursal s1 = new Sucursal(null, "Sucursal a Eliminar", "CDMX", "5553333333", "eliminar@mail.com", "G009");
        Sucursal creada = sucursalDAO.crearNuevaSucursal(s1);
        boolean eliminado = sucursalDAO.eliminarSucursal(creada.getId());
        assertTrue(eliminado);
        Sucursal consultada = sucursalDAO.buscarSucursalPorId(creada.getId());
        assertNotNull(consultada);
        assertFalse(consultada.isEstadoActivo());
    }

    @Test
    void eliminarSucursalInexistenteDevuelveFalse() throws IOException {
        boolean eliminado = sucursalDAO.eliminarSucursal("S999");
        assertFalse(eliminado);
    }

    @Test
    void listarSucursales() throws IOException {
        Sucursal s1 = new Sucursal(null, "Sucursal 1", "CDMX", "5554444444", "s1@mail.com", "G010");
        Sucursal s2 = new Sucursal(null, "Sucursal 2", "Monterrey", "8185555555", "s2@mail.com", "G011");
        sucursalDAO.guardarSucursales(List.of(s1, s2));
        List<Sucursal> todas = sucursalDAO.listarSucursales();
        assertEquals(2, todas.size());
    }

    @Test
    void crearVariasSucursalesGeneraIdsUnicos() throws IOException {
        Sucursal s1 = new Sucursal(null, "Sucursal A", "CDMX", "5550000001", "a@mail.com", "G012");
        Sucursal s2 = new Sucursal(null, "Sucursal B", "CDMX", "5550000002", "b@mail.com", "G013");
        Sucursal creada1 = sucursalDAO.crearNuevaSucursal(s1);
        Sucursal creada2 = sucursalDAO.crearNuevaSucursal(s2);
        assertNotEquals(creada1.getId(), creada2.getId());
    }

    @Test
    void contactoSeGuardaYRecuperaCorrectamente() throws IOException {
        Sucursal s = new Sucursal(null, "Sucursal Contacto", "CDMX", "5559999999", "contacto@mail.com", "G014");
        s.setContacto("Luis Ramírez");
        Sucursal creada = sucursalDAO.crearNuevaSucursal(s);
        Sucursal consultada = sucursalDAO.buscarSucursalPorId(creada.getId());
        assertEquals("Luis Ramírez", consultada.getContacto());
    }
}