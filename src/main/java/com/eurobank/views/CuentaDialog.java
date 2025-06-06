package com.eurobank.views;

import com.eurobank.models.Cuenta;
import com.eurobank.models.TipoCuenta;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CuentaDialog {
    private TextField txtNumeroCuenta;
    private ComboBox<TipoCuenta> cbTipo;
    private TextField txtSaldo;
    private TextField txtLimiteCredito;
    private TextField txtIdCliente;
    private Button btnGuardar;
    private Button btnCancelar;
    private Stage stage;

    public void mostrarFormulario(Stage owner, Cuenta cuenta, String titulo) {
        stage = new Stage();
        stage.setTitle(titulo);
        stage.initOwner(owner);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Configurar controles
        cbTipo = new ComboBox<>();
        cbTipo.getItems().setAll(TipoCuenta.values());

        txtNumeroCuenta = new TextField();
        txtNumeroCuenta.setDisable(true); // No editable, se genera automáticamente

        txtSaldo = new TextField();
        txtLimiteCredito = new TextField();
        txtIdCliente = new TextField();

        // Si estamos editando, cargar los datos
        if (cuenta != null) {
            txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
            cbTipo.setValue(cuenta.getTipo());
            txtSaldo.setText(String.valueOf(cuenta.getSaldo()));
            txtLimiteCredito.setText(String.valueOf(cuenta.getLimiteCredito()));
            txtIdCliente.setText(cuenta.getIdCliente());
        } else {
            cbTipo.setValue(TipoCuenta.CORRIENTE);
            txtSaldo.setText("0.0");
            txtLimiteCredito.setText("0.0");
        }

        // Configurar validación para campos numéricos
        txtSaldo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtSaldo.setText(oldValue);
            }
        });

        txtLimiteCredito.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtLimiteCredito.setText(oldValue);
            }
        });

        // Añadir controles al grid
        grid.add(new Label("Número de Cuenta:"), 0, 0);
        grid.add(txtNumeroCuenta, 1, 0);
        grid.add(new Label("Tipo de Cuenta:"), 0, 1);
        grid.add(cbTipo, 1, 1);
        grid.add(new Label("Saldo:"), 0, 2);
        grid.add(txtSaldo, 1, 2);
        grid.add(new Label("Límite de Crédito:"), 0, 3);
        grid.add(txtLimiteCredito, 1, 3);
        grid.add(new Label("ID Cliente:"), 0, 4);
        grid.add(txtIdCliente, 1, 4);

        btnGuardar = new Button("Guardar");
        btnCancelar = new Button("Cancelar");

        GridPane botones = new GridPane();
        botones.setHgap(10);
        botones.add(btnCancelar, 0, 0);
        botones.add(btnGuardar, 1, 0);

        grid.add(botones, 1, 5);

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    // Getters
    public String getNumeroCuenta() { return txtNumeroCuenta.getText(); }
    public TipoCuenta getTipo() { return cbTipo.getValue(); }
    public double getSaldo() { return Double.parseDouble(txtSaldo.getText()); }
    public double getLimiteCredito() { return Double.parseDouble(txtLimiteCredito.getText()); }
    public String getIdCliente() { return txtIdCliente.getText(); }
    public Button getBtnGuardar() { return btnGuardar; }
    public Button getBtnCancelar() { return btnCancelar; }
    public void cerrar() { stage.close(); }
}