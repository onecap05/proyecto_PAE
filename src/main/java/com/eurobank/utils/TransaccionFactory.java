package com.eurobank.utils;

import java.time.LocalDateTime;
import com.eurobank.models.*;

public class TransaccionFactory {
    public static Transaccion crearTransaccion(String tipo, String id, double monto,
                                               LocalDateTime fecha, String idSucursal,
                                               String... cuentas) {
        switch(tipo.toUpperCase()) {
            case "DEPOSITO":
                return new TransaccionDeposito(id, monto, fecha, idSucursal, cuentas[0]);
            case "RETIRO":
                return new TransaccionRetiro(id, monto, fecha, idSucursal, cuentas[0]);
            case "TRANSFERENCIA":
                return new TransaccionTransferencia(id, monto, fecha, idSucursal,
                        cuentas[0], cuentas[1]);
            default:
                return new Transaccion(id, monto, fecha, idSucursal);
        }
    }
}