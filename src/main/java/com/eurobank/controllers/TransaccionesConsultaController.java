package com.eurobank.controllers;

import com.eurobank.models.DAO.TransaccionDAO;
import com.eurobank.models.Transaccion;
import com.eurobank.utils.VentanasEmergentes;
import com.eurobank.views.TransaccionesConsultaView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;


public class TransaccionesConsultaController {

    VentanasEmergentes ventanasEmergentes = new VentanasEmergentes();

    private final TransaccionesConsultaView view;
    private final TransaccionDAO transaccionDAO;

    public TransaccionesConsultaController(TransaccionesConsultaView view) {
        this.view = view;
        this.transaccionDAO = new TransaccionDAO();
    }

    public void inicializar() {
        try {
            List<Transaccion> transacciones = transaccionDAO.cargarTransacciones();
            view.getTabla().setItems(FXCollections.observableArrayList(transacciones));
        } catch (IOException e) {
            e.printStackTrace();
        }

        view.getBtnGenerarDocumento().setOnAction(e -> generarExcelDesdeTabla());

    }

    public void aplicarFiltros() {
        String id = view.getIdField().getText().trim();
        String filtroFecha = view.getFiltroFechaCombo().getValue();
        LocalDateTime ahora = LocalDateTime.now();

        try {
            List<Transaccion> todas = transaccionDAO.cargarTransacciones();

            List<Transaccion> filtradas = todas.stream()
                    .filter(t -> id.isEmpty() || t.getId().equalsIgnoreCase(id))
                    .filter(t -> {
                        LocalDateTime fecha = t.getFecha();
                        if (fecha == null) return false; // Skip transactions with null dates
                        if (filtroFecha == null || filtroFecha.isEmpty()) return true; // No filter applied
                        return switch (filtroFecha) {
                            case "Hoy" -> fecha.toLocalDate().isEqual(LocalDate.now());
                            case "Esta semana" -> {
                                LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                                LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
                                yield !fecha.toLocalDate().isBefore(startOfWeek) && !fecha.toLocalDate().isAfter(endOfWeek);
                            }
                            case "Este mes" -> fecha.getMonth() == ahora.getMonth() && fecha.getYear() == ahora.getYear();
                            case "Este año" -> fecha.getYear() == ahora.getYear();
                            default -> true;
                        };
                    })
                    .collect(Collectors.toList());

            view.getTabla().setItems(FXCollections.observableArrayList(filtradas));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generarExcelDesdeTabla() {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Transacciones");

            // Cabecera
            org.apache.poi.ss.usermodel.Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Tipo");
            header.createCell(2).setCellValue("Monto");
            header.createCell(3).setCellValue("Fecha");
            header.createCell(4).setCellValue("Sucursal");

            // Datos
            ObservableList<Transaccion> transacciones = view.getTabla().getItems();
            for (int i = 0; i < transacciones.size(); i++) {
                Transaccion t = transacciones.get(i);
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(t.getId());
                row.createCell(1).setCellValue(t.getTipo());
                row.createCell(2).setCellValue(t.getMonto());
                row.createCell(3).setCellValue(t.getFecha().toString());
                row.createCell(4).setCellValue(t.getIdSucursal());
            }

            // Ajustar ancho de columnas
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar el archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar documento Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"));
            java.io.File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try (java.io.FileOutputStream fileOut = new java.io.FileOutputStream(file)) {
                    workbook.write(fileOut);
                    ventanasEmergentes.mostrarAlerta("Éxito", "Documento Excel generado correctamente", "se genero con exito");
                }
            }

        } catch (Exception e) {
            ventanasEmergentes.mostrarAlerta("Error", "No se pudo generar el documento Excel", "Error al generar el documento Excel");
            e.printStackTrace();

        }
    }
}
