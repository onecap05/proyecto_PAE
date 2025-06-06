package com.eurobank.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.eurobank.controllers.LoginController;

public class LoginView {
    public static void mostrar(Stage stage) {
        // Crear el panel principal con fondo azul
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Agregar un fondo con gradiente azul
        grid.setStyle("-fx-background-color: linear-gradient(to bottom, #2a4b7c, #1a3a8f);");

        // Título de la aplicación
        Text titulo = new Text("EuroBank");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setFill(Color.WHITE);
        grid.add(titulo, 0, 0, 2, 1);

        // Subtítulo
        Text subtitulo = new Text("Inicio de Sesión");
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        subtitulo.setFill(Color.LIGHTGRAY);
        grid.add(subtitulo, 0, 1, 2, 1);

        // Espaciador
        grid.add(new Label(""), 0, 2);

        // Campo de usuario
        Label lblUsuario = new Label("Usuario:");
        lblUsuario.setTextFill(Color.WHITE);
        grid.add(lblUsuario, 0, 3);

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Ingrese su usuario");
        grid.add(txtUsuario, 1, 3);

        // Campo de contraseña
        Label lblPassword = new Label("Contraseña:");
        lblPassword.setTextFill(Color.WHITE);
        grid.add(lblPassword, 0, 4);

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Ingrese su contraseña");
        grid.add(txtPassword, 1, 4);

        // Panel de botones
        HBox hbButtons = new HBox(10);
        hbButtons.setAlignment(Pos.BOTTOM_RIGHT);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        btnCancelar.setOnAction(e -> stage.close());

        Button btnLogin = new Button("Iniciar Sesión");
        btnLogin.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnLogin.setOnAction(new LoginController(stage, txtUsuario, txtPassword));

        hbButtons.getChildren().addAll(btnCancelar, btnLogin);
        grid.add(hbButtons, 1, 5);

        // Mensaje de error (inicialmente oculto)
        Text txtError = new Text();
        txtError.setFill(Color.RED);
        grid.add(txtError, 1, 6);

        // Crear la escena
        Scene scene = new Scene(grid, 400, 300);
        stage.setTitle("EuroBank - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }
}