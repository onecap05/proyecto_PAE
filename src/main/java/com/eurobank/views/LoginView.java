package com.eurobank.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.eurobank.controllers.LoginController;

public class LoginView {
    public static void mostrar(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtUsuario = new TextField();
        PasswordField txtPassword = new PasswordField();
        Button btnLogin = new Button("Iniciar Sesión");

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(txtUsuario, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(txtPassword, 1, 1);
        grid.add(btnLogin, 1, 2);

        btnLogin.setOnAction(new LoginController(stage, txtUsuario, txtPassword));

        Scene scene = new Scene(grid, 300, 200);
        stage.setScene(scene);
    }
}