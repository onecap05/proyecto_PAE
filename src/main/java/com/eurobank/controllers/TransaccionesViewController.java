package com.eurobank.controllers;

import com.eurobank.models.*;
import com.eurobank.models.DAO.CuentaDAO;
import com.eurobank.models.DAO.TransaccionDAO;
import com.eurobank.utils.Validaciones;
import com.eurobank.utils.VentanasEmergentes;
import com.eurobank.views.CuentasView;
import com.eurobank.views.TransaccionesView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TransaccionesViewController {

    private TransaccionesView view;
    private Stage primaryStage;
    String idCuentaSeleccionada = CuentasController.idCuentaSeleccionada;
    String idSucursal = LoginController.idSucursal;
    VentanasEmergentes ventanasEmergentes = new VentanasEmergentes();
    private boolean esEmpresarial = false;

    public TransaccionesViewController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.view = new TransaccionesView();
    }

    public void mostrarTransaccionesView() {
        inicializar();
        verificarTipoTransaccion();
        Scene scene = new Scene(view.getView(), 600, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Transacciones");
        primaryStage.show();
    }

    public void inicializar() {
        // Verificar si es cuenta empresarial
        try {
            Cuenta cuenta = new CuentaDAO().buscarCuentaPorNumero(idCuentaSeleccionada);
            esEmpresarial = cuenta != null && cuenta.getTipo() == TipoCuenta.EMPRESARIAL;
            if (esEmpresarial) {
                view.mostrarOpcionCredito(true);
            }
        } catch (IOException e) {
            System.err.println("Error al verificar tipo de cuenta: " + e.getMessage());
        }

        if (idCuentaSeleccionada != null && !idCuentaSeleccionada.isEmpty()) {
            view.setLbCuentaOrigenObtenida(idCuentaSeleccionada);
        } else {
            System.err.println("El idCuentaSeleccionada es nulo o está vacío.");
        }

        if (idSucursal != null && !idSucursal.isEmpty()) {
            view.setLbSucursalObtenida(idSucursal);
        } else {
            System.err.println("El idSucursal es nulo o está vacío.");
        }

        view.getCancelarButton().setOnAction(event -> cancelarTransaccion());
    }



    public void verificarTipoTransaccion() {
        // Add a listener to the ComboBox to enable/disable the "Cuenta destino" field dynamically
        view.getTipoCombo().setOnAction(event -> {
            String tipoSeleccionado = view.getTipoCombo().getValue();

            if ("Transferencia".equals(tipoSeleccionado)) {
                view.getCuentaDestinoField().setDisable(false); // Enable the field
            } else {
                view.getCuentaDestinoField().setDisable(true); // Disable the field
            }
        });


        view.getAceptarButton().setOnAction(event -> {
            String tipoSeleccionado = view.getTipoCombo().getValue();

            if (tipoSeleccionado == null || tipoSeleccionado.isEmpty()) {
                ventanasEmergentes.mostrarAlerta("Error", "Tipo de transacción no seleccionado",
                        "Por favor, seleccione un tipo de transacción.");
                return;
            }

            switch (tipoSeleccionado) {
                case "Transferencia":

                    if (view.getCuentaDestinoField().getText().isEmpty()) {
                        ventanasEmergentes.mostrarAlerta("Error", "Cuenta destino vacía", "Por favor, ingrese una cuenta destino.");
                        return;
                    }
                    realizarTransferencia();

                    break;

                case "Depósito":

                    realizarDeposito();
                    break;

                case "Retiro":

                    realizarRetiro();

                    break;

                default:

                    ventanasEmergentes.mostrarAlerta("Error", "Tipo de transacción no válido",
                            "Por favor, seleccione un tipo de transacción válido.");
                    break;
            }
        });
    }

    private void realizarTransferencia() {
        Validaciones validaciones = new Validaciones();
        boolean usarCredito = esEmpresarial && view.getRbCredito().isSelected();

        double monto = 0;
        double saldoCuenta = obtenerSaldoCuenta();
        double limiteCredito = obtenerLimiteCredito();

        if (view.getMontoField().getText().isEmpty()) {
            ventanasEmergentes.mostrarAlerta("Error", "Monto vacío", "Por favor, ingrese un monto válido.");
            return;
        }

        if (!validaciones.validarMontoPositivo(view.getMontoField().getText())) {
            ventanasEmergentes.mostrarAlerta("Error", "Monto inválido", "Por favor, ingrese un monto válido.");
            return;
        }

        if (view.getCuentaDestinoField().getText().isEmpty()) {
            ventanasEmergentes.mostrarAlerta("Error", "Cuenta destino vacía", "Por favor, ingrese una cuenta destino.");
            return;
        }

        if (!validarCuentaDestino(view.getCuentaDestinoField().getText())) {
            ventanasEmergentes.mostrarAlerta("Error", "Cuenta destino inválida",
                    "La cuenta destino ingresada no es válida o no existe.");
            return;
        }

        monto = Double.parseDouble(view.getMontoField().getText());

        if (usarCredito) {
            if (monto > limiteCredito) {
                ventanasEmergentes.mostrarAlerta("Error", "Límite de crédito excedido",
                        "No puede transferir más de lo que tiene disponible en su línea de crédito.");
                return;
            }
        } else {
            if (monto > saldoCuenta) {
                ventanasEmergentes.mostrarAlerta("Error", "Saldo insuficiente",
                        "No puede transferir más de lo que tiene en su cuenta.");
                return;
            }
        }

        try {
            if (usarCredito) {
                actualizarLimiteCredito(idCuentaSeleccionada, -monto);
            } else {
                actualizarSaldo(saldoCuenta - monto);
            }
            actualizarCuentaDestinoField(view.getCuentaDestinoField().getText(), monto);
            registrarTransferencia();
        } catch (Exception e) {
            ventanasEmergentes.mostrarAlerta("Error", "Error en transacción",
                    "Ocurrió un error al procesar la transacción: " + e.getMessage());
        }
    }

    private double obtenerLimiteCredito() {
        try {
            Cuenta cuenta = new CuentaDAO().buscarCuentaPorNumero(idCuentaSeleccionada);
            return cuenta != null ? cuenta.getLimiteCredito() : 0;
        } catch (IOException e) {
            System.err.println("Error al obtener límite de crédito: " + e.getMessage());
            return 0;
        }
    }

    private void actualizarLimiteCredito(String numeroCuenta, double monto) throws IOException {
        CuentaDAO cuentaDAO = new CuentaDAO();
        Cuenta cuenta = cuentaDAO.buscarCuentaPorNumero(numeroCuenta);
        if (cuenta != null) {
            cuenta.setLimiteCredito(cuenta.getLimiteCredito() + monto);
            cuentaDAO.actualizarCuenta(numeroCuenta, cuenta);
        }
    }

    public void actualizarCuentaDestinoField(String cuentaDestino, double monto) {

        double saldoCuenta = 0;

        try {
            CuentaDAO cuentaDAO = new CuentaDAO();
            Cuenta cuenta = cuentaDAO.buscarCuentaPorNumero(cuentaDestino);

            saldoCuenta = cuenta.getSaldo();
            saldoCuenta += monto;
            cuenta.setSaldo(saldoCuenta);

            if (cuenta != null) {
                cuentaDAO.actualizarCuenta(cuentaDestino, cuenta);
            } else {
                ventanasEmergentes.mostrarAlerta("Error", "Cuenta destino no encontrada",
                        "La cuenta destino ingresada no existe.");
            }
        } catch (IOException e) {
            ventanasEmergentes.mostrarAlerta("Error", "Error al actualizar cuenta destino",
                    "No se pudo actualizar la cuenta destino: " + e.getMessage());
        }
    }

    public boolean validarCuentaDestino(String cuentaDestino) {

        boolean cuentaValida = false;
        try {
            CuentaDAO cuentaDAO = new CuentaDAO();
            Cuenta cuenta = cuentaDAO.buscarCuentaPorNumero(cuentaDestino);
            if (cuenta != null) {
                cuentaValida = true;
            } else {
                ventanasEmergentes.mostrarAlerta("Error", "Cuenta destino no encontrada",
                        "La cuenta destino ingresada no existe.");
            }
        } catch (IOException e) {
            ventanasEmergentes.mostrarAlerta("Error", "Error al validar cuenta destino",
                    "No se pudo validar la cuenta destino: " + e.getMessage());
        }

        return cuentaValida;
    }

    private void realizarDeposito() {

        Validaciones validaciones = new Validaciones();

        int monto = 0;
        double saldoCuenta = obtenerSaldoCuenta();

        if (view.getMontoField().getText().isEmpty()) {
            ventanasEmergentes.mostrarAlerta("Error", "Monto vacío", "Por favor, ingrese un monto válido.");
            return;
        }

        if (!validaciones.validarMontoPositivo(view.getMontoField().getText())) {
            ventanasEmergentes.mostrarAlerta("Error", "Monto inválido", "Por favor, ingrese un monto válido.");
            return;
        }



        monto = Integer.parseInt(view.getMontoField().getText());
        saldoCuenta += monto;

        actualizarSaldo(saldoCuenta);
        registrarDeposito();

    }

    private void realizarRetiro() {

        Validaciones validaciones = new Validaciones();

        double monto = 0;
        double saldoCuenta = obtenerSaldoCuenta();

        if (view.getMontoField().getText().isEmpty()) {
            ventanasEmergentes.mostrarAlerta("Error", "Monto vacío", "Por favor, ingrese un monto válido.");
            return;
        }

        if (!validaciones.validarMontoPositivo(view.getMontoField().getText())) {
            ventanasEmergentes.mostrarAlerta("Error", "Monto inválido", "Por favor, ingrese un monto válido.");
            return;
        }

        monto = Double.parseDouble(view.getMontoField().getText());

        if (monto > saldoCuenta) {
            ventanasEmergentes.mostrarAlerta("Error", "Saldo insuficiente",
                    "No puede retirar más de lo que tiene en su cuenta.");
            return;
        }



        saldoCuenta -= monto;
        actualizarSaldo(saldoCuenta);
        registrarRetiro();

    }

    private double obtenerSaldoCuenta() {

        double saldo = 0;

        try {

            CuentaDAO cuentaDAO = new CuentaDAO();
            Cuenta cuenta = new Cuenta();
            cuenta = cuentaDAO.buscarCuentaPorNumero(idCuentaSeleccionada);

            if (cuenta != null) {
                saldo = cuenta.getSaldo();
            } else {
                ventanasEmergentes.mostrarAlerta("Error", "Cuenta no encontrada",
                        "No se pudo encontrar la cuenta con el ID: " + idCuentaSeleccionada);
            }


        } catch (IOException e) {

            ventanasEmergentes.mostrarAlerta("Error", "Error al obtener datos de la cuenta",
                    "No se pudieron obtener los datos de la cuenta: " + e.getMessage());
        }

        return saldo;
    }

    public void actualizarSaldo (double saldo) {

        try {
            CuentaDAO cuentaDAO = new CuentaDAO();
            Cuenta cuenta = cuentaDAO.buscarCuentaPorNumero(idCuentaSeleccionada);

            if (cuenta != null) {
                cuenta.setSaldo(saldo);
                cuentaDAO.actualizarCuenta(idCuentaSeleccionada, cuenta);


            } else {
                ventanasEmergentes.mostrarAlerta("Error", "Cuenta no encontrada",
                        "No se pudo encontrar la cuenta con el ID: " + idCuentaSeleccionada);
            }
        } catch (IOException e) {
            ventanasEmergentes.mostrarAlerta("Error", "Error al actualizar saldo",
                    "No se pudo actualizar el saldo de la cuenta: " + e.getMessage());
        }
    }

    private void registrarRetiro() {
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        TransaccionRetiro retiro = new TransaccionRetiro();

        retiro.setMonto(Double.parseDouble(view.getMontoField().getText()));
        retiro.setIdSucursal(idSucursal);
        retiro.setNumeroCuenta(idCuentaSeleccionada);
        retiro.setFecha(LocalDateTime.now());
        retiro.setTipo("RETIRO");

        try {
            transaccionDAO.crearNuevaTransaccion(retiro);
            String opcion = VentanasEmergentes.mostrarAlertaConOpciones(
                    "El retiro se ha realizado con exito",
                    "El retiro fue exitoso",
                    "desea generar un comprobante?"
            );

            if ("Aceptar".equals(opcion)) {

                cancelarTransaccion();

            } else if ("Generar Comprobante".equals(opcion)) {

                generarComprobantePDF(retiro, primaryStage);

            }

        } catch (IOException e) {
            ventanasEmergentes.mostrarAlerta("Error", "Error al realizar retiro",
                    "No se pudo realizar el retiro: " + e.getMessage());
        }
    }

    private void registrarDeposito() {
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        TransaccionDeposito deposito = new TransaccionDeposito();

        deposito.setMonto(Double.parseDouble(view.getMontoField().getText()));
        deposito.setIdSucursal(idSucursal);
        deposito.setNumeroCuenta(idCuentaSeleccionada);
        deposito.setFecha(LocalDateTime.now());
        deposito.setTipo("DEPOSITO");

        try {
            transaccionDAO.crearNuevaTransaccion(deposito);
            String opcion = VentanasEmergentes.mostrarAlertaConOpciones(
                    "El depósito se ha realizado con éxito",
                    "El depósito fue exitoso",
                    "¿Desea generar un comprobante?"
            );

            if ("Aceptar".equals(opcion)) {

                cancelarTransaccion();
            } else if ("Generar Comprobante".equals(opcion)) {

                generarComprobantePDF(deposito, primaryStage);
            }

        } catch (IOException e) {
            ventanasEmergentes.mostrarAlerta("Error", "Error al realizar depósito",
                    "No se pudo realizar el depósito: " + e.getMessage());
        }
    }

    private void registrarTransferencia() {
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        TransaccionTransferencia transferencia = new TransaccionTransferencia();

        transferencia.setMonto(Double.parseDouble(view.getMontoField().getText()));
        transferencia.setIdSucursal(idSucursal);
        transferencia.setNumeroCuentaOrigen(idCuentaSeleccionada);
        transferencia.setNumeroCuentaDestino(view.getCuentaDestinoField().getText());
        transferencia.setFecha(LocalDateTime.now());
        transferencia.setTipo("TRANSFERENCIA");

        try {
            transaccionDAO.crearNuevaTransaccion(transferencia);
            String opcion = VentanasEmergentes.mostrarAlertaConOpciones(
                    "La transferencia se ha realizado con éxito",
                    "La transferencia fue exitosa",
                    "¿Desea generar un comprobante?"
            );
            if ("Aceptar".equals(opcion)) {
                cancelarTransaccion();
            } else if ("Generar Comprobante".equals(opcion)) {
                generarComprobantePDF(transferencia, primaryStage);
            }

        } catch (IOException e) {
            ventanasEmergentes.mostrarAlerta("Error", "Error al realizar transferencia",
                    "No se pudo realizar la transferencia: " + e.getMessage());
        }
    }

    private void cancelarTransaccion() {
        CuentasView cuentasView = new CuentasView();
        CuentasController cuentasController = new CuentasController(cuentasView, primaryStage);

        cuentasView.mostrar(primaryStage);

    }



    public void generarComprobantePDF(Transaccion transaccion, Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar comprobante PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo PDF", "*.pdf"));
        fileChooser.setInitialFileName("comprobante_" + transaccion.getId() + ".pdf");

        File file = fileChooser.showSaveDialog(ownerWindow);
        if (file == null) {
            return; // Usuario canceló
        }

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Encabezado
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Comprobante de Transacción", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Detalles
            document.add(new Paragraph("ID: " + transaccion.getId()));
            document.add(new Paragraph("Tipo: " + transaccion.getTipo()));
            document.add(new Paragraph("Monto: $" + transaccion.getMonto()));
            document.add(new Paragraph("Fecha: " + transaccion.getFecha()));
            document.add(new Paragraph("Sucursal: " + transaccion.getIdSucursal()));

            // Extra para transferencias o depósitos
            if (transaccion instanceof TransaccionDeposito deposito) {
                document.add(new Paragraph("Cuenta: " + deposito.getNumeroCuenta()));
            } else if (transaccion instanceof TransaccionRetiro retiro) {
                document.add(new Paragraph("Cuenta: " + retiro.getNumeroCuenta()));
            } else if (transaccion instanceof TransaccionTransferencia transferencia) {
                document.add(new Paragraph("Cuenta Origen: " + transferencia.getNumeroCuentaOrigen()));
                document.add(new Paragraph("Cuenta Destino: " + transferencia.getNumeroCuentaDestino()));
            }

            document.close();
            System.out.println("Comprobante generado: " + file.getAbsolutePath());
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }


}