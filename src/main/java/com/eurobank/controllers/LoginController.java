package com.eurobank.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.eurobank.views.MainView;

public class LoginController implements EventHandler<ActionEvent> {
    private Stage stage;
    private TextField txtUsuario;
    private PasswordField txtPassword;

    public LoginController(Stage stage, TextField txtUsuario, PasswordField txtPassword) {
        this.stage = stage;
        this.txtUsuario = txtUsuario;
        this.txtPassword = txtPassword;
    }

    @Override
    public void handle(ActionEvent event) {
        if(validarCredenciales()) {
            MainView.mostrar(stage);
        } else {
            mostrarError("Credenciales incorrectas");
        }
    }

    private boolean validarCredenciales() {
        return !txtUsuario.getText().isEmpty() && !txtPassword.getText().isEmpty();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}