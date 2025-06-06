package com.eurobank.views;

import com.eurobank.models.Cliente;
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

public class ClientesView {
    private final TableView<Cliente> tablaClientes = new TableView<>();
    private final TextField txtBusqueda = new TextField();
    private final Button btnBuscar = new Button("Buscar");
    private final Button btnAgregar = new Button("Agregar");
    private final Button btnEditar = new Button("Editar");
    private final Button btnEliminar = new Button("Eliminar");
    private final Button btnConsultarSaldos = new Button("Consultar Saldos");
    private final ObservableList<Cliente> clientesData = FXCollections.observableArrayList();

    public ClientesView() {
        configurarTabla();
    }

    public void mostrar(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Clientes - EuroBank");

        HBox barraBusqueda = new HBox(10,
                new Label("Buscar:"),
                txtBusqueda,
                btnBuscar
        );
        barraBusqueda.setPadding(new Insets(10));

        HBox botoneraSuperior = new HBox(10, btnAgregar, btnEditar, btnEliminar);
        HBox botoneraInferior = new HBox(10, btnConsultarSaldos);
        botoneraSuperior.setPadding(new Insets(10));
        botoneraInferior.setPadding(new Insets(10));

        VBox vbox = new VBox(10, barraBusqueda, tablaClientes, botoneraSuperior, botoneraInferior);
        vbox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurarTabla() {
        TableColumn<Cliente, String> colIdFiscal = new TableColumn<>("ID Fiscal");
        colIdFiscal.setCellValueFactory(new PropertyValueFactory<>("idFiscal"));
        colIdFiscal.setMinWidth(120);

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setMinWidth(120);

        TableColumn<Cliente, String> colApellidos = new TableColumn<>("Apellidos");
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colApellidos.setMinWidth(120);

        TableColumn<Cliente, String> colNacionalidad = new TableColumn<>("Nacionalidad");
        colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        colNacionalidad.setMinWidth(100);

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTelefono.setMinWidth(100);

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setMinWidth(150);

        TableColumn<Cliente, Boolean> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoActivo"));
        colEstado.setMinWidth(80);

        tablaClientes.getColumns().addAll(colIdFiscal, colNombre, colApellidos,
                colNacionalidad, colTelefono, colEmail, colEstado);
        tablaClientes.setItems(clientesData);
    }

    public TableView<Cliente> getTablaClientes() { return tablaClientes; }
    public TextField getTxtBusqueda() { return txtBusqueda; }
    public Button getBtnBuscar() { return btnBuscar; }
    public Button getBtnAgregar() { return btnAgregar; }
    public Button getBtnEditar() { return btnEditar; }
    public Button getBtnEliminar() { return btnEliminar; }
    public Button getBtnConsultarSaldos() { return btnConsultarSaldos; }
    public ObservableList<Cliente> getClientesData() { return clientesData; }
}