package com.eurobank.views;

import com.eurobank.controllers.EmpleadosController;
import com.eurobank.models.Empleado;
import com.eurobank.models.RolEmpleado;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmpleadosView {

    private Stage stage;
    private TableView<Empleado> tableView;
    private ComboBox<RolEmpleado> cbFiltroRol;
    private Button btnAgregar;
    private Button btnEditar;
    private Button btnEliminar;
    private EmpleadosController controller;

    public EmpleadosView(EmpleadosController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        stage = new Stage();
        stage.setTitle("Gestión de Empleados");

        tableView = new TableView<>();
        crearColumnasTabla();

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setFixedCellSize(40);
        tableView.setRowFactory(tv -> {
            TableRow<Empleado> row = new TableRow<>();
            row.setStyle("-fx-cell-size: 40px;");
            return row;
        });

        cbFiltroRol = new ComboBox<>();
        cbFiltroRol.getItems().addAll(RolEmpleado.values());
        cbFiltroRol.getItems().add(0, null);
        cbFiltroRol.setPromptText("Todos los roles");

        btnAgregar = new Button("Agregar");
        btnEditar = new Button("Editar");
        btnEliminar = new Button("Eliminar");

        VBox controlesSuperiores = new VBox(10,
                new Label("Filtrar por rol:"),
                cbFiltroRol);
        controlesSuperiores.setPadding(new Insets(10));

        HBox controlesInferiores = new HBox(10, btnAgregar, btnEditar, btnEliminar);
        controlesInferiores.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(controlesSuperiores);
        root.setCenter(tableView);
        root.setBottom(controlesInferiores);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
    }

    private void crearColumnasTabla() {
        TableColumn<Empleado, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Empleado, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Empleado, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<Empleado, String> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        TableColumn<Empleado, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Empleado, Double> colSalario = new TableColumn<>("Salario");
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));

        TableColumn<Empleado, String> colSucursal = new TableColumn<>("Sucursal");
        colSucursal.setCellValueFactory(new PropertyValueFactory<>("idSucursal"));

        tableView.getColumns().addAll(
                colId, colNombre, colDireccion, colRol, colUsuario, colSalario, colSucursal
        );
    }

    public void mostrar() {
        stage.show();
    }

    public TableView<Empleado> getTableView() {
        return tableView;
    }

    public ComboBox<RolEmpleado> getCbFiltroRol() {
        return cbFiltroRol;
    }

    public Button getBtnAgregar() {
        return btnAgregar;
    }

    public Button getBtnEditar() {
        return btnEditar;
    }

    public Button getBtnEliminar() {
        return btnEliminar;
    }

    public void setEmpleadosEnTabla(ObservableList<Empleado> empleados) {
        tableView.setItems(empleados);
    }
}
