package org.example;

import com.eurobank.views.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Open the LoginView window
        LoginView.mostrar(primaryStage);
    }

    public static void main(String[] args) {
        launch(args); // Initialize JavaFX runtime
    }
}