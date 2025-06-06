package com.eurobank.controllers;

import com.eurobank.models.Cuenta;
import com.eurobank.models.TipoCuenta;
import com.eurobank.utils.VentanasEmergentes;
import com.eurobank.views.CuentaDialog;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class CuentaDialogController {
    private CuentaDialog view;

    public void mostrarFormulario(Stage owner, Cuenta cuenta, Consumer<Cuenta> onGuardar) {
        view = new CuentaDialog();
        String titulo = cuenta == null ? "Agregar Nueva Cuenta" : "Editar Cuenta";
        view.mostrarFormulario(owner, cuenta, titulo);

        view.getBtnGuardar().setOnAction(e -> {
            if (validarCampos()) {
                Cuenta nuevaCuenta = new Cuenta(
                        view.getNumeroCuenta(),
                        view.getTipo(),
                        view.getSaldo(),
                        view.getLimiteCredito(),
                        view.getIdCliente()
                );

                onGuardar.accept(nuevaCuenta);
                view.cerrar();
            }
        });

        view.getBtnCancelar().setOnAction(e -> view.cerrar());
    }

    private boolean validarCampos() {
        if (view.getTipo() == null) {
            VentanasEmergentes.mostrarAlerta("Error", "Tipo requerido",
                    "Debe seleccionar un tipo de cuenta");
            return false;
        }

        if (view.getIdCliente().isEmpty()) {
            VentanasEmergentes.mostrarAlerta("Error", "Cliente requerido",
                    "Debe especificar un ID de cliente");
            return false;
        }

        // Validación especial para cuentas empresariales
        if (view.getTipo() == TipoCuenta.EMPRESARIAL && view.getLimiteCredito() <= 0) {
            VentanasEmergentes.mostrarAlerta("Error", "Límite requerido",
                    "Las cuentas empresariales deben tener un límite de crédito mayor a cero");
            return false;
        }

        return true;
    }
}