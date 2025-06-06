package com.eurobank.controllers;

import com.eurobank.models.DAO.SucursalDAO;
import com.eurobank.models.Sucursal;
import com.eurobank.views.SucursalesView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SucursalesController {
    private SucursalesView view;
    private SucursalDAO sucursalDAO;
    private Stage primaryStage;

    public SucursalesController(SucursalesView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.sucursalDAO = new SucursalDAO();
        configurarEventos();
        cargarDatos();
    }

    private void configurarEventos() {
        view.getBtnAgregar().setOnAction(e -> mostrarFormularioAgregar());
        view.getBtnEditar().setOnAction(e -> mostrarFormularioEditar());
        view.getBtnEliminar().setOnAction(e -> eliminarSucursal());
    }

    private void cargarDatos() {
        try {
            ObservableList<Sucursal> sucursales = FXCollections.observableArrayList(sucursalDAO.listarSucursalesActivas());
            view.setSucursalesData(sucursales);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudieron cargar las sucursales", Alert.AlertType.ERROR);
            view.setSucursalesData(FXCollections.observableArrayList());
        }
    }

    private void mostrarFormularioAgregar() {
        SucursalDialogController formularioController = new SucursalDialogController();
        formularioController.mostrarFormulario(primaryStage, null, this::guardarNuevaSucursal);
    }

    private void mostrarFormularioEditar() {
        Sucursal sucursalSeleccionada = view.getTablaSucursales().getSelectionModel().getSelectedItem();

        if (sucursalSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor seleccione una sucursal para editar", Alert.AlertType.WARNING);
            return;
        }

        SucursalDialogController formularioController = new SucursalDialogController();
        formularioController.mostrarFormulario(primaryStage, sucursalSeleccionada, sucursalEditada -> {
            try {
                if (sucursalDAO.actualizarSucursal(sucursalSeleccionada.getId(), sucursalEditada)) {
                    cargarDatos();
                    mostrarAlerta("Éxito", "Sucursal actualizada correctamente", Alert.AlertType.INFORMATION);
                }
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo actualizar la sucursal", Alert.AlertType.ERROR);
            }
        });
    }

    private void guardarNuevaSucursal(Sucursal nuevaSucursal) {
        try {
            Sucursal sucursalGuardada = sucursalDAO.crearNuevaSucursal(nuevaSucursal);
            cargarDatos();
            mostrarAlerta("Éxito", "Sucursal agregada correctamente con ID: " + sucursalGuardada.getId(), Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo guardar la sucursal", Alert.AlertType.ERROR);
        }
    }

    private void eliminarSucursal() {
        Sucursal sucursalSeleccionada = view.getTablaSucursales().getSelectionModel().getSelectedItem();

        if (sucursalSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor seleccione una sucursal para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar la sucursal " + sucursalSeleccionada.getNombre() + "?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                if (sucursalDAO.eliminarSucursal(sucursalSeleccionada.getId())) {
                    cargarDatos();
                    mostrarAlerta("Éxito", "Sucursal eliminada correctamente", Alert.AlertType.INFORMATION);
                }
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo eliminar la sucursal", Alert.AlertType.ERROR);
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}