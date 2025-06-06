package com.eurobank.views;

import com.eurobank.models.Cuenta;
import com.eurobank.models.TipoCuenta;
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

public class CuentasView {
    private final TableView<Cuenta> tablaCuentas = new TableView<>();
    private final TextField txtBusqueda = new TextField();
    private final ComboBox<TipoCuenta> cbTipoCuenta = new ComboBox<>();
    private final Button btnBuscar = new Button("Buscar");
    private final Button btnAgregar = new Button("Agregar");
    private final Button btnEditar = new Button("Editar");
    private final Button btnEliminar = new Button("Eliminar");
    private final Button btnTransferir = new Button("Transferir");
    private final ObservableList<Cuenta> cuentasData = FXCollections.observableArrayList();

    public CuentasView() {
        configurarTabla();
        configurarBusqueda();
    }

    public void mostrar(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Cuentas Bancarias - EuroBank");

        HBox barraBusqueda = new HBox(10,
                new Label("Buscar:"),
                txtBusqueda,
                new Label("Tipo:"),
                cbTipoCuenta,
                btnBuscar
        );
        barraBusqueda.setPadding(new Insets(10));

        HBox botonera = new HBox(10, btnAgregar, btnEditar, btnEliminar, btnTransferir);
        botonera.setPadding(new Insets(10));

        VBox vbox = new VBox(10, barraBusqueda, tablaCuentas, botonera);
        vbox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurarTabla() {
        TableColumn<Cuenta, String> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        colNumero.setMinWidth(150);

        TableColumn<Cuenta, TipoCuenta> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTipo.setMinWidth(150);

        TableColumn<Cuenta, Double> colSaldo = new TableColumn<>("Saldo");
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        colSaldo.setMinWidth(150);

        TableColumn<Cuenta, Double> colLimite = new TableColumn<>("Límite Crédito");
        colLimite.setCellValueFactory(new PropertyValueFactory<>("limiteCredito"));
        colLimite.setMinWidth(150);

        TableColumn<Cuenta, String> colCliente = new TableColumn<>("ID Cliente");
        colCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colCliente.setMinWidth(150);

        tablaCuentas.getColumns().addAll(colNumero, colTipo, colSaldo, colLimite, colCliente);
        tablaCuentas.setItems(cuentasData);
    }

    private void configurarBusqueda() {
        cbTipoCuenta.setItems(FXCollections.observableArrayList(TipoCuenta.values()));
        cbTipoCuenta.getItems().add(0, null); // Opción para mostrar todos los tipos
        cbTipoCuenta.getSelectionModel().selectFirst();
    }

    public TableView<Cuenta> getTablaCuentas() { return tablaCuentas; }
    public TextField getTxtBusqueda() { return txtBusqueda; }
    public ComboBox<TipoCuenta> getCbTipoCuenta() { return cbTipoCuenta; }
    public Button getBtnBuscar() { return btnBuscar; }
    public Button getBtnAgregar() { return btnAgregar; }
    public Button getBtnEditar() { return btnEditar; }
    public Button getBtnEliminar() { return btnEliminar; }
    public Button getBtnTransferir() { return btnTransferir; }
    public ObservableList<Cuenta> getCuentasData() { return cuentasData; }
}