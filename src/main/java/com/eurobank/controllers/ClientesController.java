package com.eurobank.controllers;

import com.eurobank.models.Cliente;
import com.eurobank.models.DAO.ClienteDAO;
import com.eurobank.models.DAO.CuentaDAO;
import com.eurobank.models.excepciones.ClienteNoEncontradoException;
import com.eurobank.utils.VentanasEmergentes;
import com.eurobank.views.ClientesView;
import com.eurobank.views.SaldosClienteView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ClientesController {
    private ClientesView view;
    private ClienteDAO clienteDAO;
    private CuentaDAO cuentaDAO;
    private Stage primaryStage;

    public ClientesController(ClientesView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
        try {
            this.clienteDAO = new ClienteDAO();
            this.cuentaDAO = new CuentaDAO();
            configurarEventos();
            cargarDatos();
        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al inicializar",
                    "No se pudo inicializar el controlador de clientes: " + e.getMessage());
        }
    }

    private void configurarEventos() {
        view.getBtnAgregar().setOnAction(e -> mostrarFormularioAgregar());
        view.getBtnEditar().setOnAction(e -> mostrarFormularioEditar());
        view.getBtnEliminar().setOnAction(e -> eliminarCliente());
        view.getBtnBuscar().setOnAction(e -> buscarClientes());
        view.getBtnConsultarSaldos().setOnAction(e -> consultarSaldos());
    }

    private void cargarDatos() {
        try {
            ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.filtrarClientesActivos());
            view.getClientesData().setAll(clientes);
        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al cargar datos",
                    "No se pudieron cargar los clientes: " + e.getMessage());
        }
    }

    private void buscarClientes() {

        try {

            String textoBusqueda = view.getTxtBusqueda().getText().toLowerCase();

            ObservableList<Cliente> clientesFiltrados = FXCollections.observableArrayList(
                    clienteDAO.filtrarClientesActivos().stream()
                            .filter(c ->
                                    c.getIdFiscal().toLowerCase().contains(textoBusqueda) ||
                                            c.getNombre().toLowerCase().contains(textoBusqueda) ||
                                            c.getApellidos().toLowerCase().contains(textoBusqueda) ||
                                            c.getEmail().toLowerCase().contains(textoBusqueda))
                            .toList()
            );

            if (clientesFiltrados.isEmpty()) {
                throw new ClienteNoEncontradoException(textoBusqueda);
            }

            view.getClientesData().setAll(clientesFiltrados);

        } catch (ClienteNoEncontradoException e) {
            VentanasEmergentes.mostrarAlerta("Cliente no encontrado", "Búsqueda fallida", e.getMessage());

        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al buscar",
                    "No se pudo realizar la búsqueda: " + e.getMessage());
        }
    }

    private void mostrarFormularioAgregar() {
        ClienteDialogController formularioController = new ClienteDialogController();
        formularioController.mostrarFormulario(primaryStage, null, this::guardarNuevoCliente);
    }

    private void mostrarFormularioEditar() {
        Cliente clienteSeleccionado = view.getTablaClientes().getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            VentanasEmergentes.mostrarAlerta("Advertencia", "Selección requerida",
                    "Por favor seleccione un cliente para editar");
            return;
        }

        ClienteDialogController formularioController = new ClienteDialogController();
        formularioController.mostrarFormulario(primaryStage, clienteSeleccionado, clienteEditado -> {
            try {
                if (clienteDAO.actualizarCliente(clienteSeleccionado.getIdFiscal(), clienteEditado)) {
                    cargarDatos();
                    VentanasEmergentes.mostrarAlerta("Éxito", "Actualización exitosa",
                            "El cliente se actualizó correctamente");
                }
            } catch (Exception e) {
                VentanasEmergentes.mostrarAlerta("Error", "Error al actualizar",
                        "No se pudo actualizar el cliente: " + e.getMessage());
            }
        });
    }

    private void guardarNuevoCliente(Cliente nuevoCliente) {
        try {
            if (clienteDAO.crearCliente(nuevoCliente)) {
                cargarDatos();
                VentanasEmergentes.mostrarAlerta("Éxito", "Cliente creado",
                        "Cliente registrado correctamente con ID: " + nuevoCliente.getIdFiscal());
            }
        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al guardar",
                    "No se pudo guardar el nuevo cliente: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        Cliente clienteSeleccionado = view.getTablaClientes().getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            VentanasEmergentes.mostrarAlerta("Advertencia", "Selección requerida",
                    "Por favor seleccione un cliente para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar al cliente " +
                clienteSeleccionado.getNombre() + " " +
                clienteSeleccionado.getApellidos() + "?");
        confirmacion.setContentText("Esta acción desactivará al cliente pero no borrará su historial.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                if (clienteDAO.eliminarCliente(clienteSeleccionado.getIdFiscal())) {
                    cargarDatos();
                    VentanasEmergentes.mostrarAlerta("Éxito", "Cliente eliminado",
                            "El cliente se ha desactivado correctamente");
                }
            } catch (Exception e) {
                VentanasEmergentes.mostrarAlerta("Error", "Error al eliminar",
                        "No se pudo eliminar el cliente: " + e.getMessage());
            }
        }
    }

    private void consultarSaldos() {
        Cliente clienteSeleccionado = view.getTablaClientes().getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            VentanasEmergentes.mostrarAlerta("Advertencia", "Selección requerida",
                    "Por favor seleccione un cliente para consultar sus saldos");
            return;
        }

        try {
            SaldosClienteView saldosView = new SaldosClienteView();
            saldosView.mostrar(primaryStage, clienteSeleccionado, cuentaDAO);
        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al consultar",
                    "No se pudieron cargar los saldos del cliente: " + e.getMessage());
        }
    }
}