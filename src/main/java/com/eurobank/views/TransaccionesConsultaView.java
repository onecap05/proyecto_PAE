package com.eurobank.views;

import com.eurobank.controllers.TransaccionesConsultaController;
import com.eurobank.models.Transaccion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class TransaccionesConsultaView {

    private TableView<Transaccion> tabla;
    private TextField idField;
    private ComboBox<String> filtroFechaCombo;
    private Button btnFiltrar;
    private Button btnGenerarDocumento;

    private TransaccionesConsultaController controller;

    public void mostrarVentana() {
        Stage stage = new Stage();
        stage.setTitle("Consulta de Transacciones");

        idField = new TextField();
        idField.setPromptText("Buscar por ID");

        filtroFechaCombo = new ComboBox<>();
        filtroFechaCombo.getItems().addAll("Hoy", "Esta semana", "Este mes", "Este año", "Todos");
        filtroFechaCombo.setValue("Todos");

        btnFiltrar = new Button("Aplicar filtros");
        btnGenerarDocumento = new Button("Generar Documento");


        HBox filtrosBox = new HBox(10, idField, filtroFechaCombo, btnFiltrar, btnGenerarDocumento);
        filtrosBox.setPadding(new Insets(10));

        tabla = crearTabla();

        VBox root = new VBox(10, filtrosBox, tabla);
        root.setPadding(new Insets(10));

        controller = new TransaccionesConsultaController(this);
        controller.inicializar();

        btnFiltrar.setOnAction(e -> controller.aplicarFiltros());

        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    private TableView<Transaccion> crearTabla() {
        TableView<Transaccion> table = new TableView<>();

        TableColumn<Transaccion, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));

        TableColumn<Transaccion, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo()));

        TableColumn<Transaccion, String> colMonto = new TableColumn<>("Monto");
        colMonto.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getMonto())));

        TableColumn<Transaccion, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(data -> {
            LocalDateTime fecha = data.getValue().getFecha();
            return new SimpleStringProperty(fecha != null ? fecha.toString() : "N/A");
        });

        TableColumn<Transaccion, String> colSucursal = new TableColumn<>("Sucursal");
        colSucursal.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdSucursal()));

        table.getColumns().addAll(colId, colTipo, colMonto, colFecha, colSucursal);
        return table;
    }


    public TableView<Transaccion> getTabla() {
        return tabla;
    }

    public TextField getIdField() {
        return idField;
    }

    public ComboBox<String> getFiltroFechaCombo() {
        return filtroFechaCombo;
    }

    public Button getBtnGenerarDocumento() {
        return btnGenerarDocumento;
    }

}

