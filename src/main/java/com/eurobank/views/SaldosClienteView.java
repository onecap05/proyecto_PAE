package com.eurobank.views;

import com.eurobank.models.Cliente;
import com.eurobank.models.Cuenta;
import com.eurobank.models.DAO.CuentaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class SaldosClienteView {
    private TableView<Cuenta> tablaCuentas = new TableView<>();

    public void mostrar(Stage owner, Cliente cliente, CuentaDAO cuentaDAO) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Saldos del Cliente: " + cliente.getNombre() + " " + cliente.getApellidos());
        stage.initOwner(owner);

        configurarTabla();

        ObservableList<Cuenta> cuentas = FXCollections.observableArrayList(
                cuentaDAO.listarCuentasPorCliente(cliente.getIdFiscal())
        );
        tablaCuentas.setItems(cuentas);

        double saldoTotal = cuentas.stream().mapToDouble(Cuenta::getSaldo).sum();
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        Label lblSaldoTotal = new Label("Saldo Total: " + formatoMoneda.format(saldoTotal));
        lblSaldoTotal.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox vbox = new VBox(10, tablaCuentas, lblSaldoTotal);
        vbox.setPadding(new Insets(15));

        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void configurarTabla() {
        TableColumn<Cuenta, String> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        colNumero.setMinWidth(120);

        TableColumn<Cuenta, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTipo.setMinWidth(100);

        TableColumn<Cuenta, Double> colSaldo = new TableColumn<>("Saldo");
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        colSaldo.setMinWidth(120);

        TableColumn<Cuenta, Double> colLimite = new TableColumn<>("Límite Crédito");
        colLimite.setCellValueFactory(new PropertyValueFactory<>("limiteCredito"));
        colLimite.setMinWidth(120);

        tablaCuentas.getColumns().addAll(colNumero, colTipo, colSaldo, colLimite);
    }
}