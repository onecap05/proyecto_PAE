package com.eurobank.models.DAO;

import com.eurobank.models.Cliente;
import com.eurobank.models.Cuenta;
import com.eurobank.models.TipoCuenta;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuentaDAOTest {

    private static final String ARCHIVO_CUENTAS = "src/main/resources/data/cuentas.json";
    private static final String ARCHIVO_CUENTAS_BK = "src/main/resources/data/cuentas_backup.json";
    private static final String ARCHIVO_CLIENTES = "src/main/resources/data/clientes.json";
    private static final String ARCHIVO_CLIENTES_BK = "src/main/resources/data/clientes_backup.json";

    private ClienteDAO clienteDAO;
    private CuentaDAO cuentaDAO;
    private Cliente clientePrueba;

    @BeforeEach
    void setUp() throws IOException {
        if (new File(ARCHIVO_CUENTAS).exists()) {
            Files.copy(Paths.get(ARCHIVO_CUENTAS), Paths.get(ARCHIVO_CUENTAS_BK), StandardCopyOption.REPLACE_EXISTING);
        }
        if (new File(ARCHIVO_CLIENTES).exists()) {
            Files.copy(Paths.get(ARCHIVO_CLIENTES), Paths.get(ARCHIVO_CLIENTES_BK), StandardCopyOption.REPLACE_EXISTING);
        }
        new File(ARCHIVO_CUENTAS).delete();

        clienteDAO = new ClienteDAO();
        cuentaDAO = new CuentaDAO();

        clientePrueba = new Cliente("RFC_TEST", "Test", "User", "Mexicana", LocalDate.of(1990, 1, 1), "Calle Test", "5550000000", "test@mail.com");
        clienteDAO.guardarClientes(List.of(clientePrueba));
    }

    @AfterEach
    void tearDown() throws IOException {
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

    @Test
    void crearCuenta() throws IOException {
        Cuenta cuenta = cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 1000.0, 0.0, clientePrueba.getIdFiscal());
        assertNotNull(cuenta);
        assertEquals(TipoCuenta.AHORROS, cuenta.getTipo());
        assertEquals(clientePrueba.getIdFiscal(), cuenta.getIdCliente());
    }

    @Test
    void guardarCuentasYCargarCuentas() throws IOException {
        Cuenta cuenta = cuentaDAO.crearCuenta(TipoCuenta.CORRIENTE, 500.0, 0.0, clientePrueba.getIdFiscal());
        List<Cuenta> cuentas = cuentaDAO.cargarCuentas();
        assertTrue(cuentas.stream().anyMatch(c -> c.getNumeroCuenta().equals(cuenta.getNumeroCuenta())));
    }

    @Test
    void buscarCuentaPorNumero() throws IOException {
        Cuenta cuenta = cuentaDAO.crearCuenta(TipoCuenta.EMPRESARIAL, 2000.0, 5000.0, clientePrueba.getIdFiscal());
        Cuenta encontrada = cuentaDAO.buscarCuentaPorNumero(cuenta.getNumeroCuenta());
        assertNotNull(encontrada);
        assertEquals(cuenta.getNumeroCuenta(), encontrada.getNumeroCuenta());
    }

    @Test
    void listarCuentasPorCliente() throws IOException {
        cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 100.0, 0.0, clientePrueba.getIdFiscal());
        cuentaDAO.crearCuenta(TipoCuenta.CORRIENTE, 200.0, 0.0, clientePrueba.getIdFiscal());
        List<Cuenta> cuentas = cuentaDAO.listarCuentasPorCliente(clientePrueba.getIdFiscal());
        assertEquals(2, cuentas.size());
    }

    @Test
    void filtrarCuentasPorTipo() throws IOException {
        cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 100.0, 0.0, clientePrueba.getIdFiscal());
        cuentaDAO.crearCuenta(TipoCuenta.CORRIENTE, 200.0, 0.0, clientePrueba.getIdFiscal());
        List<Cuenta> cuentasAhorro = cuentaDAO.filtrarCuentasPorTipo(TipoCuenta.AHORROS);
        assertTrue(cuentasAhorro.stream().allMatch(c -> c.getTipo() == TipoCuenta.AHORROS));
    }

    @Test
    void listarCuentasActivas() throws IOException {
        cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 100.0, 0.0, clientePrueba.getIdFiscal());
        cuentaDAO.crearCuenta(TipoCuenta.EMPRESARIAL, 200.0, 1000.0, clientePrueba.getIdFiscal());
        List<Cuenta> activas = cuentaDAO.listarCuentasActivas();
        assertEquals(2, activas.size());
    }

    @Test
    void eliminarCuenta() throws IOException {
        Cuenta cuenta = cuentaDAO.crearCuenta(TipoCuenta.CORRIENTE, 100.0, 0.0, clientePrueba.getIdFiscal());
        boolean eliminado = cuentaDAO.eliminarCuenta(cuenta.getNumeroCuenta());
        assertTrue(eliminado);
        Cuenta inactiva = cuentaDAO.buscarCuentaPorNumero(cuenta.getNumeroCuenta());
        assertFalse(inactiva.isEstadoActivo());
    }

    @Test
    void actualizarCuenta() throws IOException {
        Cuenta cuenta = cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 100.0, 0.0, clientePrueba.getIdFiscal());
        Cuenta actualizada = new Cuenta(cuenta.getNumeroCuenta(), TipoCuenta.AHORROS, 999.0, 0.0, clientePrueba.getIdFiscal());
        actualizada.setEstadoActivo(true);
        boolean actualizado = cuentaDAO.actualizarCuenta(cuenta.getNumeroCuenta(), actualizada);
        assertTrue(actualizado);
        Cuenta consultada = cuentaDAO.buscarCuentaPorNumero(cuenta.getNumeroCuenta());
        assertEquals(999.0, consultada.getSaldo());
    }

    @Test
    void buscarCuentaInactiva() throws IOException {
        Cuenta cuenta = cuentaDAO.crearCuenta(TipoCuenta.CORRIENTE, 100.0, 0.0, clientePrueba.getIdFiscal());
        cuentaDAO.eliminarCuenta(cuenta.getNumeroCuenta());
        Cuenta inactiva = cuentaDAO.buscarCuentaPorNumero(cuenta.getNumeroCuenta());
        assertNotNull(inactiva);
        assertFalse(inactiva.isEstadoActivo());
    }

    @Test
    void actualizarSaldoCuenta() throws IOException {
        Cuenta cuenta = cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 100.0, 0.0, clientePrueba.getIdFiscal());
        cuenta.setSaldo(555.5);
        boolean actualizado = cuentaDAO.actualizarCuenta(cuenta.getNumeroCuenta(), cuenta);
        assertTrue(actualizado);
        Cuenta consultada = cuentaDAO.buscarCuentaPorNumero(cuenta.getNumeroCuenta());
        assertEquals(555.5, consultada.getSaldo());
    }

    @Test
    void listarTodasLasCuentasIncluyendoInactivas() throws IOException {
        Cuenta c1 = cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 100.0, 0.0, clientePrueba.getIdFiscal());
        Cuenta c2 = cuentaDAO.crearCuenta(TipoCuenta.CORRIENTE, 200.0, 0.0, clientePrueba.getIdFiscal());
        cuentaDAO.eliminarCuenta(c2.getNumeroCuenta());
        List<Cuenta> todas = cuentaDAO.cargarCuentas();
        assertEquals(2, todas.size());
        assertTrue(todas.stream().anyMatch(c -> !c.isEstadoActivo()));
    }

    @Test
    void buscarCuentasPorTipoYCliente() throws IOException {
        cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 100.0, 0.0, clientePrueba.getIdFiscal());
        cuentaDAO.crearCuenta(TipoCuenta.AHORROS, 200.0, 0.0, "OTRO_CLIENTE");
        List<Cuenta> cuentas = cuentaDAO.filtrarCuentasPorTipo(TipoCuenta.AHORROS);
        assertTrue(cuentas.stream().anyMatch(c -> c.getIdCliente().equals(clientePrueba.getIdFiscal())));
    }

    @Test
    void eliminarCuentaInexistenteDevuelveFalse() throws IOException {
        boolean eliminado = cuentaDAO.eliminarCuenta("CUENTA-NO-EXISTE");
        assertFalse(eliminado);
    }

    @Test
    void guardarCuentasLanzaIOException() throws IOException {
        CuentaDAO dao = new CuentaDAO() {
            @Override
            public void guardarCuentas(List<Cuenta> cuentas) throws IOException {
                throw new IOException("Simulación de error de escritura");
            }
        };
        List<Cuenta> lista = List.of(new Cuenta("CUENTA-1", TipoCuenta.AHORROS, 100.0, 0.0, "RFC_TEST"));
        assertThrows(IOException.class, () -> dao.guardarCuentas(lista));
    }
}