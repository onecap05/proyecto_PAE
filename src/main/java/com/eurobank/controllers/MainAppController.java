package com.eurobank.controllers;

import com.eurobank.MainApp;
import com.eurobank.models.RolEmpleado;
import com.eurobank.views.CuentasView;
import com.eurobank.views.LoginView;
import com.eurobank.views.SucursalesView;
import com.eurobank.views.TransaccionesConsultaView;
import javafx.stage.Stage;

public class MainAppController {
    private MainApp view;
    private RolEmpleado rolUsuario;

    public MainAppController(Stage primaryStage, RolEmpleado rolUsuario) {
        this.rolUsuario = rolUsuario;
        this.view = new MainApp();
        view.mostrar(primaryStage);
        configurarAccesoSegunRol();
        configurarEventos();
    }

    private void configurarAccesoSegunRol() {
        switch(rolUsuario) {
            case CAJERO:
                view.getBtnEmpleados().setDisable(true);
                view.getBtnSucursales().setDisable(true);
                view.getBtnClientes().setDisable(true);
                break;

            case EJECUTIVO_CUENTA:
                view.getBtnEmpleados().setDisable(true);
                view.getBtnSucursales().setDisable(true);
                break;

            case GERENTE:
            case ADMINISTRADOR:
                break;
        }
    }

    private void configurarEventos() {
        view.getBtnSucursales().setOnAction(e -> {
            SucursalesView sucursalesView = new SucursalesView();
            Stage sucursalesStage = new Stage();
            sucursalesView.mostrar(sucursalesStage);
            new SucursalesController(sucursalesView, sucursalesStage);

        });

        view.getBtnEmpleados().setOnAction(e -> {
            EmpleadosController empleadosController = new EmpleadosController();
            empleadosController.mostrarVentana();
        });

        view.getBtnClientes().setOnAction(e -> {
            com.eurobank.views.ClientesView clientesView = new com.eurobank.views.ClientesView();
            Stage clientesStage = new Stage();
            clientesView.mostrar(clientesStage);
            new com.eurobank.controllers.ClientesController(clientesView, clientesStage);
        });

        view.getBtnCuentas().setOnAction(e -> {
            CuentasView cuentasView = new CuentasView();
            Stage cuentasStage = new Stage();
            cuentasView.mostrar(cuentasStage);
            new CuentasController(cuentasView, cuentasStage);
        });

        view.getBtnTransacciones().setOnAction(e -> {
            TransaccionesConsultaView transaccionesView = new TransaccionesConsultaView();
            Stage transaccionesStage = new Stage();
            transaccionesView.mostrarVentana();
            new TransaccionesConsultaController(transaccionesView);
        });

        view.getBtnCerrarSesion().setOnAction(e -> {
            cerrarSesion();
        });
    }

    private void cerrarSesion() {
        view.getPrimaryStage().close();

        Stage loginStage = new Stage();
        LoginView loginView = new LoginView();
        loginView.mostrar(loginStage);
    }
}