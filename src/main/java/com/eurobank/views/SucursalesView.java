package com.eurobank.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.eurobank.models.Sucursal;
import javafx.collections.FXCollections;

public class SucursalesView {
    public static void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Sucursales");

        // Crear tabla
        TableView<Sucursal> tabla = new TableView<>();

        // Columnas
        TableColumn<Sucursal, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Sucursal, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Sucursal, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<Sucursal, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Sucursal, String> colGerente = new TableColumn<>("Gerente");
        colGerente.setCellValueFactory(new PropertyValueFactory<>("gerente"));

        tabla.getColumns().addAll(colId, colNombre, colDireccion, colTelefono, colGerente);

        // Datos de ejemplo
        tabla.setItems(FXCollections.observableArrayList(
                new Sucursal("S001", "Sucursal Centro", "Av. Principal 123", "555-100-2000", "Juan Pérez"),
                new Sucursal("S002", "Sucursal Norte", "Calle Norte 456", "555-300-4000", "María García")
        ));

        // Botones
        Button btnAgregar = new Button("Agregar Sucursal");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");
        Button btnDetalle = new Button("Ver Detalle");

        HBox botones = new HBox(10, btnAgregar, btnEditar, btnEliminar, btnDetalle);

        // Eventos
        btnAgregar.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                NuevaSucursalDialog.mostrar();
            }
        });

        BorderPane root = new BorderPane();
        root.setCenter(tabla);
        root.setBottom(botones);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    // Diálogo para nueva sucursal
    public static class NuevaSucursalDialog {
        public static void mostrar() {
            Stage stage = new Stage();
            stage.setTitle("Nueva Sucursal");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20));

            // Controles del formulario
            TextField tfId = new TextField();
            TextField tfNombre = new TextField();
            TextField tfDireccion = new TextField();
            TextField tfTelefono = new TextField();
            TextField tfEmail = new TextField();
            TextField tfGerente = new TextField();
            TextField tfContacto = new TextField();

            Button btnGuardar = new Button("Guardar");
            Button btnCancelar = new Button("Cancelar");

            // Organización en grid
            grid.add(new Label("ID:"), 0, 0);
            grid.add(tfId, 1, 0);
            grid.add(new Label("Nombre:"), 0, 1);
            grid.add(tfNombre, 1, 1);
            grid.add(new Label("Dirección:"), 0, 2);
            grid.add(tfDireccion, 1, 2);
            grid.add(new Label("Teléfono:"), 0, 3);
            grid.add(tfTelefono, 1, 3);
            grid.add(new Label("Email:"), 0, 4);
            grid.add(tfEmail, 1, 4);
            grid.add(new Label("Gerente:"), 0, 5);
            grid.add(tfGerente, 1, 5);
            grid.add(new Label("Contacto:"), 0, 6);
            grid.add(tfContacto, 1, 6);
            grid.add(btnGuardar, 0, 7);
            grid.add(btnCancelar, 1, 7);

            // Eventos
            btnCancelar.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    stage.close();
                }
            });

            Scene scene = new Scene(grid);
            stage.setScene(scene);
            stage.show();
        }
    }
}