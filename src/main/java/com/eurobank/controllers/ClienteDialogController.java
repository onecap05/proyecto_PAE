package com.eurobank.controllers;

import com.eurobank.models.Cliente;
import com.eurobank.utils.Validaciones;
import com.eurobank.utils.VentanasEmergentes;
import com.eurobank.views.ClienteDialog;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Consumer;

public class ClienteDialogController {
    private ClienteDialog view;

    public void mostrarFormulario(Stage owner, Cliente cliente, Consumer<Cliente> onGuardar) {
        view = new ClienteDialog();
        String titulo = cliente == null ? "Registrar Nuevo Cliente" : "Editar Cliente";
        view.mostrarFormulario(owner, cliente, titulo);

        view.getBtnGuardar().setOnAction(e -> {
            if (validarCampos()) {
                Cliente nuevoCliente = new Cliente(
                        view.getIdFiscal(),
                        view.getNombre(),
                        view.getApellidos(),
                        view.getNacionalidad(),
                        view.getFechaNacimiento(),
                        view.getDireccion(),
                        view.getTelefono(),
                        view.getEmail()
                );

                onGuardar.accept(nuevoCliente);
                view.cerrar();
            }
        });

        view.getBtnCancelar().setOnAction(e -> view.cerrar());
    }

    private boolean validarCampos() {


        if (view.getIdFiscal().isEmpty()) {
            VentanasEmergentes.mostrarAlerta("Error", "ID Fiscal requerido",
                    "Debe ingresar un ID fiscal (DNI, NIE, CIF, etc.)");
            return false;
        }

        if (view.getNombre().isEmpty() || view.getApellidos().isEmpty()) {
            VentanasEmergentes.mostrarAlerta("Error", "Nombre requerido",
                    "Debe ingresar nombre y apellidos del cliente");
            return false;
        }

        Period edad = Period.between(view.getFechaNacimiento(), LocalDate.now());
        if (edad.getYears() < 18) {
            VentanasEmergentes.mostrarAlerta("Error", "Edad insuficiente",
                    "El cliente debe ser mayor de 18 años");
            return false;
        }

        if (view.getEmail().isEmpty() || !view.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            VentanasEmergentes.mostrarAlerta("Error", "Email inválido",
                    "Debe ingresar un email válido");
            return false;
        }

        if (!Validaciones.validarNumeroTelefono(view.getTelefono())) {
            VentanasEmergentes.mostrarAlerta("Error", "Teléfono inválido",
                    "Debe ingresar un número de teléfono válido");
            return false;
        }

        if (!Validaciones.validarNombre(view.getNombre()) || !Validaciones.validarNombre(view.getApellidos())) {
            VentanasEmergentes.mostrarAlerta("Error", "Nombre inválido",
                    "Los nombres y apellidos solo pueden contener letras y espacios");
            return false;
        }

        if (!Validaciones.validarNombre(view.getNacionalidad())) {
            VentanasEmergentes.mostrarAlerta("Error", "Nacionalidad inválida",
                    "La nacionalidad solo puede contener letras y espacios");
            return false;
        }


        return true;
    }
}