//package com.eurobank.controllers;
//
//import com.eurobank.models.*;
//import com.eurobank.models.DAO.TransaccionDAO;
//import com.eurobank.models.excepciones.SaldoInsuficienteException;
//import com.eurobank.models.excepciones.TransaccionFallidaException;
//import com.eurobank.utils.TransaccionFactory;
//import com.eurobank.views.TransaccionesView;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.Alert;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class TransaccionesController {
//
//    private TransaccionesView view;
//    private TransaccionDAO transaccionDAO;
//
//    public TransaccionesController(TransaccionesView view) {
//        this.view = view;
//        this.transaccionDAO = new TransaccionDAO();
//        this.view.setController(this);
//    }
//
//    public void agregarTransaccion() {
//        try {
//            String tipo = view.getTipoTransaccion();
//            double monto = view.getMonto();
//            String sucursal = view.getSucursal();
//            LocalDateTime fecha = view.getFecha();
//
//            Transaccion nuevaTransaccion;
//            switch (tipo) {
//                case "DEPOSITO":
//                case "RETIRO":
//                    String cuenta = view.getCuentaOrigen();
//                    nuevaTransaccion = TransaccionFactory.crearTransaccion(
//                            tipo, null, monto, fecha, sucursal, cuenta);
//                    break;
//                case "TRANSFERENCIA":
//                    String cuentaOrigen = view.getCuentaOrigen();
//                    String cuentaDestino = view.getCuentaDestino();
//                    if (cuentaDestino == null || cuentaDestino.trim().isEmpty()) {
//                        throw new TransaccionFallidaException("La cuenta destino es requerida para transferencias");
//                    }
//                    nuevaTransaccion = TransaccionFactory.crearTransaccion(
//                            tipo, null, monto, fecha, sucursal, cuentaOrigen, cuentaDestino);
//                    break;
//                default:
//                    throw new TransaccionFallidaException("Tipo de transacción no válido");
//            }
//
//            transaccionDAO.crearNuevaTransaccion(nuevaTransaccion);
//            view.cargarTransacciones();
//            view.limpiarFormulario();
//            view.mostrarAlerta("Éxito", "Transacción realizada",
//                    "La transacción se ha registrado correctamente", Alert.AlertType.INFORMATION);
//        } catch (SaldoInsuficienteException e) {
//            view.mostrarAlerta("Error", "Saldo insuficiente", e.getMessage(), Alert.AlertType.ERROR);
//        } catch (TransaccionFallidaException e) {
//            view.mostrarAlerta("Error", "Transacción fallida", e.getMessage(), Alert.AlertType.ERROR);
//        } catch (IOException e) {
//            view.mostrarAlerta("Error", "Error de sistema",
//                    "No se pudo guardar la transacción: " + e.getMessage(), Alert.AlertType.ERROR);
//        }
//    }
//
//    public void exportarTransacciones() {
//        try {
//            List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
//            FileWriter writer = new FileWriter("transacciones_exportadas.csv");
//
//            // Escribir encabezados
//            writer.write("ID,Tipo,Monto,Fecha,Sucursal,Cuenta Origen,Cuenta Destino\n");
//
//            // Escribir datos
//            for (Transaccion t : transacciones) {
//                writer.write(String.format("\"%s\",\"%s\",%.2f,\"%s\",\"%s\"",
//                        t.getId(), t.getTipo(), t.getMonto(), t.getFecha(), t.getIdSucursal()));
//
//                if (t instanceof TransaccionDeposito || t instanceof TransaccionRetiro) {
//                    String cuenta = (t instanceof TransaccionDeposito) ?
//                            ((TransaccionDeposito) t).getNumeroCuenta() :
//                            ((TransaccionRetiro) t).getNumeroCuenta();
//                    writer.write(String.format(",\"%s\",\"\"\n", cuenta));
//                } else if (t instanceof TransaccionTransferencia) {
//                    TransaccionTransferencia tt = (TransaccionTransferencia) t;
//                    writer.write(String.format(",\"%s\",\"%s\"\n",
//                            tt.getNumeroCuentaOrigen(), tt.getNumeroCuentaDestino()));
//                } else {
//                    writer.write(",\"\",\"\"\n");
//                }
//            }
//
//            writer.close();
//            view.mostrarAlerta("Éxito", "Exportación completada",
//                    "Las transacciones se han exportado a transacciones_exportadas.csv", Alert.AlertType.INFORMATION);
//        } catch (IOException e) {
//            view.mostrarAlerta("Error", "Error al exportar",
//                    "No se pudo exportar las transacciones: " + e.getMessage(), Alert.AlertType.ERROR);
//        }
//    }
//
//    public void filtrarPorSucursal(String idSucursal) {
//        try {
//            List<Transaccion> transacciones = transaccionDAO.filtrarPorSucursal(idSucursal);
//            ObservableList<Transaccion> datos = FXCollections.observableArrayList(transacciones);
//            view.getTablaTransacciones().setItems(datos);
//        } catch (IOException e) {
//            view.mostrarAlerta("Error", "Error al filtrar",
//                    "No se pudieron cargar las transacciones: " + e.getMessage(), Alert.AlertType.ERROR);
//        }
//    }
//
//    public void filtrarPorTipo(String tipo) {
//        try {
//            List<Transaccion> transacciones = transaccionDAO.filtrarPorTipo(tipo);
//            ObservableList<Transaccion> datos = FXCollections.observableArrayList(transacciones);
//            view.getTablaTransacciones().setItems(datos);
//        } catch (IOException e) {
//            view.mostrarAlerta("Error", "Error al filtrar",
//                    "No se pudieron cargar las transacciones: " + e.getMessage(), Alert.AlertType.ERROR);
//        }
//    }
//}