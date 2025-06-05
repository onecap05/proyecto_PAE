package com.eurobank.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.eurobank.models.Cuenta;
import javafx.collections.FXCollections;

public class CuentasView {
    public static void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Cuentas Bancarias");

        TableView<Cuenta> tabla = new TableView<>();

        TableColumn<Cuenta, String> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<Cuenta, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Cuenta, Double> colSaldo = new TableColumn<>("Saldo");
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));

        tabla.getColumns().addAll(colNumero, colTipo, colSaldo);

        // Datos de ejemplo
        tabla.setItems(FXCollections.observableArrayList(
                new Cuenta("123456", "ahorros", 15000.0),
                new Cuenta("789012", "corriente", 25000.0)
        ));

        BorderPane root = new BorderPane(tabla);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}