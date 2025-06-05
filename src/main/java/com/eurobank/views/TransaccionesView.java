package com.eurobank.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.eurobank.models.Transaccion;
import javafx.collections.FXCollections;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransaccionesView {
    public static void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Transacciones");

        // Crear tabla
        TableView<Transaccion> tabla = new TableView<>();

        // Columnas
        TableColumn<Transaccion, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Transaccion, Double> colMonto = new TableColumn<>("Monto");
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

        TableColumn<Transaccion, String> colFecha = new TableColumn<>("Fecha/Hora");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaFormateada"));

        TableColumn<Transaccion, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Transaccion, String> colCuentaOrigen = new TableColumn<>("Cuenta Origen");
        colCuentaOrigen.setCellValueFactory(new PropertyValueFactory<>("cuentaOrigen"));

        TableColumn<Transaccion, String> colCuentaDestino = new TableColumn<>("Cuenta Destino");
        colCuentaDestino.setCellValueFactory(new PropertyValueFactory<>("cuentaDestino"));

        TableColumn<Transaccion, String> colSucursal = new TableColumn<>("Sucursal");
        colSucursal.setCellValueFactory(new PropertyValueFactory<>("sucursal"));

        tabla.getColumns().addAll(colId, colMonto, colFecha, colTipo, colCuentaOrigen, colCuentaDestino, colSucursal);

        // Datos de ejemplo
        tabla.setItems(FXCollections.observableArrayList(
                new Transaccion("T001", 1500.0, LocalDateTime.now(), "depósito", "123456", null, "Sucursal Centro"),
                new Transaccion("T002", 2500.0, LocalDateTime.now(), "transferencia", "123456", "789012", "Sucursal Norte")
        ));

        // Botones
        Button btnNueva = new Button("Nueva Transacción");
        Button btnDetalle = new Button("Ver Detalle");
        Button btnExportar = new Button("Exportar");

        HBox botones = new HBox(10, btnNueva, btnDetalle, btnExportar);

        // Eventos
        btnNueva.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                NuevaTransaccionDialog.mostrar();
            }
        });

        BorderPane root = new BorderPane();
        root.setCenter(tabla);
        root.setBottom(botones);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    // Diálogo para nueva transacción
    public static class NuevaTransaccionDialog {
        public static void mostrar() {
            Stage stage = new Stage();
            stage.setTitle("Nueva Transacción");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20));

            // Controles del formulario
            ComboBox<String> cbTipo = new ComboBox<>();
            cbTipo.getItems().addAll("depósito", "retiro", "transferencia");
            cbTipo.setValue("depósito");

            TextField tfMonto = new TextField();
            TextField tfCuentaOrigen = new TextField();
            TextField tfCuentaDestino = new TextField();
            ComboBox<String> cbSucursal = new ComboBox<>();
            cbSucursal.getItems().addAll("Sucursal Centro", "Sucursal Norte", "Sucursal Sur");

            Button btnGuardar = new Button("Guardar");
            Button btnCancelar = new Button("Cancelar");

            // Organización en grid
            grid.add(new Label("Tipo:"), 0, 0);
            grid.add(cbTipo, 1, 0);
            grid.add(new Label("Monto:"), 0, 1);
            grid.add(tfMonto, 1, 1);
            grid.add(new Label("Cuenta Origen:"), 0, 2);
            grid.add(tfCuentaOrigen, 1, 2);
            grid.add(new Label("Cuenta Destino:"), 0, 3);
            grid.add(tfCuentaDestino, 1, 3);
            grid.add(new Label("Sucursal:"), 0, 4);
            grid.add(cbSucursal, 1, 4);
            grid.add(btnGuardar, 0, 5);
            grid.add(btnCancelar, 1, 5);

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