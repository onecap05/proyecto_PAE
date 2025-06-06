package com.eurobank.utils;

import javafx.scene.control.Alert;

public class VentanasEmergentes {

    public static void mostrarAlerta(String titulo, String cabecera, String contenido) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
