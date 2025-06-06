package com.eurobank.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class VentanasEmergentes {

    public static void mostrarAlerta(String titulo, String cabecera, String contenido) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    public static String mostrarAlertaConOpciones(String titulo, String cabecera, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);

        ButtonType btnAceptar = new ButtonType("Aceptar");
        ButtonType btnGenerarComprobante = new ButtonType("Generar Comprobante");

        alerta.getButtonTypes().setAll(btnAceptar, btnGenerarComprobante);

        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent()) {
            if (resultado.get() == btnAceptar) {
                return "Aceptar";
            } else if (resultado.get() == btnGenerarComprobante) {
                return "Generar Comprobante";
            }
        }
        return "Ninguna opción seleccionada";
    }
}
