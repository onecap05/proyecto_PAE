package com.eurobank.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.eurobank.models.Cliente;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import java.time.LocalDate;

public class ClienteDialog {
    public static void mostrarDialogoAgregar(ObservableList<Cliente> listaClientes) {
        Stage stage = new Stage();
        stage.setTitle("Registrar Nuevo Cliente");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Campos del formulario según documento
        TextField tfRfcCurp = new TextField();
        TextField tfNombre = new TextField();
        TextField tfApellidos = new TextField();
        TextField tfNacionalidad = new TextField();
        DatePicker dpFechaNac = new DatePicker();
        TextField tfDireccion = new TextField();
        TextField tfTelefono = new TextField();
        TextField tfEmail = new TextField();

        // Organización en grid
        grid.add(new Label("RFC/CURP:"), 0, 0);
        grid.add(tfRfcCurp, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(tfNombre, 1, 1);
        grid.add(new Label("Apellidos:"), 0, 2);
        grid.add(tfApellidos, 1, 2);
        grid.add(new Label("Nacionalidad:"), 0, 3);
        grid.add(tfNacionalidad, 1, 3);
        grid.add(new Label("Fecha Nacimiento:"), 0, 4);
        grid.add(dpFechaNac, 1, 4);
        grid.add(new Label("Dirección:"), 0, 5);
        grid.add(tfDireccion, 1, 5);
        grid.add(new Label("Teléfono:"), 0, 6);
        grid.add(tfTelefono, 1, 6);
        grid.add(new Label("Email:"), 0, 7);
        grid.add(tfEmail, 1, 7);

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");

        grid.add(btnGuardar, 0, 8);
        grid.add(btnCancelar, 1, 8);

        btnCancelar.setOnAction(e -> stage.close());

        btnGuardar.setOnAction(e -> {
            try {
                Cliente nuevo = new Cliente(
                        tfRfcCurp.getText(),
                        tfNombre.getText(),
                        tfApellidos.getText(),
                        tfNacionalidad.getText(),
                        dpFechaNac.getValue(),
                        tfDireccion.getText(),
                        tfTelefono.getText(),
                        tfEmail.getText()
                );

                listaClientes.add(nuevo);
                stage.close();
            } catch (Exception ex) {
                mostrarError("Error al guardar: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    public static void mostrarDialogoEditar(Cliente cliente) {
        Stage stage = new Stage();
        stage.setTitle("Editar Cliente");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Campos precargados
        TextField tfRfcCurp = new TextField(cliente.getIdFiscal());
        tfRfcCurp.setDisable(true);
        TextField tfNombre = new TextField(cliente.getNombre());
        TextField tfApellidos = new TextField(cliente.getApellidos());
        TextField tfNacionalidad = new TextField(cliente.getNacionalidad());
        DatePicker dpFechaNac = new DatePicker(cliente.getFechaNacimiento());
        TextField tfDireccion = new TextField(cliente.getDireccion());
        TextField tfTelefono = new TextField(cliente.getTelefono());
        TextField tfEmail = new TextField(cliente.getEmail());

        // CheckBox para estado activo
        CheckBox cbActivo = new CheckBox("Cliente activo");
        cbActivo.setSelected(cliente.isEstadoActivo());

        // Organización en grid
        grid.add(new Label("RFC/CURP:"), 0, 0);
        grid.add(tfRfcCurp, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(tfNombre, 1, 1);
        grid.add(new Label("Apellidos:"), 0, 2);
        grid.add(tfApellidos, 1, 2);
        grid.add(new Label("Nacionalidad:"), 0, 3);
        grid.add(tfNacionalidad, 1, 3);
        grid.add(new Label("Fecha Nacimiento:"), 0, 4);
        grid.add(dpFechaNac, 1, 4);
        grid.add(new Label("Dirección:"), 0, 5);
        grid.add(tfDireccion, 1, 5);
        grid.add(new Label("Teléfono:"), 0, 6);
        grid.add(tfTelefono, 1, 6);
        grid.add(new Label("Email:"), 0, 7);
        grid.add(tfEmail, 1, 7);
        grid.add(cbActivo, 0, 9); // Agrega el CheckBox

        Button btnGuardar = new Button("Guardar Cambios");
        Button btnCancelar = new Button("Cancelar");

        grid.add(btnGuardar, 0, 10);
        grid.add(btnCancelar, 1, 10);

        btnCancelar.setOnAction(e -> stage.close());

        btnGuardar.setOnAction(e -> {
            try {
                cliente.setNombre(tfNombre.getText());
                cliente.setApellidos(tfApellidos.getText());
                cliente.setNacionalidad(tfNacionalidad.getText());
                cliente.setFechaNacimiento(dpFechaNac.getValue());
                cliente.setDireccion(tfDireccion.getText());
                cliente.setTelefono(tfTelefono.getText());
                cliente.setEmail(tfEmail.getText());
                cliente.setEstadoActivo(cbActivo.isSelected()); // Actualiza el estado activo

                stage.close();
            } catch (Exception ex) {
                mostrarError("Error al guardar: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    private static void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}