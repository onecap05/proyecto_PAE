//package com.eurobank.views;
//
//import javafx.beans.property.SimpleStringProperty;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import com.eurobank.models.*;
//
//public class TransaccionesView {
//    public static TableView<Transaccion> crearTablaTransacciones() {
//        TableView<Transaccion> tabla = new TableView<>();
//
//        // Columnas comunes
//        TableColumn<Transaccion, String> colId = new TableColumn<>("ID");
//        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
//
//        TableColumn<Transaccion, String> colTipo = new TableColumn<>("Tipo");
//        colTipo.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getTipo())
//        );
//
//        TableColumn<Transaccion, Double> colMonto = new TableColumn<>("Monto");
//        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
//
//        TableColumn<Transaccion, String> colFecha = new TableColumn<>("Fecha");
//        colFecha.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getFecha().toString())
//        );
//
//        // Columna para detalles específicos
//        TableColumn<Transaccion, String> colDetalle = new TableColumn<>("Detalle");
//        colDetalle.setCellValueFactory(cellData -> {
//            Transaccion t = cellData.getValue();
//            if (t instanceof TransaccionDeposito) {
//                return new SimpleStringProperty(
//                        "Depósito en cuenta: " + ((TransaccionDeposito)t).getNumeroCuenta()
//                );
//            } else if (t instanceof TransaccionRetiro) {
//                return new SimpleStringProperty(
//                        "Retiro de cuenta: " + ((TransaccionRetiro)t).getNumeroCuenta()
//                );
//            } else if (t instanceof TransaccionTransferencia) {
//                return new SimpleStringProperty(
//                        "De " + ((TransaccionTransferencia)t).getNumeroCuentaOrigen() +
//                                " a " + ((TransaccionTransferencia)t).getNumeroCuentaDestino()
//                );
//            }
//            return new SimpleStringProperty("");
//        });
//
//        tabla.getColumns().addAll(colId, colTipo, colMonto, colFecha, colDetalle);
//
//        return tabla;
//    }
//}