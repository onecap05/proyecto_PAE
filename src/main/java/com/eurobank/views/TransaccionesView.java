package com.eurobank.views;

import com.eurobank.controllers.TransaccionesController;
import com.eurobank.models.Transaccion;
import com.eurobank.models.DAO.TransaccionDAO;
import com.eurobank.models.excepciones.TransaccionFallidaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class TransaccionesView extends BorderPane {
    private TableView<Transaccion> tablaTransacciones;
    private TransaccionDAO transaccionDAO;
    private ComboBox<String> cbTipoTransaccion;
    private TextField tfMonto, tfCuentaOrigen, tfCuentaDestino, tfSucursal;
    private Button btnAgregar, btnExportar;
    private DatePicker dpFecha;

    public TransaccionesView() {
        transaccionDAO = new TransaccionDAO();
        inicializarUI();
        cargarTransacciones();
    }

    private void inicializarUI() {
        // Configuración del título
        Label titulo = new Label("Gestión de Transacciones");
        titulo.setFont(new Font(20));
        setTop(titulo);

        // Configuración de la tabla
        configurarTabla();

        // Configuración del formulario
        VBox formulario = crearFormulario();

        // Configuración del panel central
        VBox panelCentral = new VBox(10, tablaTransacciones, formulario);
        panelCentral.setPadding(new Insets(10));
        setCenter(panelCentral);

        // Configuración de los botones de acción
        HBox botones = new HBox(10, btnAgregar, btnExportar);
        botones.setPadding(new Insets(10));
        setBottom(botones);
    }

    private void configurarTabla() {
        tablaTransacciones = new TableView<>();

        TableColumn<Transaccion, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Transaccion, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Transaccion, Double> colMonto = new TableColumn<>("Monto");
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

        TableColumn<Transaccion, LocalDateTime> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<Transaccion, String> colSucursal = new TableColumn<>("Sucursal");
        colSucursal.setCellValueFactory(new PropertyValueFactory<>("idSucursal"));

        tablaTransacciones.getColumns().addAll(colId, colTipo, colMonto, colFecha, colSucursal);
        tablaTransacciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private VBox crearFormulario() {
        Label lblTipo = new Label("Tipo de Transacción:");
        cbTipoTransaccion = new ComboBox<>();
        cbTipoTransaccion.getItems().addAll("DEPOSITO", "RETIRO", "TRANSFERENCIA");
        cbTipoTransaccion.getSelectionModel().selectFirst();
        cbTipoTransaccion.setOnAction(e -> actualizarCamposFormulario());

        Label lblMonto = new Label("Monto:");
        tfMonto = new TextField();
        tfMonto.setPromptText("Ingrese el monto");

        Label lblCuentaOrigen = new Label("Cuenta Origen:");
        tfCuentaOrigen = new TextField();
        tfCuentaOrigen.setPromptText("Número de cuenta origen");

        Label lblCuentaDestino = new Label("Cuenta Destino:");
        tfCuentaDestino = new TextField();
        tfCuentaDestino.setPromptText("Número de cuenta destino");
        tfCuentaDestino.setDisable(true);

        Label lblSucursal = new Label("Sucursal:");
        tfSucursal = new TextField();
        tfSucursal.setPromptText("ID de sucursal");

        Label lblFecha = new Label("Fecha:");
        dpFecha = new DatePicker();
        dpFecha.setValue(LocalDateTime.now().toLocalDate());

        btnAgregar = new Button("Agregar Transacción");
        btnExportar = new Button("Exportar a CSV");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(lblTipo, 0, 0);
        grid.add(cbTipoTransaccion, 1, 0);
        grid.add(lblMonto, 0, 1);
        grid.add(tfMonto, 1, 1);
        grid.add(lblCuentaOrigen, 0, 2);
        grid.add(tfCuentaOrigen, 1, 2);
        grid.add(lblCuentaDestino, 0, 3);
        grid.add(tfCuentaDestino, 1, 3);
        grid.add(lblSucursal, 0, 4);
        grid.add(tfSucursal, 1, 4);
        grid.add(lblFecha, 0, 5);
        grid.add(dpFecha, 1, 5);

        return new VBox(10, grid);
    }

    private void actualizarCamposFormulario() {
        String tipo = cbTipoTransaccion.getValue();
        tfCuentaDestino.setDisable(!tipo.equals("TRANSFERENCIA"));
    }

    public void cargarTransacciones() {
        try {
            List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
            ObservableList<Transaccion> datos = FXCollections.observableArrayList(transacciones);
            tablaTransacciones.setItems(datos);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudieron cargar las transacciones", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void mostrarAlerta(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public void setController(TransaccionesController controller) {
        btnAgregar.setOnAction(e -> controller.agregarTransaccion());
        btnExportar.setOnAction(e -> controller.exportarTransacciones());
    }

    // Métodos para obtener datos del formulario
    public String getTipoTransaccion() {
        return cbTipoTransaccion.getValue();
    }

    public double getMonto() {
        try {
            return Double.parseDouble(tfMonto.getText());
        } catch (NumberFormatException e) {
            throw new TransaccionFallidaException("El monto debe ser un número válido");
        }
    }

    public String getCuentaOrigen() {
        String cuenta = tfCuentaOrigen.getText();
        if (cuenta == null || cuenta.trim().isEmpty()) {
            throw new TransaccionFallidaException("La cuenta origen es requerida");
        }
        return cuenta;
    }

    public String getCuentaDestino() {
        return tfCuentaDestino.getText();
    }

    public String getSucursal() {
        String sucursal = tfSucursal.getText();
        if (sucursal == null || sucursal.trim().isEmpty()) {
            throw new TransaccionFallidaException("La sucursal es requerida");
        }
        return sucursal;
    }

    public LocalDateTime getFecha() {
        return dpFecha.getValue().atStartOfDay();
    }

    public void limpiarFormulario() {
        tfMonto.clear();
        tfCuentaOrigen.clear();
        tfCuentaDestino.clear();
        tfSucursal.clear();
        dpFecha.setValue(LocalDateTime.now().toLocalDate());
    }

    public TableView<Transaccion> getTablaTransacciones() {
        return tablaTransacciones;
    }
}