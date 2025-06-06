package com.eurobank;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainApp {
    private final Button btnEmpleados = new Button("Administración de Empleados");
    private final Button btnSucursales = new Button("Administración de Sucursales");
    private final Button btnCuentas = new Button("Gestión de Cuentas");
    private final Button btnTransacciones = new Button("Registro de Transacciones");
    private final Button btnClientes = new Button("Gestión de Clientes");
    private final Button btnCerrarSesion = new Button("Cerrar Sesión");
    private Stage primaryStage;

    public void mostrar(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("EuroBank - Sistema de Gestión Bancaria");

        // Header bar
        HBox header = new HBox();
        header.setStyle("-fx-background-color: #223366;");
        header.setPadding(new Insets(20, 0, 20, 0));
        header.setAlignment(Pos.CENTER);

        javafx.scene.control.Label titulo = new javafx.scene.control.Label("EUROBANK");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setTextFill(Color.WHITE);
        header.getChildren().add(titulo);

        // Button style and width
        String buttonStyle = "-fx-background-color: #223366; -fx-text-fill: white; -fx-font-size: 14px;";
        double buttonWidth = 220;

        btnEmpleados.setStyle(buttonStyle);
        btnEmpleados.setPrefWidth(buttonWidth);
        btnSucursales.setStyle(buttonStyle);
        btnSucursales.setPrefWidth(buttonWidth);
        btnCuentas.setStyle(buttonStyle);
        btnCuentas.setPrefWidth(buttonWidth);
        btnTransacciones.setStyle(buttonStyle);
        btnTransacciones.setPrefWidth(buttonWidth);
        btnClientes.setStyle(buttonStyle);
        btnClientes.setPrefWidth(buttonWidth);
        btnCerrarSesion.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px;");
        btnCerrarSesion.setPrefWidth(buttonWidth);

        // VBox for menu buttons
        VBox menuBox = new VBox(15);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(30, 0, 30, 0));
        menuBox.getChildren().addAll(
                btnEmpleados,
                btnSucursales,
                btnClientes,
                btnCuentas,
                btnTransacciones
        );

        // Logout button container
        HBox logoutBox = new HBox(btnCerrarSesion);
        logoutBox.setAlignment(Pos.CENTER);
        logoutBox.setPadding(new Insets(20, 0, 0, 0));

        // Main center VBox
        VBox centerBox = new VBox(10, menuBox, logoutBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setBackground(new Background(new BackgroundFill(Color.web("#f5f5f5"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Layout
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Button getBtnEmpleados() { return btnEmpleados; }
    public Button getBtnSucursales() { return btnSucursales; }
    public Button getBtnCuentas() { return btnCuentas; }
    public Button getBtnTransacciones() { return btnTransacciones; }
    public Button getBtnClientes() { return btnClientes; }
    public Button getBtnCerrarSesion() { return btnCerrarSesion; }
    public Stage getPrimaryStage() { return primaryStage; }
}