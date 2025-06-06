package com.eurobank.views;

import com.eurobank.models.Sucursal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SucursalDialog {
    private TextField txtNombre, txtDireccion, txtTelefono, txtCorreo, txtIdGerente;
    private Button btnGuardar, btnCancelar;
    private Stage stage;

    public void mostrarFormulario(Stage owner, Sucursal sucursal, String titulo) {
        stage = new Stage();
        stage.setTitle(titulo);
        stage.initOwner(owner);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        txtNombre = new TextField();
        txtDireccion = new TextField();
        txtTelefono = new TextField();
        txtCorreo = new TextField();
        txtIdGerente = new TextField();

        if (sucursal != null) {
            txtNombre.setText(sucursal.getNombre());
            txtDireccion.setText(sucursal.getDireccion());
            txtTelefono.setText(sucursal.getTelefono());
            txtCorreo.setText(sucursal.getCorreo());
            txtIdGerente.setText(sucursal.getIdGerente());
        }

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Dirección:"), 0, 1);
        grid.add(txtDireccion, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(txtTelefono, 1, 2);
        grid.add(new Label("Correo:"), 0, 3);
        grid.add(txtCorreo, 1, 3);
        grid.add(new Label("ID Gerente:"), 0, 4);
        grid.add(txtIdGerente, 1, 4);

        btnGuardar = new Button("Guardar");
        btnCancelar = new Button("Cancelar");

        GridPane botones = new GridPane();
        botones.setHgap(10);
        botones.add(btnCancelar, 0, 0);
        botones.add(btnGuardar, 1, 0);

        grid.add(botones, 1, 5);

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    public String getNombre() {
        return txtNombre.getText();
    }

    public String getDireccion() {
        return txtDireccion.getText();
    }

    public String getTelefono() {
        return txtTelefono.getText();
    }

    public String getCorreo() {
        return txtCorreo.getText();
    }

    public String getIdGerente() {
        return txtIdGerente.getText();
    }

    public Button getBtnGuardar() {
        return btnGuardar;
    }

    public Button getBtnCancelar() {
        return btnCancelar;
    }

    public void cerrar() {
        stage.close();
    }
}