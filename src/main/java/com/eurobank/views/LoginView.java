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
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(30, 40, 30, 40));
        grid.setStyle("-fx-background-color: linear-gradient(to bottom, #2a4b7c, #1a3a8f);");

        Text titulo = new Text("EuroBank");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setFill(Color.WHITE);
        GridPane.setHalignment(titulo, javafx.geometry.HPos.CENTER);
        grid.add(titulo, 0, 0, 2, 1);

        Text subtitulo = new Text("Inicio de Sesión");
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitulo.setFill(Color.LIGHTGRAY);
        GridPane.setHalignment(subtitulo, javafx.geometry.HPos.CENTER);
        grid.add(subtitulo, 0, 1, 2, 1);

        grid.add(new Label(""), 0, 2);

        Label lblUsuario = new Label("Usuario:");
        lblUsuario.setTextFill(Color.WHITE);
        lblUsuario.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        grid.add(lblUsuario, 0, 3);

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Ingrese su usuario");
        txtUsuario.setPrefWidth(200);
        grid.add(txtUsuario, 1, 3);

        Label lblPassword = new Label("Contraseña:");
        lblPassword.setTextFill(Color.WHITE);
        lblPassword.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        grid.add(lblPassword, 0, 4);

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Ingrese su contraseña");
        txtPassword.setPrefWidth(200);
        grid.add(txtPassword, 1, 4);

        HBox hbButtons = new HBox(15);
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setPadding(new Insets(20, 0, 0, 0));

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        btnCancelar.setPrefWidth(100);
        btnCancelar.setOnAction(e -> stage.close());

        Button btnLogin = new Button("Iniciar Sesión");
        btnLogin.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnLogin.setPrefWidth(100);
        btnLogin.setOnAction(new LoginController(stage, txtUsuario, txtPassword));

        hbButtons.getChildren().addAll(btnCancelar, btnLogin);
        grid.add(hbButtons, 0, 5, 2, 1);

        Text txtError = new Text();
        txtError.setFill(Color.RED);
        GridPane.setHalignment(txtError, javafx.geometry.HPos.CENTER);
        grid.add(txtError, 0, 6, 2, 1);

        Scene scene = new Scene(grid, 400, 350);
        stage.setTitle("EuroBank - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}