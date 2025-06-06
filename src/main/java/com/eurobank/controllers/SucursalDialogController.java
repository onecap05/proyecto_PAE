package com.eurobank.controllers;

import com.eurobank.models.DAO.SucursalDAO;
import com.eurobank.models.Sucursal;
import com.eurobank.views.SucursalDialog;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class SucursalDialogController {
    private SucursalDialog view;

    public void mostrarFormulario(Stage owner, Sucursal sucursal, Consumer<Sucursal> onGuardar) {
        view = new SucursalDialog();
        String titulo = sucursal == null ? "Agregar Nueva Sucursal" : "Editar Sucursal";
        view.mostrarFormulario(owner, sucursal, titulo);
        SucursalDAO sucursalDAO = new SucursalDAO();

        view.getBtnGuardar().setOnAction(e -> {
            if (validarCampos()) {
                Sucursal nuevaSucursal = new Sucursal(
                        "TEMP",
                        view.getNombre(),
                        view.getDireccion(),
                        view.getTelefono(),
                        view.getCorreo(),
                        view.getIdGerente()
                );

                onGuardar.accept(nuevaSucursal);
                view.cerrar();
            }
        });

        view.getBtnCancelar().setOnAction(e -> view.cerrar());
    }

    private boolean validarCampos() {
        if (view.getNombre().isEmpty() ||
                view.getDireccion().isEmpty() ||
                view.getTelefono().isEmpty() ||
                view.getCorreo().isEmpty() ||
                view.getIdGerente().isEmpty()) {

            mostrarAlerta("Error de validación", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        if (!view.getCorreo().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            mostrarAlerta("Error de validación", "El correo electrónico no es válido", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}