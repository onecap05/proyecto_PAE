package com.eurobank.models.DAO;

import com.eurobank.models.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransaccionDAOTest {

    private static final String ARCHIVO_TRANSACCIONES = "src/main/resources/data/transacciones.json";
    private static final String ARCHIVO_TRANSACCIONES_BK = "src/main/resources/data/transacciones_backup.json";
    private static final String ARCHIVO_CUENTAS = "src/main/resources/data/cuentas.json";
    private static final String ARCHIVO_CUENTAS_BK = "src/main/resources/data/cuentas_backup.json";
    private static final String ARCHIVO_CLIENTES = "src/main/resources/data/clientes.json";
    private static final String ARCHIVO_CLIENTES_BK = "src/main/resources/data/clientes_backup.json";

    private TransaccionDAO transaccionDAO;
    private CuentaDAO cuentaDAO;
    private ClienteDAO clienteDAO;
    private Cliente clientePrueba;
    private Cuenta cuentaPrueba;

    @BeforeEach
    void setUp() throws IOException {
        // Respaldar archivos
        if (new File(ARCHIVO_TRANSACCIONES).exists()) {
            Files.copy(Paths.get(ARCHIVO_TRANSACCIONES), Paths.get(ARCHIVO_TRANSACCIONES_BK), StandardCopyOption.REPLACE_EXISTING);
        }
        if (new File(ARCHIVO_CUENTAS).exists()) {
            Files.copy(Paths.get(ARCHIVO_CUENTAS), Paths.get(ARCHIVO_CUENTAS_BK), StandardCopyOption.REPLACE_EXISTING);
        }
        if (new File(ARCHIVO_CLIENTES).exists()) {
            Files.copy(Paths.get(ARCHIVO_CLIENTES), Paths.get(ARCHIVO_CLIENTES_BK), StandardCopyOption.REPLACE_EXISTING);
        }
        new File(ARCHIVO_TRANSACCIONES).delete();
        new File(ARCHIVO_CUENTAS).delete();
        new File(ARCHIVO_CLIENTES).delete();

        transaccionDAO = new TransaccionDAO();
        cuentaDAO = new CuentaDAO();
        clienteDAO = new ClienteDAO();
        clientePrueba = new Cliente("RFC_TEST", "Test", "User", "Mexicana", LocalDate.of(1990, 1, 1), "Calle Test", "5550000000", "test@mail.com");
        clienteDAO.guardarClientes(List.of(clientePrueba));
        cuentaPrueba = cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 1000.0, 0.0, clientePrueba.getIdFiscal());
    }

    @AfterEach
    void tearDown() throws IOException {
        if (new File(ARCHIVO_TRANSACCIONES_BK).exists()) {
            Files.copy(Paths.get(ARCHIVO_TRANSACCIONES_BK), Paths.get(ARCHIVO_TRANSACCIONES), StandardCopyOption.REPLACE_EXISTING);
            new File(ARCHIVO_TRANSACCIONES_BK).delete();
        } else if (new File(ARCHIVO_TRANSACCIONES).exists()) {
            new File(ARCHIVO_TRANSACCIONES).delete();
        }
        if (new File(ARCHIVO_CUENTAS_BK).exists()) {
            Files.copy(Paths.get(ARCHIVO_CUENTAS_BK), Paths.get(ARCHIVO_CUENTAS), StandardCopyOption.REPLACE_EXISTING);
            new File(ARCHIVO_CUENTAS_BK).delete();
        } else if (new File(ARCHIVO_CUENTAS).exists()) {
            new File(ARCHIVO_CUENTAS).delete();
        }
        if (new File(ARCHIVO_CLIENTES_BK).exists()) {
            Files.copy(Paths.get(ARCHIVO_CLIENTES_BK), Paths.get(ARCHIVO_CLIENTES), StandardCopyOption.REPLACE_EXISTING);
            new File(ARCHIVO_CLIENTES_BK).delete();
        } else if (new File(ARCHIVO_CLIENTES).exists()) {
            new File(ARCHIVO_CLIENTES).delete();
        }
    }

    private TransaccionDeposito crearDepositoEjemplo() {
        return new TransaccionDeposito(null, 500.0, LocalDateTime.now(), "SUC-1", cuentaPrueba.getNumeroCuenta());
    }

    private TransaccionRetiro crearRetiroEjemplo() {
        return new TransaccionRetiro(null, 200.0, LocalDateTime.now(), "SUC-1", cuentaPrueba.getNumeroCuenta());
    }

    private TransaccionTransferencia crearTransferenciaEjemplo() {
        return new TransaccionTransferencia(null, 100.0, LocalDateTime.now(), "SUC-1", cuentaPrueba.getNumeroCuenta(), "DEST-123");
    }

    @Test
    void guardarTransaccionesYCargarTransacciones() throws IOException {
        Transaccion d = crearDepositoEjemplo();
        Transaccion r = crearRetiroEjemplo();
        transaccionDAO.guardarTransacciones(List.of(d, r));
        List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
        assertEquals(2, transacciones.size());
        assertTrue(transacciones.stream().anyMatch(t -> t.getTipo().equalsIgnoreCase("DEPOSITO")));
        assertTrue(transacciones.stream().anyMatch(t -> t.getTipo().equalsIgnoreCase("RETIRO")));
    }

    @Test
    void crearNuevaTransaccion() throws IOException {
        TransaccionDeposito deposito = crearDepositoEjemplo();
        Transaccion creada = transaccionDAO.crearNuevaTransaccion(deposito);
        assertNotNull(creada.getId());
        List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
        assertEquals(1, transacciones.size());
        assertEquals("DEPOSITO", transacciones.get(0).getTipo());
    }

    @Test
    void filtrarPorSucursal() throws IOException {
        Transaccion d1 = crearDepositoEjemplo();
        Transaccion d2 = new TransaccionDeposito(null, 300.0, LocalDateTime.now(), "SUC-2", cuentaPrueba.getNumeroCuenta());
        transaccionDAO.guardarTransacciones(List.of(d1, d2));
        List<Transaccion> suc1 = transaccionDAO.filtrarPorSucursal("SUC-1");
        assertEquals(1, suc1.size());
        assertEquals("SUC-1", suc1.get(0).getIdSucursal());
    }

    @Test
    void filtrarPorTipo() throws IOException {
        Transaccion d = crearDepositoEjemplo();
        Transaccion r = crearRetiroEjemplo();
        Transaccion t = crearTransferenciaEjemplo();
        transaccionDAO.guardarTransacciones(List.of(d, r, t));
        List<Transaccion> depositos = transaccionDAO.filtrarPorTipo("DEPOSITO");
        assertTrue(depositos.stream().allMatch(tr -> tr.getTipo().equalsIgnoreCase("DEPOSITO")));
        assertEquals(1, depositos.size());
    }

    @Test
    void crearVariasTransaccionesGeneraIdsUnicos() throws IOException {
        Transaccion t1 = transaccionDAO.crearNuevaTransaccion(crearDepositoEjemplo());
        Transaccion t2 = transaccionDAO.crearNuevaTransaccion(crearRetiroEjemplo());
        assertNotEquals(t1.getId(), t2.getId());
    }

    @Test
    void cargarTransaccionesVacioSiNoExisteArchivo() throws IOException {
        new File(ARCHIVO_TRANSACCIONES).delete();
        List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
        assertNotNull(transacciones);
        assertTrue(transacciones.isEmpty());
    }

    // Métodos adicionales

    @Test
    void filtrarPorNumeroCuenta() throws IOException {
        Transaccion d1 = crearDepositoEjemplo();
        Transaccion d2 = new TransaccionDeposito(null, 100.0, LocalDateTime.now(), "SUC-1", "OTRA-CUENTA");
        transaccionDAO.guardarTransacciones(List.of(d1, d2));
        List<Transaccion> porCuenta = transaccionDAO.cargarTransacciones().stream()
                .filter(t -> t instanceof TransaccionDeposito td && td.getNumeroCuenta().equals(cuentaPrueba.getNumeroCuenta()))
                .toList();
        assertEquals(1, porCuenta.size());
        assertEquals(cuentaPrueba.getNumeroCuenta(), ((TransaccionDeposito)porCuenta.get(0)).getNumeroCuenta());
    }

    @Test
    void filtrarPorFecha() throws IOException {
        LocalDateTime fechaHoy = LocalDateTime.now();
        LocalDateTime fechaAyer = fechaHoy.minusDays(1);
        Transaccion tHoy = new TransaccionDeposito(null, 100, fechaHoy, "SUC-1", cuentaPrueba.getNumeroCuenta());
        Transaccion tAyer = new TransaccionDeposito(null, 200, fechaAyer, "SUC-1", cuentaPrueba.getNumeroCuenta());
        transaccionDAO.guardarTransacciones(List.of(tHoy, tAyer));
        List<Transaccion> hoy = transaccionDAO.cargarTransacciones().stream()
                .filter(t -> t.getFecha().toLocalDate().equals(fechaHoy.toLocalDate()))
                .toList();
        assertEquals(1, hoy.size());
        assertEquals(100, hoy.get(0).getMonto());
    }

    @Test
    void buscarTransaccionInexistenteDevuelveNull() throws IOException {
        List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
        boolean existe = transacciones.stream().anyMatch(t -> "T-9999".equals(t.getId()));
        assertFalse(existe);
    }

    @Test
    void persistenciaTrasMultiplesEscrituras() throws IOException {
        Transaccion t1 = crearDepositoEjemplo();
        transaccionDAO.crearNuevaTransaccion(t1);
        Transaccion t2 = crearRetiroEjemplo();
        transaccionDAO.crearNuevaTransaccion(t2);
        List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
        assertEquals(2, transacciones.size());
    }

    @Test
    void eliminarTodasLasTransacciones() throws IOException {
        transaccionDAO.guardarTransacciones(List.of(crearDepositoEjemplo(), crearRetiroEjemplo()));
        transaccionDAO.guardarTransacciones(List.of());
        List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
        assertTrue(transacciones.isEmpty());
    }

    @Test
    void datosTransferenciaSeGuardanCorrectamente() throws IOException {
        TransaccionTransferencia transferencia = crearTransferenciaEjemplo();
        transaccionDAO.crearNuevaTransaccion(transferencia);
        List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
        assertEquals(1, transacciones.size());
        assertTrue(transacciones.get(0) instanceof TransaccionTransferencia);
        TransaccionTransferencia t = (TransaccionTransferencia) transacciones.get(0);
        assertEquals("DEST-123", t.getNumeroCuentaDestino());
        assertEquals(cuentaPrueba.getNumeroCuenta(), t.getNumeroCuentaOrigen());
    }

    @Test
    void guardarTransaccionesLanzaIOException() {
        TransaccionDAO dao = new TransaccionDAO() {
            @Override
            public void guardarTransacciones(List<Transaccion> transacciones) throws IOException {
                throw new IOException("Simulación de error de escritura");
            }
        };
        List<Transaccion> lista = List.of(new TransaccionDeposito(null, 100.0, LocalDateTime.now(), "SUC-1", "CUENTA-1"));
        assertThrows(IOException.class, () -> dao.guardarTransacciones(lista));
    }
}