package com.eurobank.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.eurobank.models.Cliente;
import javafx.collections.FXCollections;

public class ClientesView {
    public static void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Clientes");

        TableView<Cliente> tabla = new TableView<>();

        TableColumn<Cliente, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Cliente, String> colRfc = new TableColumn<>("RFC");
        colRfc.setCellValueFactory(new PropertyValueFactory<>("rfc"));

        tabla.getColumns().addAll(colId, colNombre, colRfc);

        // Datos de ejemplo
        tabla.setItems(FXCollections.observableArrayList(
                new Cliente("C001", "Juan Pérez", "PERJ920101"),
                new Cliente("C002", "María García", "GARM850202")
        ));

        // Botones CRUD
        Button btnAgregar = new Button("Agregar");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");

        HBox botones = new HBox(10, btnAgregar, btnEditar, btnEliminar);

        BorderPane root = new BorderPane();
        root.setCenter(tabla);
        root.setBottom(botones);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}