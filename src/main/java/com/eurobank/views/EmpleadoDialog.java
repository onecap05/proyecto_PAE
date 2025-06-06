package com.eurobank.views;

import com.eurobank.controllers.EmpleadoDialogController;
import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import java.util.Optional;

public class EmpleadoDialog {

    public static Empleado mostrarDialogoAgregar() {
        EmpleadoDialogController controller = new EmpleadoDialogController();

        Dialog<Empleado> dialog = new Dialog<>();
        dialog.setTitle("Agregar Nuevo Empleado");
        dialog.setHeaderText("Complete los datos del empleado");

        ButtonType agregarButtonType = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(agregarButtonType, ButtonType.CANCEL);

        GridPane grid = crearGrid();
        TextField tfNombre = new TextField();
        TextField tfDireccion = new TextField();
        DatePicker dpFechaNacimiento = new DatePicker();
        dpFechaNacimiento.getEditor().setDisable(true);
        dpFechaNacimiento.getEditor().setOpacity(1);
        TextField tfGenero = new TextField();
        TextField tfSalario = new TextField();
        ComboBox<RolEmpleado> cbRol = new ComboBox<>();
        cbRol.getItems().addAll(RolEmpleado.values());
        TextField tfUsuario = new TextField();
        PasswordField pfPassword = new PasswordField();
        TextField tfSucursal = new TextField();

        grid.add(new Label("Nombre:"), 0, 0); grid.add(tfNombre, 1, 0);
        grid.add(new Label("Dirección:"), 0, 1); grid.add(tfDireccion, 1, 1);
        grid.add(new Label("Fecha Nacimiento:"), 0, 2); grid.add(dpFechaNacimiento, 1, 2);
        grid.add(new Label("Género:"), 0, 3); grid.add(tfGenero, 1, 3);
        grid.add(new Label("Salario:"), 0, 4); grid.add(tfSalario, 1, 4);
        grid.add(new Label("Rol:"), 0, 5); grid.add(cbRol, 1, 5);
        grid.add(new Label("Usuario:"), 0, 6); grid.add(tfUsuario, 1, 6);
        grid.add(new Label("Contraseña:"), 0, 7); grid.add(pfPassword, 1, 7);
        grid.add(new Label("ID Sucursal:"), 0, 8); grid.add(tfSucursal, 1, 8);

        dialog.getDialogPane().setContent(grid);

        Node botonAgregar = dialog.getDialogPane().lookupButton(agregarButtonType);
        botonAgregar.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            controller.validarEmpleadoDesdeDialogo(
                    tfNombre, tfDireccion, dpFechaNacimiento, tfGenero, tfSalario,
                    cbRol, tfUsuario, pfPassword, tfSucursal, event
            );
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == agregarButtonType) {
                Empleado empleado = new Empleado();
                empleado.setNombre(tfNombre.getText());
                empleado.setDireccion(tfDireccion.getText());
                empleado.setFechaNacimiento(dpFechaNacimiento.getValue());
                empleado.setGenero(tfGenero.getText());
                empleado.setSalario(Double.parseDouble(tfSalario.getText()));
                empleado.setRol(cbRol.getValue());
                empleado.setUsuario(tfUsuario.getText());
                empleado.setPassword(pfPassword.getText());
                empleado.setIdSucursal(tfSucursal.getText());
                return empleado;
            }
            return null;
        });

        Optional<Empleado> resultado = dialog.showAndWait();
        return resultado.orElse(null);
    }

    public static Empleado mostrarDialogoEditar(Empleado empleado) {
        EmpleadoDialogController controller = new EmpleadoDialogController();

        Dialog<Empleado> dialog = new Dialog<>();
        dialog.setTitle("Editar Empleado");
        dialog.setHeaderText("Edite los datos del empleado");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = crearGrid();
        TextField tfNombre = new TextField(empleado.getNombre());
        TextField tfDireccion = new TextField(empleado.getDireccion());
        DatePicker dpFechaNacimiento = new DatePicker(empleado.getFechaNacimiento());
        dpFechaNacimiento.getEditor().setDisable(true);
        dpFechaNacimiento.getEditor().setOpacity(1);
        TextField tfGenero = new TextField(empleado.getGenero());
        TextField tfSalario = new TextField(String.valueOf(empleado.getSalario()));
        ComboBox<RolEmpleado> cbRol = new ComboBox<>();
        cbRol.getItems().addAll(RolEmpleado.values());
        cbRol.setValue(empleado.getRol());
        TextField tfUsuario = new TextField(empleado.getUsuario());
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Dejar en blanco para no cambiar");
        TextField tfSucursal = new TextField(empleado.getIdSucursal());

        grid.add(new Label("Nombre:"), 0, 0); grid.add(tfNombre, 1, 0);
        grid.add(new Label("Dirección:"), 0, 1); grid.add(tfDireccion, 1, 1);
        grid.add(new Label("Fecha Nacimiento:"), 0, 2); grid.add(dpFechaNacimiento, 1, 2);
        grid.add(new Label("Género:"), 0, 3); grid.add(tfGenero, 1, 3);
        grid.add(new Label("Salario:"), 0, 4); grid.add(tfSalario, 1, 4);
        grid.add(new Label("Rol:"), 0, 5); grid.add(cbRol, 1, 5);
        grid.add(new Label("Usuario:"), 0, 6); grid.add(tfUsuario, 1, 6);
        grid.add(new Label("Contraseña:"), 0, 7); grid.add(pfPassword, 1, 7);
        grid.add(new Label("ID Sucursal:"), 0, 8); grid.add(tfSucursal, 1, 8);

        dialog.getDialogPane().setContent(grid);

        Node botonGuardar = dialog.getDialogPane().lookupButton(guardarButtonType);
        botonGuardar.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            controller.validarEmpleadoDesdeDialogo(
                    tfNombre, tfDireccion, dpFechaNacimiento, tfGenero, tfSalario,
                    cbRol, tfUsuario, pfPassword, tfSucursal, event
            );
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                empleado.setNombre(tfNombre.getText());
                empleado.setDireccion(tfDireccion.getText());
                empleado.setFechaNacimiento(dpFechaNacimiento.getValue());
                empleado.setGenero(tfGenero.getText());
                empleado.setSalario(Double.parseDouble(tfSalario.getText()));
                empleado.setRol(cbRol.getValue());
                empleado.setUsuario(tfUsuario.getText());

                if (!pfPassword.getText().isEmpty()) {
                    empleado.setPassword(pfPassword.getText());
                }

                empleado.setIdSucursal(tfSucursal.getText());
                return empleado;
            }
            return null;
        });

        Optional<Empleado> resultado = dialog.showAndWait();
        return resultado.orElse(null);
    }

    private static GridPane crearGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        return grid;
    }
}
