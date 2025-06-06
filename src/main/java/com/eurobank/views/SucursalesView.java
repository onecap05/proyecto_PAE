package com.eurobank.views;

import com.eurobank.models.Sucursal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SucursalesView {
    private final TableView<Sucursal> tablaSucursales = new TableView<>();
    private final Button btnAgregar = new Button("Agregar");
    private final Button btnEditar = new Button("Editar");
    private final Button btnEliminar = new Button("Eliminar");
    private final ObservableList<Sucursal> sucursalesData = FXCollections.observableArrayList();

    public SucursalesView() {
        configurarTabla();
    }

    public void mostrar(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Sucursales - EuroBank");

        HBox botonera = new HBox(10, btnAgregar, btnEditar, btnEliminar);
        botonera.setPadding(new Insets(10));

        VBox vbox = new VBox(10, tablaSucursales, botonera);
        vbox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurarTabla() {
        TableColumn<Sucursal, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Sucursal, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Sucursal, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<Sucursal, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Sucursal, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        TableColumn<Sucursal, String> colGerente = new TableColumn<>("ID Gerente");
        colGerente.setCellValueFactory(new PropertyValueFactory<>("idGerente"));

        TableColumn<Sucursal, Boolean> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoActivo"));

        tablaSucursales.getColumns().addAll(colId, colNombre, colDireccion,
                colTelefono, colCorreo, colGerente, colEstado);

        tablaSucursales.setItems(sucursalesData);
    }

    public TableView<Sucursal> getTablaSucursales() {
        return tablaSucursales;
    }

    public Button getBtnAgregar() {
        return btnAgregar;
    }

    public Button getBtnEditar() {
        return btnEditar;
    }

    public Button getBtnEliminar() {
        return btnEliminar;
    }

    public void setSucursalesData(ObservableList<Sucursal> sucursales) {
        sucursalesData.setAll(sucursales);
    }
}