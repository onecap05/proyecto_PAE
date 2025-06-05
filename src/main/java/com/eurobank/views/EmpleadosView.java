package com.eurobank.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class EmpleadosView {
    private static ObservableList<Empleado> empleados = FXCollections.observableArrayList();
    private static FilteredList<Empleado> empleadosFiltrados;

    static {
        // Datos de ejemplo
        empleados.add(new Empleado("E001", "Admin Principal", RolEmpleado.ADMINISTRADOR, "admin", "admin123"));

        Empleado gerente = new Empleado("E002", "María García", RolEmpleado.GERENTE, "mgarciag", "mgarciag123");
        gerente.setNivelAcceso("regional");
        gerente.setAnosExperiencia(5);
        empleados.add(gerente);

        Empleado ejecutivo = new Empleado("E003", "Carlos López", RolEmpleado.EJECUTIVO_CUENTA, "clopez", "clopez123");
        ejecutivo.setEspecializacion("PYMES");
        ejecutivo.setClientesAsignados(15);
        empleados.add(ejecutivo);

        Empleado cajero = new Empleado("E004", "Ana Martínez", RolEmpleado.CAJERO, "amartinez", "amartinez123");
        cajero.setHorarioTrabajo("08:00-16:00");
        cajero.setNumeroVentanilla(3);
        empleados.add(cajero);

        empleadosFiltrados = new FilteredList<>(empleados);
    }

    public static void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Empleados");

        // Tabla principal
        TableView<Empleado> tableView = new TableView<>();
        tableView.setItems(empleadosFiltrados);

        // Columnas básicas
        TableColumn<Empleado, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Empleado, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Empleado, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Empleado, String> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        TableColumn<Empleado, String> colDetalleRol = new TableColumn<>("Detalle Rol");
        colDetalleRol.setCellValueFactory(cellData -> {
            Empleado emp = cellData.getValue();
            switch(emp.getRol()) {
                case CAJERO:
                    return new SimpleStringProperty("Ventanilla: " + emp.getNumeroVentanilla() + " | Horario: " + emp.getHorarioTrabajo());
                case EJECUTIVO_CUENTA:
                    return new SimpleStringProperty("Clientes: " + emp.getClientesAsignados() + " | Especialización: " + emp.getEspecializacion());
                case GERENTE:
                    return new SimpleStringProperty("Nivel: " + emp.getNivelAcceso() + " | Exp: " + emp.getAnosExperiencia() + " años");
                default:
                    return new SimpleStringProperty("");
            }
        });

        tableView.getColumns().addAll(colId, colNombre, colUsuario, colRol, colDetalleRol);

        // Controles de filtrado
        ComboBox<RolEmpleado> cbFiltroRol = new ComboBox<>();
        cbFiltroRol.getItems().addAll(RolEmpleado.values());
        cbFiltroRol.getItems().add(0, null); // Opción para mostrar todos
        cbFiltroRol.setPromptText("Filtrar por rol");

        cbFiltroRol.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RolEmpleado rolSeleccionado = cbFiltroRol.getValue();
                empleadosFiltrados.setPredicate(empleado -> {
                    if (rolSeleccionado == null) return true;
                    return empleado.getRol() == rolSeleccionado;
                });
            }
        });

        // Botones CRUD
        Button btnAgregar = new Button("Agregar");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");

        // Contenedor de controles
        HBox controlesSuperiores = new HBox(10, new Label("Filtrar por rol:"), cbFiltroRol);
        HBox controlesInferiores = new HBox(10, btnAgregar, btnEditar, btnEliminar);
        controlesSuperiores.setPadding(new Insets(10));
        controlesInferiores.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(controlesSuperiores);
        root.setCenter(tableView);
        root.setBottom(controlesInferiores);

        // Manejadores de eventos
        btnAgregar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DialogoEmpleado.mostrarDialogoAgregar(empleados);
            }
        });

        btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Empleado seleccionado = tableView.getSelectionModel().getSelectedItem();
                if (seleccionado != null) {
                    DialogoEmpleado.mostrarDialogoEditar(seleccionado);
                }
            }
        });

        btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Empleado seleccionado = tableView.getSelectionModel().getSelectedItem();
                if (seleccionado != null) {
                    empleados.remove(seleccionado);
                }
            }
        });

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static class DialogoEmpleado {
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
                public void handle(ActionEvent event