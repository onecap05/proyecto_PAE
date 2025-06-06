package org.example;

import com.eurobank.controllers.SucursalesController;
import com.eurobank.views.SucursalesView;
import javafx.application.Application;
import javafx.stage.Stage;

public class SucursalesTest extends Application {

    public void start(Stage primaryStage) {
        SucursalesView view = new SucursalesView(); // Primero crear la vista
        new SucursalesController(view, primaryStage); // Luego el controlador
        view.mostrar(primaryStage); // Finalmente mostrar
    }

    public static void main(String[] args) {
        launch(args);
    }
}