package com.eurobank.controllers;

import com.eurobank.models.DAO.EmpleadoDAO;
import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import com.eurobank.views.EmpleadosView;
import com.eurobank.views.EmpleadoDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class EmpleadosController {

    private EmpleadosView view;
    private EmpleadoDAO empleadoDAO;
    private ObservableList<Empleado> empleados;
    private FilteredList<Empleado> empleadosFiltrados;

    public EmpleadosController() {

        try {

            this.empleadoDAO = new EmpleadoDAO();
            this.empleados = FXCollections.observableArrayList(empleadoDAO.listarEmpleadosActivos());
            this.empleadosFiltrados = new FilteredList<>(empleados, Empleado::isEstadoActivo);

            this.view = new EmpleadosView(this);
            configurarEventos();
            view.setEmpleadosEnTabla(empleadosFiltrados);

        } catch (IOException e) {

            mostrarError("Error al cargar empleados: " + e.getMessage());
        }
    }

    private void configurarEventos() {

        view.getCbFiltroRol().setOnAction(this::filtrarPorRol);
        view.getBtnAgregar().setOnAction(this::crearNuevoEmpleado);
        view.getBtnEditar().setOnAction(this::actualizarEmpleado);
        view.getBtnEliminar().setOnAction(this::eliminarEmpleado);
    }

    private void filtrarPorRol(ActionEvent event) {


        RolEmpleado rolSeleccionado = view.getCbFiltroRol().getValue();
        empleadosFiltrados.setPredicate(empleado -> rolSeleccionado == null || empleado.getRol() == rolSeleccionado);
    }

    private void crearNuevoEmpleado(ActionEvent event) {

        try {

            Empleado nuevoEmpleado = EmpleadoDialog.mostrarDialogoAgregar();

            if (nuevoEmpleado != null) {

                Empleado empleadoCreado = empleadoDAO.crearNuevoEmpleado(nuevoEmpleado);
                empleados.add(empleadoCreado);
                view.getTableView().refresh();
            }

        } catch (IllegalArgumentException e) {
            mostrarAlerta("Datos inválidos", e.getMessage());

        } catch (IOException e) {
            mostrarError("Error al guardar empleado: " + e.getMessage());
        }
    }


    private void actualizarEmpleado(ActionEvent event) {

        Empleado seleccionado = view.getTableView().getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            Empleado empleadoEditado = EmpleadoDialog.mostrarDialogoEditar(seleccionado);

            if (empleadoEditado != null) {

                var errores = empleadoEditado.validarCamposBasicos();

                if (!errores.isEmpty()) {
                    mostrarAlerta("Datos inválidos", String.join("\n", errores));
                    return;
                }

                try {

                    if (empleadoDAO.actualizarEmpleado(seleccionado.getId(), empleadoEditado)) {

                        int index = empleados.indexOf(seleccionado);
                        empleados.set(index, empleadoEditado);
                        view.getTableView().refresh();
                    }

                } catch (IOException e) {

                    mostrarError("Error al actualizar empleado: " + e.getMessage());
                }
            }

        } else {

            mostrarAlerta("Selección requerida", "Por favor seleccione un empleado para editar");
        }
    }

    private void eliminarEmpleado(ActionEvent event) {

        Empleado seleccionado = view.getTableView().getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            if (confirmarEliminacion()) {

                try {

                    if (empleadoDAO.eliminarEmpleado(seleccionado.getId())) {
                        empleados.remove(seleccionado);
                    }

                } catch (IOException e) {
                    mostrarError("Error al eliminar empleado: " + e.getMessage());
                }
            }

        } else {
            mostrarAlerta("Selección requerida", "Por favor seleccione un empleado para eliminar");
        }
    }

    private boolean confirmarEliminacion() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de desactivar este empleado?");
        alert.setContentText("Esta acción marcará al empleado como inactivo.");
        return alert.showAndWait().get() == ButtonType.OK;
    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void mostrarVentana() {
        view.mostrar();
    }
}
