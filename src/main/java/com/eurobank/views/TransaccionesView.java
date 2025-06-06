package com.eurobank.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransaccionesView {
    private VBox view;
    private ComboBox<String> tipoCombo;
    private TextField cuentaDestinoField;
    private TextField montoField;
    private Button aceptarButton;
    private Button cancelarButton;
    private Label lbCuentaOrigenObtenida;
    private Label lbSucursalObtenida;
    private RadioButton rbSaldo;
    private RadioButton rbCredito;
    private HBox fondoSelectionBox;

    public TransaccionesView() {
        crearVista();
    }

    private void crearVista() {
        // Contenedor principal
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(30, 40, 30, 40));
        mainContainer.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        mainContainer.setEffect(new javafx.scene.effect.DropShadow(10, Color.gray(0, 0.2)));

        // Título
        Label title = new Label("Realizar Transacción");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#2c3e50"));

        // Icono
        ImageView icon = new ImageView(new Image("https://static.vecteezy.com/system/resources/previews/000/495/375/non_2x/vector-transaction-line-black-icon.jpg"));
        icon.setFitWidth(60);
        icon.setFitHeight(60);

        // Campos del formulario
        GridPane formGrid = new GridPane();
        formGrid.setVgap(15);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);

        // Cuenta origen (solo lectura)
        Label lbCuentaOrigen = createLabel("Cuenta origen");
        lbCuentaOrigenObtenida = createValueLabel(" ");

        // Sucursal (solo lectura)
        Label lbSucursal = createLabel("Sucursal");
        lbSucursalObtenida = createValueLabel(" ");

        // Tipo de transacción
        Label typeLabel = createLabel("Tipo de transacción");
        tipoCombo = new ComboBox<>();
        tipoCombo.getItems().addAll("Transferencia", "Depósito", "Retiro");
        tipoCombo.setPromptText("Seleccione un tipo");
        tipoCombo.setMinWidth(300);
        tipoCombo.setStyle("-fx-font-size: 14px;");

        // Cuenta destino
        Label accountLabel = createLabel("Cuenta destino");
        cuentaDestinoField = new TextField();
        cuentaDestinoField.setPromptText("Ingrese número de cuenta");
        cuentaDestinoField.setMinWidth(300);

        // Monto
        Label amountLabel = createLabel("Monto");
        montoField = new TextField();
        montoField.setPromptText("0.00");
        montoField.setMinWidth(300);
        montoField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Fecha (automática)
        Label dateLabel = createLabel("Fecha");
        Label dateValue = createValueLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Agregar elementos al grid
        formGrid.addRow(0, lbCuentaOrigen, lbCuentaOrigenObtenida);
        formGrid.addRow(1, lbSucursal, lbSucursalObtenida);
        formGrid.addRow(2, typeLabel, tipoCombo);
        formGrid.addRow(3, accountLabel, cuentaDestinoField);
        formGrid.addRow(4, amountLabel, montoField);
        formGrid.addRow(5, dateLabel, dateValue);

        // Contenedor para selección de fondo (saldo/crédito)
        fondoSelectionBox = new HBox(15);
        fondoSelectionBox.setAlignment(Pos.CENTER);
        fondoSelectionBox.setVisible(false); // Inicialmente oculto

        ToggleGroup fondoGroup = new ToggleGroup();

        rbSaldo = new RadioButton("Usar Saldo");
        rbSaldo.setToggleGroup(fondoGroup);
        rbSaldo.setSelected(true);
        rbSaldo.setStyle("-fx-font-size: 14px;");

        rbCredito = new RadioButton("Usar Crédito");
        rbCredito.setToggleGroup(fondoGroup);
        rbCredito.setStyle("-fx-font-size: 14px;");

        fondoSelectionBox.getChildren().addAll(rbSaldo, rbCredito);

        // Botones
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        cancelarButton = new Button("Cancelar");
        cancelarButton.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #7f8c8d; -fx-font-weight: bold;");
        cancelarButton.setMinWidth(120);

        aceptarButton = new Button("Aceptar");
        aceptarButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        aceptarButton.setMinWidth(120);

        buttonBox.getChildren().addAll(cancelarButton, aceptarButton);

        // Agregar todo al contenedor principal
        VBox formContainer = new VBox(15, formGrid, fondoSelectionBox);
        mainContainer.getChildren().addAll(icon, title, formContainer, buttonBox);

        // Configurar la vista
        view = mainContainer;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        label.setTextFill(Color.web("#34495e"));
        return label;
    }

    private Label createValueLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", 14));
        label.setTextFill(Color.web("#2c3e50"));
        label.setStyle("-fx-border-color: #ddd; -fx-border-width: 1px; -fx-border-radius: 4px; " +
                "-fx-padding: 8px; -fx-background-color: #f9f9f9;");
        label.setMinWidth(300);
        label.setMinHeight(35);
        return label;
    }

    // Métodos para acceder a los componentes
    public VBox getView() {
        return view;
    }

    public ComboBox<String> getTipoCombo() {
        return tipoCombo;
    }

    public TextField getCuentaDestinoField() {
        return cuentaDestinoField;
    }

    public TextField getMontoField() {
        return montoField;
    }

    public Button getAceptarButton() {
        return aceptarButton;
    }

    public Button getCancelarButton() {
        return cancelarButton;
    }

    public Label getLbCuentaOrigenObtenida() {
        return lbCuentaOrigenObtenida;
    }

    public Label getLbSucursalObtenida() {
        return lbSucursalObtenida;
    }

    public RadioButton getRbSaldo() {
        return rbSaldo;
    }

    public RadioButton getRbCredito() {
        return rbCredito;
    }

    public HBox getFondoSelectionBox() {
        return fondoSelectionBox;
    }

    public void setLbCuentaOrigenObtenida(String cuentaOrigen) {
        this.lbCuentaOrigenObtenida.setText(cuentaOrigen);
    }

    public void setLbSucursalObtenida(String sucursal) {
        this.lbSucursalObtenida.setText(sucursal);
    }

    public void mostrarOpcionCredito(boolean mostrar) {
        fondoSelectionBox.setVisible(mostrar);
        if (mostrar) {
            rbSaldo.setSelected(true); // Seleccionar saldo por defecto
        }
    }
}