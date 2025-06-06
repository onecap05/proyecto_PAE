package org.example;

import com.eurobank.controllers.EmpleadosController;
import com.eurobank.models.DAO.EmpleadoDAO;
import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainEmpleados extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Configuración de la ventana principal
        primaryStage.setTitle("EuroBank - Sistema de Gestión");

        // Botón para abrir la gestión de empleados
        Button btnGestionEmpleados = new Button("Abrir Gestión de Empleados");
        btnGestionEmpleados.setStyle("-fx-font-size: 14px; -fx-padding: 10 20;");
        btnGestionEmpleados.setOnAction(e -> {
            EmpleadosController empleadosController = new EmpleadosController();
            empleadosController.mostrarVentana();
        });

        // Layout simple
        VBox root = new VBox(20, btnGestionEmpleados);
        root.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}