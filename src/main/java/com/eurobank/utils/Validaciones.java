package com.eurobank.utils;

public class Validaciones {


    public static boolean validarNombre(String nombre) {
        return nombre != null && nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }


    public static boolean validarNumeroTelefono(String telefono) {
        return telefono != null && telefono.matches("^\\d{10}$");
    }

    public static boolean validarMontoPositivo(String monto) {
        return monto != null && monto.matches("^\\d+(\\.\\d+)?$") && Double.parseDouble(monto) > 0;
    }


}
