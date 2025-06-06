package com.eurobank.controllers;

import com.eurobank.models.DAO.EmpleadoDAO;
import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import com.eurobank.utils.VentanasEmergentes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController implements EventHandler<ActionEvent> {
    private Stage stage;
    private TextField txtUsuario;
    private PasswordField txtPassword;
    VentanasEmergentes ventanasEmergentes = new VentanasEmergentes();
    public static String idSucursal = null;


    public LoginController(Stage stage, TextField txtUsuario, PasswordField txtPassword) {
        this.stage = stage;
        this.txtUsuario = txtUsuario;
        this.txtPassword = txtPassword;
    }

    @Override
    public void handle(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        buscarUsuario(usuario, password);

    }

    private void buscarUsuario (String usuario, String password) {
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();

       try {

           Empleado empleadoEnconstrado = empleadoDAO.buscarEmpleadoPorUsuario(usuario);
              if (empleadoEnconstrado != null && empleadoEnconstrado.getPassword().equals(password)) {

                idSucursal = empleadoEnconstrado.getIdSucursal();

                validarTipoUsuario(empleadoEnconstrado);
                abrirMenuPrincipal(empleadoEnconstrado);

              } else {

                ventanasEmergentes.mostrarAlerta("Empleado invalido", "el empleado no existe", "El usuario o la contraseña son incorrectos. Por favor, inténtalo de nuevo.");
              }

        } catch (Exception e) {
            e.getMessage();
        }

    }

    private void validarTipoUsuario(Empleado empleado) {

        if (empleado.getRol().equals(RolEmpleado.CAJERO)) {
            ventanasEmergentes.mostrarAlerta("Bienvenido", "Inicio de sesión exitoso", "Has iniciado sesión como Cajero.");
        } else if (empleado.getRol().equals(RolEmpleado.ADMINISTRADOR)) {
            ventanasEmergentes.mostrarAlerta("Bienvenido", "Inicio de sesión exitoso", "Has iniciado sesión como Administrador.");
        } else if (empleado.getRol().equals(RolEmpleado.GERENTE)) {
            ventanasEmergentes.mostrarAlerta("Bienvenido", "Inicio de sesión exitoso", "Has iniciado sesión como Gerente.");
        } else if (empleado.getRol().equals(RolEmpleado.EJECUTIVO_CUENTA)) {
            ventanasEmergentes.mostrarAlerta("Bienvenido", "Inicio de sesión exitoso", "Has iniciado sesión como Ejecutivo de Cuenta.");
        } else {
            ventanasEmergentes.mostrarAlerta("Error", "Tipo de usuario no reconocido", "Por favor, contacta al administrador del sistema.");
        }
    }

    private void abrirMenuPrincipal(Empleado empleado) {
        stage.close();

        String mensajeBienvenida = "Bienvenido " + empleado.getNombre() +
                "\nRol: " + empleado.getRol().toString();
        ventanasEmergentes.mostrarAlerta("Bienvenido", "Inicio de sesión exitoso", mensajeBienvenida);

        Stage menuStage = new Stage();
        MainAppController menuController = new MainAppController(menuStage, empleado.getRol());
    }

}