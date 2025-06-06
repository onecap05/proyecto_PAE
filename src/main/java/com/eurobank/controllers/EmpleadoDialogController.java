package com.eurobank.controllers;

import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EmpleadoDialogController {

    public boolean validarEmpleadoDesdeDialogo(TextField tfNombre,
                                               TextField tfDireccion,
                                               DatePicker dpFechaNacimiento,
                                               TextField tfGenero,
                                               TextField tfSalario,
                                               ComboBox<RolEmpleado> cbRol,
                                               TextField tfUsuario,
                                               PasswordField pfPassword,
                                               TextField tfSucursal,
                                               ActionEvent event) {

        try {

            String textoFecha = dpFechaNacimiento.getEditor().getText();

            if (textoFecha != null && !textoFecha.isBlank()) {
                dpFechaNacimiento.setValue(dpFechaNacimiento.getConverter().fromString(textoFecha));
            }

            double salario = Double.parseDouble(tfSalario.getText());

            Empleado tempEmpleado = new Empleado();
            tempEmpleado.setNombre(tfNombre.getText());
            tempEmpleado.setDireccion(tfDireccion.getText());
            tempEmpleado.setFechaNacimiento(dpFechaNacimiento.getValue());
            tempEmpleado.setGenero(tfGenero.getText());
            tempEmpleado.setSalario(salario);
            tempEmpleado.setRol(cbRol.getValue());
            tempEmpleado.setUsuario(tfUsuario.getText());
            tempEmpleado.setPassword(pfPassword.getText());
            tempEmpleado.setIdSucursal(tfSucursal.getText());

            List<String> errores = tempEmpleado.validarCamposBasicos();
            if (!errores.isEmpty()) {
                mostrarAlerta("Corrija los siguientes errores:\n" + String.join("\n", errores));
                event.consume();
                return false;
            }

        } catch (DateTimeParseException e) {

            mostrarAlerta("Formato de fecha inválido. Use el formato correcto (por ejemplo, yyyy-MM-dd).");
            event.consume();
            return false;

        } catch (NumberFormatException e) {

            mostrarAlerta("El salario debe ser un número válido.");
            event.consume();
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error de Validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
