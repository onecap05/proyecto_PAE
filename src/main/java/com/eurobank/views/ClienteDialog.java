package com.eurobank.views;

import com.eurobank.models.Cliente;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ClienteDialog {
    private TextField txtIdFiscal;
    private TextField txtNombre;
    private TextField txtApellidos;
    private TextField txtNacionalidad;
    private DatePicker dpFechaNacimiento;
    private TextField txtDireccion;
    private TextField txtTelefono;
    private TextField txtEmail;
    private Button btnGuardar;
    private Button btnCancelar;
    private Stage stage;

    public void mostrarFormulario(Stage owner, Cliente cliente, String titulo) {
        stage = new Stage();
        stage.setTitle(titulo);
        stage.initOwner(owner);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        txtIdFiscal = new TextField();
        txtNombre = new TextField();
        txtApellidos = new TextField();
        txtNacionalidad = new TextField();
        dpFechaNacimiento = new DatePicker();
        txtDireccion = new TextField();
        txtTelefono = new TextField();
        txtEmail = new TextField();

        if (cliente != null) {
            txtIdFiscal.setText(cliente.getIdFiscal());
            txtNombre.setText(cliente.getNombre());
            txtApellidos.setText(cliente.getApellidos());
            txtNacionalidad.setText(cliente.getNacionalidad());
            dpFechaNacimiento.setValue(cliente.getFechaNacimiento());
            txtDireccion.setText(cliente.getDireccion());
            txtTelefono.setText(cliente.getTelefono());
            txtEmail.setText(cliente.getEmail());
        } else {
            dpFechaNacimiento.setValue(LocalDate.now().minusYears(18));
        }

        grid.add(new Label("ID Fiscal:"), 0, 0);
        grid.add(txtIdFiscal, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Apellidos:"), 0, 2);
        grid.add(txtApellidos, 1, 2);
        grid.add(new Label("Nacionalidad:"), 0, 3);
        grid.add(txtNacionalidad, 1, 3);
        grid.add(new Label("Fecha Nacimiento:"), 0, 4);
        grid.add(dpFechaNacimiento, 1, 4);
        grid.add(new Label("Dirección:"), 0, 5);
        grid.add(txtDireccion, 1, 5);
        grid.add(new Label("Teléfono:"), 0, 6);
        grid.add(txtTelefono, 1, 6);
        grid.add(new Label("Email:"), 0, 7);
        grid.add(txtEmail, 1, 7);

        btnGuardar = new Button("Guardar");
        btnCancelar = new Button("Cancelar");

        GridPane botones = new GridPane();
        botones.setHgap(10);
        botones.add(btnCancelar, 0, 0);
        botones.add(btnGuardar, 1, 0);

        grid.add(botones, 1, 8);

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    public String getIdFiscal() { return txtIdFiscal.getText(); }
    public String getNombre() { return txtNombre.getText(); }
    public String getApellidos() { return txtApellidos.getText(); }
    public String getNacionalidad() { return txtNacionalidad.getText(); }
    public LocalDate getFechaNacimiento() { return dpFechaNacimiento.getValue(); }
    public String getDireccion() { return txtDireccion.getText(); }
    public String getTelefono() { return txtTelefono.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public Button getBtnGuardar() { return btnGuardar; }
    public Button getBtnCancelar() { return btnCancelar; }
    public void cerrar() { stage.close(); }
}