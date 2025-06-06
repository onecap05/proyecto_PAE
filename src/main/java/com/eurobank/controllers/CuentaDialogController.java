package com.eurobank.controllers;

import com.eurobank.models.Cliente;
import com.eurobank.models.Cuenta;
import com.eurobank.models.DAO.ClienteDAO;
import com.eurobank.models.TipoCuenta;
import com.eurobank.utils.VentanasEmergentes;
import com.eurobank.views.CuentaDialog;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
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

        if (validarCliente(view.getIdCliente())) {
            VentanasEmergentes.mostrarAlerta("Error", "Cliente no encontrado",
                    "El cliente con ID " + view.getIdCliente() + " no existe");
            return false;
        }

        if (view.getTipo() == TipoCuenta.EMPRESARIAL && view.getLimiteCredito() <= 0) {
            VentanasEmergentes.mostrarAlerta("Error", "Límite requerido",
                    "Las cuentas empresariales deben tener un límite de crédito mayor a cero");
            return false;
        }

        return true;
    }

    public boolean validarCliente (String idCliente) {

        boolean esValido = false;

        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            Cliente cliente = clienteDAO.buscarClientePorIdFiscal(idCliente);
            if (cliente == null) {
                esValido = true;
            }
        } catch (IOException e) {
            VentanasEmergentes.mostrarAlerta("Error", "Error al buscar cliente",
                    "No se pudo verificar el cliente: " + e.getMessage());
        }

        return esValido;
    }


}