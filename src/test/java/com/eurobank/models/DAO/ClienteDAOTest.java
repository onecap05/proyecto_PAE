package com.eurobank.models.DAO;

import com.eurobank.models.Cliente;
import com.eurobank.models.excepciones.ClienteNoEncontradoException;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDAOTest {

    private ClienteDAO clienteDAO;
    private static final String ARCHIVO_REAL = "src/main/resources/data/clientes.json";
    private static final String ARCHIVO_RESPALDO = "src/main/resources/data/clientes_backup.json";
    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    void setUp() throws IOException {
        File archivo = new File(ARCHIVO_REAL);
        if (archivo.exists()) {
            Files.copy(archivo.toPath(), Paths.get(ARCHIVO_RESPALDO), StandardCopyOption.REPLACE_EXISTING);
        }
        clienteDAO = new ClienteDAO();
        cliente1 = new Cliente("RFC1", "Juan", "Pérez", "Mexicana", LocalDate.of(1990, 1, 1), "Calle 1", "5551112222", "juan@mail.com");
        cliente2 = new Cliente("RFC2", "Ana", "López", "Mexicana", LocalDate.of(1985, 5, 5), "Calle 2", "5553334444", "ana@mail.com");
        clienteDAO.guardarClientes(List.of(cliente1, cliente2));
    }

    @AfterEach
    void tearDown() throws IOException {
        File respaldo = new File(ARCHIVO_RESPALDO);
        if (respaldo.exists()) {
            Files.copy(respaldo.toPath(), Paths.get(ARCHIVO_REAL), StandardCopyOption.REPLACE_EXISTING);
            respaldo.delete();
        } else {
            File archivo = new File(ARCHIVO_REAL);
            if (archivo.exists()) {
                archivo.delete();
            }
        }
    }

    @Test
    void guardarClientes() throws IOException {
        List<Cliente> clientes = clienteDAO.cargarClientes();
        assertEquals(2, clientes.size());
        assertEquals("Juan", clientes.get(0).getNombre());
    }

    @Test
    void cargarClientes() throws IOException {
        List<Cliente> clientes = clienteDAO.cargarClientes();
        assertFalse(clientes.isEmpty());
        assertEquals("RFC1", clientes.get(0).getIdFiscal());
    }

    @Test
    void cargarClientesArchivoVacio() throws IOException {
        File archivo = new File(ARCHIVO_REAL);
        if (archivo.exists()) archivo.delete();
        List<Cliente> clientes = clienteDAO.cargarClientes();
        assertTrue(clientes.isEmpty());
    }

    @Test
    void buscarClientePorIdFiscal() throws IOException {
        Cliente encontrado = clienteDAO.buscarClientePorIdFiscal("RFC2");
        assertNotNull(encontrado);
        assertEquals("Ana", encontrado.getNombre());
    }

    @Test
    void buscarClientePorIdFiscalNoExistente() throws IOException {
        Cliente encontrado = clienteDAO.buscarClientePorIdFiscal("RFC99");
        assertNull(encontrado);
    }

    @Test
    void eliminarCliente() throws IOException, ClienteNoEncontradoException {
        boolean eliminado = clienteDAO.eliminarCliente("RFC1");
        assertTrue(eliminado);
        Cliente cliente = clienteDAO.buscarClientePorIdFiscal("RFC1");
        assertFalse(cliente.isEstadoActivo());
    }

    @Test
    void eliminarClienteNoExistente() throws IOException {
        assertFalse(clienteDAO.eliminarCliente("RFC99"));
    }

    @Test
    void actualizarCliente() throws IOException, ClienteNoEncontradoException {
        Cliente actualizado = new Cliente("RFC1", "Juanito", "Pérez", "Mexicana", LocalDate.of(1990, 1, 1), "Calle 1", "5551112222", "juanito@mail.com");
        boolean actualizadoOk = clienteDAO.actualizarCliente("RFC1", actualizado);
        assertTrue(actualizadoOk);
        Cliente cliente = clienteDAO.buscarClientePorIdFiscal("RFC1");
        assertEquals("Juanito", cliente.getNombre());
    }

    @Test
    void actualizarClienteNoExistente() throws IOException, ClienteNoEncontradoException {
        Cliente clienteNoExistente = new Cliente("RFC99", "Carlos", "Gómez", "Mexicana", LocalDate.of(1995, 3, 3), "Calle 99", "5559998888", "carlos@mail.com");
        boolean actualizado = clienteDAO.actualizarCliente("RFC99", clienteNoExistente);
        assertFalse(actualizado);
    }

    @Test
    void filtrarClientesActivos() throws IOException, ClienteNoEncontradoException {
        clienteDAO.eliminarCliente("RFC2");
        List<Cliente> activos = clienteDAO.filtrarClientesActivos();
        assertEquals(1, activos.size());
        assertEquals("RFC1", activos.get(0).getIdFiscal());
    }

    @Test
    void filtrarClientesActivosConVariosEstados() throws IOException, ClienteNoEncontradoException {
        clienteDAO.eliminarCliente("RFC1");
        List<Cliente> activos = clienteDAO.filtrarClientesActivos();
        assertEquals(1, activos.size());
        assertEquals("RFC2", activos.get(0).getIdFiscal());
    }

    @Test
    void filtrarClientesActivosTodosInactivos() throws IOException, ClienteNoEncontradoException {
        clienteDAO.eliminarCliente("RFC1");
        clienteDAO.eliminarCliente("RFC2");
        List<Cliente> activos = clienteDAO.filtrarClientesActivos();
        assertTrue(activos.isEmpty());
    }

    @Test
    void crearCliente() throws IOException {
        Cliente nuevo = new Cliente("RFC3", "Luis", "Martínez", "Mexicana", LocalDate.of(2000, 2, 2), "Calle 3", "5556667777", "luis@mail.com");
        boolean creado = clienteDAO.crearCliente(nuevo);
        assertTrue(creado);
        Cliente encontrado = clienteDAO.buscarClientePorIdFiscal("RFC3");
        assertNotNull(encontrado);
        assertEquals("Luis", encontrado.getNombre());
    }

    @Test
    void guardarClientesLanzaIOException() {
        ClienteDAO dao = new ClienteDAO() {
            @Override
            public void guardarClientes(List<Cliente> clientes) throws IOException {
                throw new IOException("Simulación de error de escritura");
            }
        };
        List<Cliente> lista = List.of(new Cliente("RFCX", "X", "Y", "Nac", LocalDate.now(), "Calle", "123", "x@y.com"));
        assertThrows(IOException.class, () -> dao.guardarClientes(lista));
    }
}