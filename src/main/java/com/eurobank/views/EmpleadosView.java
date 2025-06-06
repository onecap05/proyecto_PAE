//package com.eurobank.views;
//
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import com.eurobank.models.Empleado;
//import com.eurobank.models.RolEmpleado;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//public class EmpleadosView {
//    private static ObservableList<Empleado> empleados = FXCollections.observableArrayList();
//    private static FilteredList<Empleado> empleadosFiltrados = new FilteredList<>(empleados, emp -> emp.isEstadoActivo());
//
//    static {
//        cargarDatosEjemplo();
//    }
//
//    public static void mostrar() {
//        Stage stage = new Stage();
//        stage.setTitle("Gestión de Empleados");
//
//        TableView<Empleado> tableView = crearTablaEmpleados();
//
//        ComboBox<RolEmpleado> cbFiltroRol = crearComboFiltro();
//
//        HBox controlesInferiores = crearControlesCRUD(tableView);
//
//        VBox controlesSuperiores = new VBox(10,
//                new Label("Filtrar empleados por rol:"),
//                cbFiltroRol);
//        controlesSuperiores.setPadding(new Insets(10));
//
//        BorderPane root = new BorderPane();
//        root.setTop(controlesSuperiores);
//        root.setCenter(tableView);
//        root.setBottom(controlesInferiores);
//
//        Scene scene = new Scene(root, 800, 500);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    private static TableView<Empleado> crearTablaEmpleados() {
//        TableView<Empleado> tableView = new TableView<>();
//        tableView.setItems(empleadosFiltrados);
//
//        TableColumn<Empleado, String> colId = new TableColumn<>("ID");
//        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
//
//        TableColumn<Empleado, String> colNombre = new TableColumn<>("Nombre");
//        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
//
//        TableColumn<Empleado, String> colUsuario = new TableColumn<>("Usuario");
//        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
//
//        TableColumn<Empleado, String> colRol = new TableColumn<>("Rol");
//        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
//
//        TableColumn<Empleado, String> colDetalleRol = new TableColumn<>("Detalle Rol");
//        colDetalleRol.setCellValueFactory(cellData -> {
//            Empleado emp = cellData.getValue();
//            switch(emp.getRol()) {
//                case CAJERO:
//                    return new SimpleStringProperty("Ventanilla: " + emp.getNumeroVentanilla() + " | Horario: " + emp.getHorarioTrabajo());
//                case EJECUTIVO_CUENTA:
//                    return new SimpleStringProperty("Clientes: " + emp.getClientesAsignados() + " | Especialización: " + emp.getEspecializacion());
//                case GERENTE:
//                    return new SimpleStringProperty("Nivel: " + emp.getNivelAcceso() + " | Exp: " + emp.getAnosExperiencia() + " años");
//                default:
//                    return new SimpleStringProperty("");
//            }
//        });
//
//        tableView.getColumns().addAll(colId, colNombre, colUsuario, colRol, colDetalleRol);
//        return tableView;
//    }
//
//    private static ComboBox<RolEmpleado> crearComboFiltro() {
//        ComboBox<RolEmpleado> cbFiltroRol = new ComboBox<>();
//        cbFiltroRol.getItems().addAll(RolEmpleado.values());
//        cbFiltroRol.getItems().add(0, null);
//        cbFiltroRol.setPromptText("Seleccione un rol");
//
//        cbFiltroRol.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                RolEmpleado rolSeleccionado = cbFiltroRol.getValue();
//                empleadosFiltrados.setPredicate(empleado -> {
//                    boolean activo = empleado.isEstadoActivo();
//                    if (!activo) return false;
//                    if (rolSeleccionado == null) return true;
//                    return empleado.getRol() == rolSeleccionado;
//                });
//            }
//        });
//        return cbFiltroRol;
//    }
//
//    private static HBox crearControlesCRUD(TableView<Empleado> tableView) {
//        Button btnAgregar = new Button("Agregar");
//        Button btnEditar = new Button("Editar");
//        Button btnEliminar = new Button("Eliminar");
//
//        btnAgregar.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                EmpleadoDialog.mostrarDialogoAgregar(empleados);
//            }
//        });
//
//        btnEditar.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Empleado seleccionado = tableView.getSelectionModel().getSelectedItem();
//                if (seleccionado != null) {
//                    EmpleadoDialog.mostrarDialogoEditar(seleccionado);
//                }
//            }
//        });
//
//        btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Empleado seleccionado = tableView.getSelectionModel().getSelectedItem();
//                if (seleccionado != null) {
//                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
//                    confirmacion.setTitle("Confirmar eliminación");
//                    confirmacion.setHeaderText("¿Está seguro de desactivar este empleado?");
//                    confirmacion.setContentText("Esta acción no se puede deshacer.");
//
//                    Optional<ButtonType> resultado = confirmacion.showAndWait();
//                    if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
//                        seleccionado.desactivar();
//                        tableView.refresh();
//                    }
//                }
//            }
//        });
//
//        HBox controles = new HBox(10, btnAgregar, btnEditar, btnEliminar);
//        controles.setPadding(new Insets(10));
//        return controles;
//    }
//
//    private static void cargarDatosEjemplo() {
//        empleados.add(new Empleado("E001", "Juan Pérez", "Calle Principal 123", LocalDate.of(1985, 5, 20), "Masculino", 50000.0, RolEmpleado.ADMINISTRADOR, "jperez", "jperez123"));
//
//        Empleado gerente = new Empleado("E002", "María García", "Avenida Central 456", LocalDate.of(1978, 3, 15), "Femenino", 75000.0, RolEmpleado.GERENTE, "mgarciag", "mgarciag123");
//        gerente.setNivelAcceso("regional");
//        gerente.setAnosExperiencia(5);
//        empleados.add(gerente);
//
//        Empleado ejecutivo = new Empleado("E003", "Carlos López", "Calle Secundaria 789", LocalDate.of(1990, 7, 10), "Masculino", 60000.0, RolEmpleado.EJECUTIVO_CUENTA, "clopez", "clopez123");
//        ejecutivo.setEspecializacion("PYMES");
//        ejecutivo.setClientesAsignados(15);
//        empleados.add(ejecutivo);
//
//        Empleado cajero = new Empleado("E004", "Ana Martínez", "Plaza Mayor 321", LocalDate.of(1995, 11, 25), "Femenino", 40000.0, RolEmpleado.CAJERO, "amartinez", "amartinez123");
//        cajero.setHorarioTrabajo("08:00-16:00");
//        cajero.setNumeroVentanilla(3);
//        empleados.add(cajero);
//    }
//}