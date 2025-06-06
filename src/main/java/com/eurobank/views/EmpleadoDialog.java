package com.eurobank.views;

import com.eurobank.controllers.EmpleadoDialogController;
import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import java.util.Optional;
import java.util.stream.Stream;

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

        TextField tfHorarioTrabajo = new TextField();
        TextField tfNumeroVentanilla = new TextField();
        TextField tfClientesAsignados = new TextField();
        TextField tfEspecializacion = new TextField();
        TextField tfNivelAcceso = new TextField();
        TextField tfAnosExperiencia = new TextField();

        grid.add(new Label("Nombre:"), 0, 0); grid.add(tfNombre, 1, 0);
        grid.add(new Label("Dirección:"), 0, 1); grid.add(tfDireccion, 1, 1);
        grid.add(new Label("Fecha Nacimiento:"), 0, 2); grid.add(dpFechaNacimiento, 1, 2);
        grid.add(new Label("Género:"), 0, 3); grid.add(tfGenero, 1, 3);
        grid.add(new Label("Salario:"), 0, 4); grid.add(tfSalario, 1, 4);
        grid.add(new Label("Rol:"), 0, 5); grid.add(cbRol, 1, 5);
        grid.add(new Label("Usuario:"), 0, 6); grid.add(tfUsuario, 1, 6);
        grid.add(new Label("Contraseña:"), 0, 7); grid.add(pfPassword, 1, 7);
        grid.add(new Label("ID Sucursal:"), 0, 8); grid.add(tfSucursal, 1, 8);
        grid.add(new Label("Horario de trabajo:"), 0, 9); grid.add(tfHorarioTrabajo, 1, 9);
        grid.add(new Label("Número de ventanilla:"), 0, 10); grid.add(tfNumeroVentanilla, 1, 10);
        grid.add(new Label("Clientes asignados:"), 0, 11); grid.add(tfClientesAsignados, 1, 11);
        grid.add(new Label("Especialización:"), 0, 12); grid.add(tfEspecializacion, 1, 12);
        grid.add(new Label("Nivel de acceso:"), 0, 13); grid.add(tfNivelAcceso, 1, 13);
        grid.add(new Label("Años de experiencia:"), 0, 14); grid.add(tfAnosExperiencia, 1, 14);

        Stream.of(tfHorarioTrabajo, tfNumeroVentanilla,
                        tfClientesAsignados, tfEspecializacion,
                        tfNivelAcceso, tfAnosExperiencia)
                .forEach(n -> n.setVisible(false));

        cbRol.setOnAction(event -> {
            RolEmpleado rol = cbRol.getValue();

            tfHorarioTrabajo.setVisible(false);
            tfNumeroVentanilla.setVisible(false);
            tfClientesAsignados.setVisible(false);
            tfEspecializacion.setVisible(false);
            tfNivelAcceso.setVisible(false);
            tfAnosExperiencia.setVisible(false);

            switch (rol) {
                case CAJERO -> {
                    tfHorarioTrabajo.setVisible(true);
                    tfNumeroVentanilla.setVisible(true);
                }
                case EJECUTIVO_CUENTA -> {
                    tfClientesAsignados.setVisible(true);
                    tfEspecializacion.setVisible(true);
                }
                case GERENTE -> {
                    tfNivelAcceso.setVisible(true);
                    tfAnosExperiencia.setVisible(true);
                }
                case ADMINISTRADOR -> {

                }
            }
        });

        dialog.getDialogPane().setContent(grid);

        Node botonAgregar = dialog.getDialogPane().lookupButton(agregarButtonType);
        botonAgregar.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            Empleado tempEmpleado = new Empleado();
            tempEmpleado.setNombre(tfNombre.getText());
            tempEmpleado.setDireccion(tfDireccion.getText());
            tempEmpleado.setFechaNacimiento(dpFechaNacimiento.getValue());
            tempEmpleado.setGenero(tfGenero.getText());
            tempEmpleado.setSalario(Double.parseDouble(tfSalario.getText()));
            tempEmpleado.setRol(cbRol.getValue());
            tempEmpleado.setUsuario(tfUsuario.getText());
            tempEmpleado.setPassword(pfPassword.getText());
            tempEmpleado.setIdSucursal(tfSucursal.getText());

            switch (cbRol.getValue()) {
                case CAJERO -> {
                    tempEmpleado.setHorarioTrabajo(tfHorarioTrabajo.getText());
                    tempEmpleado.setNumeroVentanilla(Integer.parseInt(tfNumeroVentanilla.getText()));
                }
                case EJECUTIVO_CUENTA -> {
                    tempEmpleado.setClientesAsignados(Integer.parseInt(tfClientesAsignados.getText()));
                    tempEmpleado.setEspecializacion(tfEspecializacion.getText());
                }
                case GERENTE -> {
                    tempEmpleado.setNivelAcceso(tfNivelAcceso.getText());
                    tempEmpleado.setAnosExperiencia(Integer.parseInt(tfAnosExperiencia.getText()));
                }
                case ADMINISTRADOR -> {

                }
            }

            if (!controller.validarEmpleadosPorRol(tempEmpleado, event)) {
                return;
            }

            if(!controller.validarSucursal(tfSucursal)) {
                return;
            }

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

                switch (cbRol.getValue()) {
                    case CAJERO -> {
                        empleado.setHorarioTrabajo(tfHorarioTrabajo.getText());
                        empleado.setNumeroVentanilla(Integer.parseInt(tfNumeroVentanilla.getText()));
                    }
                    case EJECUTIVO_CUENTA -> {
                        empleado.setClientesAsignados(Integer.parseInt(tfClientesAsignados.getText()));
                        empleado.setEspecializacion(tfEspecializacion.getText());
                    }
                    case GERENTE -> {
                        empleado.setNivelAcceso(tfNivelAcceso.getText());
                        empleado.setAnosExperiencia(Integer.parseInt(tfAnosExperiencia.getText()));
                    }
                    case ADMINISTRADOR -> {

                    }
                }

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

        TextField tfHorarioTrabajo = new TextField(empleado.getHorarioTrabajo());
        TextField tfNumeroVentanilla = new TextField(String.valueOf(empleado.getNumeroVentanilla()));
        TextField tfClientesAsignados = new TextField(String.valueOf(empleado.getClientesAsignados()));
        TextField tfEspecializacion = new TextField(empleado.getEspecializacion());
        TextField tfNivelAcceso = new TextField(empleado.getNivelAcceso());
        TextField tfAnosExperiencia = new TextField(String.valueOf(empleado.getAnosExperiencia()));

        grid.add(new Label("Nombre:"), 0, 0); grid.add(tfNombre, 1, 0);
        grid.add(new Label("Dirección:"), 0, 1); grid.add(tfDireccion, 1, 1);
        grid.add(new Label("Fecha Nacimiento:"), 0, 2); grid.add(dpFechaNacimiento, 1, 2);
        grid.add(new Label("Género:"), 0, 3); grid.add(tfGenero, 1, 3);
        grid.add(new Label("Salario:"), 0, 4); grid.add(tfSalario, 1, 4);
        grid.add(new Label("Rol:"), 0, 5); grid.add(cbRol, 1, 5);
        grid.add(new Label("Usuario:"), 0, 6); grid.add(tfUsuario, 1, 6);
        grid.add(new Label("Contraseña:"), 0, 7); grid.add(pfPassword, 1, 7);
        grid.add(new Label("ID Sucursal:"), 0, 8); grid.add(tfSucursal, 1, 8);
        grid.add(new Label("Horario de trabajo:"), 0, 9); grid.add(tfHorarioTrabajo, 1, 9);
        grid.add(new Label("Número de ventanilla:"), 0, 10); grid.add(tfNumeroVentanilla, 1, 10);
        grid.add(new Label("Clientes asignados:"), 0, 11); grid.add(tfClientesAsignados, 1, 11);
        grid.add(new Label("Especialización:"), 0, 12); grid.add(tfEspecializacion, 1, 12);
        grid.add(new Label("Nivel de acceso:"), 0, 13); grid.add(tfNivelAcceso, 1, 13);
        grid.add(new Label("Años de experiencia:"), 0, 14); grid.add(tfAnosExperiencia, 1, 14);

        Stream.of(tfHorarioTrabajo, tfNumeroVentanilla,
                        tfClientesAsignados, tfEspecializacion,
                        tfNivelAcceso, tfAnosExperiencia)
                .forEach(n -> n.setVisible(false));

        cbRol.setOnAction(event -> {
            RolEmpleado rol = cbRol.getValue();

            tfHorarioTrabajo.setVisible(false);
            tfNumeroVentanilla.setVisible(false);
            tfClientesAsignados.setVisible(false);
            tfEspecializacion.setVisible(false);
            tfNivelAcceso.setVisible(false);
            tfAnosExperiencia.setVisible(false);

            switch (rol) {
                case CAJERO -> {
                    tfHorarioTrabajo.setVisible(true);
                    tfNumeroVentanilla.setVisible(true);
                }
                case EJECUTIVO_CUENTA -> {
                    tfClientesAsignados.setVisible(true);
                    tfEspecializacion.setVisible(true);
                }
                case GERENTE -> {
                    tfNivelAcceso.setVisible(true);
                    tfAnosExperiencia.setVisible(true);
                }
                case ADMINISTRADOR -> {

                }
            }
        });

        cbRol.getOnAction().handle(null);

        dialog.getDialogPane().setContent(grid);

        Node botonGuardar = dialog.getDialogPane().lookupButton(guardarButtonType);
        botonGuardar.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            Empleado tempEmpleado = new Empleado();
            tempEmpleado.setNombre(tfNombre.getText());
            tempEmpleado.setDireccion(tfDireccion.getText());
            tempEmpleado.setFechaNacimiento(dpFechaNacimiento.getValue());
            tempEmpleado.setGenero(tfGenero.getText());
            tempEmpleado.setSalario(Double.parseDouble(tfSalario.getText()));
            tempEmpleado.setRol(cbRol.getValue());
            tempEmpleado.setUsuario(tfUsuario.getText());
            tempEmpleado.setPassword(pfPassword.getText());
            tempEmpleado.setIdSucursal(tfSucursal.getText());

            switch (cbRol.getValue()) {
                case CAJERO -> {
                    tempEmpleado.setHorarioTrabajo(tfHorarioTrabajo.getText());
                    tempEmpleado.setNumeroVentanilla(Integer.parseInt(tfNumeroVentanilla.getText()));
                }
                case EJECUTIVO_CUENTA -> {
                    tempEmpleado.setClientesAsignados(Integer.parseInt(tfClientesAsignados.getText()));
                    tempEmpleado.setEspecializacion(tfEspecializacion.getText());
                }
                case GERENTE -> {
                    tempEmpleado.setNivelAcceso(tfNivelAcceso.getText());
                    tempEmpleado.setAnosExperiencia(Integer.parseInt(tfAnosExperiencia.getText()));
                }
                case ADMINISTRADOR -> {

                }
            }

            if (!controller.validarEmpleadosPorRol(tempEmpleado, event)) {
                return;
            }

            if (!controller.validarSucursal(tfSucursal)) {
                return;
            }

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

                if (!pfPassword.getText().isBlank()) {
                    empleado.setPassword(pfPassword.getText());
                }

                empleado.setIdSucursal(tfSucursal.getText());

                switch (cbRol.getValue()) {
                    case CAJERO -> {
                        empleado.setHorarioTrabajo(tfHorarioTrabajo.getText());
                        empleado.setNumeroVentanilla(Integer.parseInt(tfNumeroVentanilla.getText()));
                    }
                    case EJECUTIVO_CUENTA -> {
                        empleado.setClientesAsignados(Integer.parseInt(tfClientesAsignados.getText()));
                        empleado.setEspecializacion(tfEspecializacion.getText());
                    }
                    case GERENTE -> {
                        empleado.setNivelAcceso(tfNivelAcceso.getText());
                        empleado.setAnosExperiencia(Integer.parseInt(tfAnosExperiencia.getText()));
                    }
                    case ADMINISTRADOR -> {

                    }
                }

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