package com.eurobank;

import javafx.application.Application;
import javafx.stage.Stage;
import com.eurobank.views.LoginView;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("EuroBank - Sistema de Administración");
        LoginView.mostrar(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}