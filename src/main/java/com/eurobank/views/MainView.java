package com.eurobank.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
    public static void mostrar(Stage stage) {
        BorderPane root = new BorderPane();

        // Menú superior
        MenuBar menuBar = new MenuBar();

        // Menú Administración
        Menu menuAdmin = new Menu("Administración");
        MenuItem itemEmpleados = new MenuItem("Empleados");
        menuAdmin.getItems().add(itemEmpleados);

        // Menú Operaciones
        Menu menuOperaciones = new Menu("Operaciones");
        MenuItem itemClientes = new MenuItem("Clientes");
        MenuItem itemCuentas = new MenuItem("Cuentas");
        MenuItem itemTransacciones = new MenuItem("Transacciones");
        MenuItem itemSucursales = new MenuItem("Sucursales");
        menuOperaciones.getItems().addAll(itemClientes, itemCuentas, itemTransacciones, itemSucursales);

        menuBar.getMenus().addAll(menuAdmin, menuOperaciones);

        // Contenido central
        VBox center = new VBox(10);
        center.getChildren().add(new Label("Bienvenido al Sistema EuroBank"));

        root.setTop(menuBar);
        root.setCenter(center);

        // Manejadores de eventos
        itemEmpleados.setOnAction(e -> EmpleadosView.mostrar());
        itemClientes.setOnAction(e -> ClientesView.mostrar());
        itemCuentas.setOnAction(e -> CuentasView.mostrar());
        itemTransacciones.setOnAction(e -> TransaccionesView.mostrar());
        itemSucursales.setOnAction(e -> SucursalesView.mostrar());

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }
}