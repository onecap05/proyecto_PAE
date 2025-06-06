package com.eurobank.controllers;

import com.eurobank.models.Cuenta;
import com.eurobank.models.TipoCuenta;
import com.eurobank.models.DAO.CuentaDAO;
import com.eurobank.views.CuentasView;
import com.eurobank.utils.VentanasEmergentes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CuentasController {
    private CuentasView view;
    private CuentaDAO cuentaDAO;
    private Stage primaryStage;
    public static String idCuentaSeleccionada = null;

    public CuentasController(CuentasView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
        try {
            this.cuentaDAO = new CuentaDAO();
            configurarEventos();
            cargarDatos();
        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al inicializar",
                    "No se pudo inicializar el controlador de cuentas: " + e.getMessage());
        }
    }

    private void configurarEventos() {
        view.getBtnAgregar().setOnAction(e -> mostrarFormularioAgregar());
        view.getBtnEditar().setOnAction(e -> mostrarFormularioEditar());
        view.getBtnEliminar().setOnAction(e -> eliminarCuenta());
        view.getBtnBuscar().setOnAction(e -> buscarCuentas());
        view.getBtnTransferir().setOnAction(e -> abrirVentanaTransferir());
    }

    private void cargarDatos() {
        try {
            ObservableList<Cuenta> cuentas = FXCollections.observableArrayList(cuentaDAO.listarCuentasActivas());
            view.getCuentasData().setAll(cuentas);
        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al cargar datos",
                    "No se pudieron cargar las cuentas: " + e.getMessage());
        }
    }

    private void buscarCuentas() {
        try {
            String textoBusqueda = view.getTxtBusqueda().getText().toLowerCase();
            TipoCuenta tipoSeleccionado = view.getCbTipoCuenta().getValue();

            ObservableList<Cuenta> cuentasFiltradas = FXCollections.observableArrayList(
                    cuentaDAO.listarCuentasActivas().stream()
                            .filter(c ->
                                    (textoBusqueda.isEmpty() ||
                                            c.getNumeroCuenta().toLowerCase().contains(textoBusqueda) ||
                                            c.getIdCliente().toLowerCase().contains(textoBusqueda))
                                            && (tipoSeleccionado == null || c.getTipo() == tipoSeleccionado)
                            )
                            .toList()
            );

            view.getCuentasData().setAll(cuentasFiltradas);
        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al buscar",
                    "No se pudo realizar la búsqueda: " + e.getMessage());
        }
    }

    private void mostrarFormularioAgregar() {
        CuentaDialogController formularioController = new CuentaDialogController();
        formularioController.mostrarFormulario(primaryStage, null, this::guardarNuevaCuenta);
    }

    private void mostrarFormularioEditar() {
        Cuenta cuentaSeleccionada = view.getTablaCuentas().getSelectionModel().getSelectedItem();

        if (cuentaSeleccionada == null) {
            VentanasEmergentes.mostrarAlerta("Advertencia", "Selección requerida",
                    "Por favor seleccione una cuenta para editar");
            return;
        }

        CuentaDialogController formularioController = new CuentaDialogController();
        formularioController.mostrarFormulario(primaryStage, cuentaSeleccionada, cuentaEditada -> {
            try {
                if (cuentaDAO.actualizarCuenta(cuentaSeleccionada.getNumeroCuenta(), cuentaEditada)) {
                    cargarDatos();
                    VentanasEmergentes.mostrarAlerta("Éxito", "Actualización exitosa",
                            "La cuenta se actualizó correctamente");
                }
            } catch (IOException e) {
                VentanasEmergentes.mostrarAlerta("Error", "Error al actualizar",
                        "No se pudo actualizar la cuenta: " + e.getMessage());
            }
        });
    }

    private void guardarNuevaCuenta(Cuenta nuevaCuenta) {
        try {
            Cuenta cuentaGuardada = cuentaDAO.crearCuenta(
                    nuevaCuenta.getTipo(),
                    nuevaCuenta.getSaldo(),
                    nuevaCuenta.getLimiteCredito(),
                    nuevaCuenta.getIdCliente()
            );
            cargarDatos();
            VentanasEmergentes.mostrarAlerta("Éxito", "Cuenta creada",
                    "Cuenta creada correctamente con número: " + cuentaGuardada.getNumeroCuenta());
        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al guardar",
                    "No se pudo guardar la nueva cuenta: " + e.getMessage());
        }
    }

    private void eliminarCuenta() {
        Cuenta cuentaSeleccionada = view.getTablaCuentas().getSelectionModel().getSelectedItem();

        if (cuentaSeleccionada == null) {
            VentanasEmergentes.mostrarAlerta("Advertencia", "Selección requerida",
                    "Por favor seleccione una cuenta para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar la cuenta " + cuentaSeleccionada.getNumeroCuenta() + "?");
        confirmacion.setContentText("Esta acción desactivará la cuenta pero no borrará su historial.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                if (cuentaDAO.eliminarCuenta(cuentaSeleccionada.getNumeroCuenta())) {
                    cargarDatos();
                    VentanasEmergentes.mostrarAlerta("Éxito", "Cuenta eliminada",
                            "La cuenta se ha desactivado correctamente");
                }
            } catch (IOException e) {
                VentanasEmergentes.mostrarAlerta("Error", "Error al eliminar",
                        "No se pudo eliminar la cuenta: " + e.getMessage());
            }
        }
    }

    public void abrirVentanaTransferir () {

        Cuenta cuentaSeleccionada = view.getTablaCuentas().getSelectionModel().getSelectedItem();

        if (cuentaSeleccionada == null) {
            VentanasEmergentes.mostrarAlerta("Advertencia", "Selección requerida",
                    "Por favor seleccione una cuenta para transferir");
            return;
        }
        idCuentaSeleccionada = cuentaSeleccionada.getNumeroCuenta();

        System.out.println("ID de cuenta seleccionada: " + idCuentaSeleccionada);

        TransaccionesViewController ventanaTransacciones = new TransaccionesViewController(primaryStage);
        ventanaTransacciones.mostrarTransaccionesView();

    }
}