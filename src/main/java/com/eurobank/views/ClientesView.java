package com.eurobank.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.eurobank.models.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.util.Optional;

public class ClientesView {
    private static ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    public static void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Clientes");

        // Filtrar solo clientes activos
        FilteredList<Cliente> clientesFiltrados = new FilteredList<>(clientes, c -> c.isEstadoActivo());

        // Crear tabla
        TableView<Cliente> tabla = new TableView<>();
        tabla.setItems(clientesFiltrados);

        // Columnas
        TableColumn<Cliente, String> colIdFiscal = new TableColumn<>("RFC/CURP");
        colIdFiscal.setCellValueFactory(new PropertyValueFactory<>("idFiscal"));

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));

        TableColumn<Cliente, String> colNacionalidad = new TableColumn<>("Nacionalidad");
        colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));

        TableColumn<Cliente, LocalDate> colFechaNac = new TableColumn<>("Fecha Nacimiento");
        colFechaNac.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        TableColumn<Cliente, String> colContacto = new TableColumn<>("Contacto");
        colContacto.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        "Tel: " + cellData.getValue().getTelefono() + "\n" +
                                "Email: " + cellData.getValue().getEmail()
                )
        );

        TableColumn<Cliente, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        tabla.getColumns().addAll(colIdFiscal, colNombre, colNacionalidad, colFechaNac, colContacto, colDireccion);

        // Botones CRUD
        Button btnAgregar = new Button("Agregar Cliente");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");

        btnAgregar.setOnAction(e -> ClienteDialog.mostrarDialogoAgregar(clientes));
        btnEditar.setOnAction(e -> {
            Cliente seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                ClienteDialog.mostrarDialogoEditar(seleccionado);
            }
        });
        // Confirmación y desactivación
        btnEliminar.setOnAction(e -> {
            Cliente seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar eliminación");
                confirmacion.setHeaderText("¿Está seguro de desactivar este cliente?");
                confirmacion.setContentText("El cliente ya no será visible pero sus cuentas permanecerán.");

                Optional<ButtonType> resultado = confirmacion.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    seleccionado.desactivar();
                    tabla.refresh();
                }
            }
        });

        HBox botones = new HBox(10, btnAgregar, btnEditar, btnEliminar);

        BorderPane root = new BorderPane();
        root.setCenter(tabla);
        root.setBottom(botones);

        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.show();
    }
}