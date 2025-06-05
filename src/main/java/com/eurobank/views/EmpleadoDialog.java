package com.eurobank.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class EmpleadoDialog {
    public static void mostrarDialogoAgregar(ObservableList<Empleado> listaEmpleados) {
        Stage stage = new Stage();
        stage.setTitle("Agregar Empleado");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Controles del formulario
        TextField tfId = new TextField();
        TextField tfNombre = new TextField();
        ComboBox<RolEmpleado> cbRol = new ComboBox<>();
        cbRol.getItems().addAll(RolEmpleado.values());

        TextField tfUsuario = new TextField();
        PasswordField pfPassword = new PasswordField();

        // Campos específicos por rol
        TextField tfHorario = new TextField();
        TextField tfVentanilla = new TextField();
        TextField tfClientes = new TextField();
        TextField tfEspecializacion = new TextField();
        TextField tfNivelAcceso = new TextField();
        TextField tfAnosExp = new TextField();

        // Organización en grid
        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfId, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(tfNombre, 1, 1);
        grid.add(new Label("Rol:"), 0, 2);
        grid.add(cbRol, 1, 2);
        grid.add(new Label("Usuario:"), 0, 3);
        grid.add(tfUsuario, 1, 3);
        grid.add(new Label("Contraseña:"), 0, 4);
        grid.add(pfPassword, 1, 4);

        // Mostrar campos según rol seleccionado
        cbRol.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Limpiar campos específicos
                grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) >= 5);

                RolEmpleado rol = cbRol.getValue();
                if (rol == null) return;

                int row = 5;
                switch(rol) {
                    case CAJERO:
                        grid.add(new Label("Horario Trabajo:"), 0, row);
                        grid.add(tfHorario, 1, row++);
                        grid.add(new Label("N° Ventanilla:"), 0, row);
                        grid.add(tfVentanilla, 1, row++);
                        break;
                    case EJECUTIVO_CUENTA:
                        grid.add(new Label("Clientes Asignados:"), 0, row);
                        grid.add(tfClientes, 1, row++);
                        grid.add(new Label("Especialización:"), 0, row);
                        grid.add(tfEspecializacion, 1, row++);
                        break;
                    case GERENTE:
                        grid.add(new Label("Nivel Acceso:"), 0, row);
                        grid.add(tfNivelAcceso, 1, row++);
                        grid.add(new Label("Años Experiencia:"), 0, row);
                        grid.add(tfAnosExp, 1, row++);
                        break;
                }
            }
        });

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");

        grid.add(btnGuardar, 0, 20);
        grid.add(btnCancelar, 1, 20);

        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        btnGuardar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Empleado nuevo = new Empleado(
                            tfId.getText(),
                            tfNombre.getText(),
                            cbRol.getValue(),
                            tfUsuario.getText(),
                            pfPassword.getText()
                    );

                    // Setear campos específicos
                    switch(nuevo.getRol()) {
                        case CAJERO:
                            nuevo.setHorarioTrabajo(tfHorario.getText());
                            nuevo.setNumeroVentanilla(Integer.parseInt(tfVentanilla.getText()));
                            break;
                        case EJECUTIVO_CUENTA:
                            nuevo.setClientesAsignados(Integer.parseInt(tfClientes.getText()));
                            nuevo.setEspecializacion(tfEspecializacion.getText());
                            break;
                        case GERENTE:
                            nuevo.setNivelAcceso(tfNivelAcceso.getText());
                            nuevo.setAnosExperiencia(Integer.parseInt(tfAnosExp.getText()));
                            break;
                    }

                    listaEmpleados.add(nuevo);
                    stage.close();
                } catch (NumberFormatException e) {
                    mostrarError("Error en formato numérico");
                } catch (Exception e) {
                    mostrarError("Error al guardar: " + e.getMessage());
                }
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    public static void mostrarDialogoEditar(Empleado empleado) {
        Stage stage = new Stage();
        stage.setTitle("Editar Empleado");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Controles del formulario (precargados con datos del empleado)
        TextField tfId = new TextField(empleado.getId());
        tfId.setDisable(true);
        TextField tfNombre = new TextField(empleado.getNombre());
        ComboBox<RolEmpleado> cbRol = new ComboBox<>();
        cbRol.getItems().addAll(RolEmpleado.values());
        cbRol.setValue(empleado.getRol());

        TextField tfUsuario = new TextField(empleado.getUsuario());
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Dejar vacío para no cambiar");

        // Campos específicos por rol
        TextField tfHorario = new TextField();
        TextField tfVentanilla = new TextField();
        TextField tfClientes = new TextField();
        TextField tfEspecializacion = new TextField();
        TextField tfNivelAcceso = new TextField();
        TextField tfAnosExp = new TextField();

        // Precargar campos específicos
        switch(empleado.getRol()) {
            case CAJERO:
                tfHorario.setText(empleado.getHorarioTrabajo());
                tfVentanilla.setText(String.valueOf(empleado.getNumeroVentanilla()));
                break;
            case EJECUTIVO_CUENTA:
                tfClientes.setText(String.valueOf(empleado.getClientesAsignados()));
                tfEspecializacion.setText(empleado.getEspecializacion());
                break;
            case GERENTE:
                tfNivelAcceso.setText(empleado.getNivelAcceso());
                tfAnosExp.setText(String.valueOf(empleado.getAnosExperiencia()));
                break;
        }

        // Resto de la implementación similar a mostrarDialogoAgregar...
        // (se omite por brevedad, pero sería igual solo que actualizando el empleado existente)
    }

    private static void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}