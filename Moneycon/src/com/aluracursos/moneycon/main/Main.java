package com.aluracursos.moneycon.main;

import com.aluracursos.moneycon.models.*;
import com.aluracursos.moneycon.service.ConsumoAPI;
import com.aluracursos.moneycon.service.ConvierteJson;
import com.aluracursos.moneycon.service.JsonFile;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static Scanner teclado = new Scanner(System.in);
    private static ConsumoAPI consumoAPI = new ConsumoAPI();
    private static ConvierteJson conversor = new ConvierteJson();
    private static final String API_KEY = "API-KEY";
    private static List<Registro> historialConversiones = new ArrayList<>();
    private static File file = new File("HistorialConversiones.json");

    public static void main(String[] args) {

        int opcion = 0;
        String opcionString;
        if (file.exists())
            historialConversiones = obtenerHistorialConversiones();

        System.out.println("Convertidor de monedas");

        while (true) {
            System.out.println("\n[1] Convertir");
            System.out.println("[2] Historial de conversiones");
            System.out.println("[3] Salir");
            System.out.print("Opcion: ");

            opcionString = teclado.nextLine();
            if (isNumber(opcionString)) {
                opcion = Integer.parseInt(opcionString);
                switch (opcion) {
                    case 1:
                        convertir();
                        break;
                    case 2:
                        muestraHistorial();
                        break;
                    case 3:
                        guardarArchivoJson();
                        return; // finalizar el programa
                }
            }
            else
                System.out.println("\nIngrese un valor numerico\n");
        }
    }

    private static void convertir() {
        System.out.println("\n********************************\n");
        System.out.println("Moneda Base:\n");

        String codigoMonedaBase = obtenerCodigoMoneda();

        System.out.print("Cantidad: ");
        Double cantidad = teclado.nextDouble();
        teclado.nextLine();

        Moneda monedaBase = new Moneda(codigoMonedaBase);
        monedaBase.setCantidad(cantidad);

        System.out.println("\n***********************************\n");
        System.out.println("Moneda a Convertir:\n");

        String codigoMonedaTarget = obtenerCodigoMoneda();

        Moneda monedaTarget = new Moneda(codigoMonedaTarget);

        Double valorMonedaConvertida = convierteMoneda(monedaBase, monedaTarget);

        // Obtener el tiempo de la conversion
        LocalDateTime ldt = LocalDateTime.now();

        // Almacenar la conversion en un string
        String respuesta = monedaBase.getCantidad() + " [" + monedaBase.getCodigoMoneda() + "] equivale a " + valorMonedaConvertida + " [" +
                monedaTarget.getCodigoMoneda() + "]";

        // Guardar los datos en el historial de conversiones
        Registro registro = new Registro(ldt, respuesta);
        historialConversiones.add(registro);

        System.out.println();
        System.out.println(respuesta);
    }

    private static Double convierteMoneda(Moneda monedaBase, Moneda monedaTarget) {
        // Convertir ambas monedas
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/" + monedaBase.getCodigoMoneda() +
                "/" + monedaTarget.getCodigoMoneda();
        String json = consumoAPI.obtenerDatos(url);
        DatosConversion conversion = conversor.obtenerDatos(json, DatosConversion.class);
        return conversion.conversion_rate() * monedaBase.getCantidad();
    }

    private static String obtenerCodigoMoneda() {
        int opcion = 0;
        String opcionString;
        String codigo = "";

        System.out.println("[1] ARS - Peso Argentino");
        System.out.println("[2] BOB - Boliviano Boliviano");
        System.out.println("[3] BRL - Real Brasileño");
        System.out.println("[4] CLP - Peso Chileno");
        System.out.println("[5] COP - Peso Colombiano");
        System.out.println("[6] USD - Dólar Estadounidense");

        do {
            System.out.print("Opcion: ");
            opcionString = teclado.nextLine();

            if (isNumber(opcionString)) {
                opcion = Integer.parseInt(opcionString);
                switch (opcion) {
                    case 1:
                        codigo = "ARS";
                        break;

                    case 2:
                        codigo = "BOB";
                        break;

                    case 3:
                        codigo = "BRL";
                        break;

                    case 4:
                        codigo = "CLP";
                        break;

                    case 5:
                        codigo = "COP";
                        break;

                    case 6:
                        codigo = "USD";
                        break;

                    default:
                        System.out.println("\nIngrese una opción correcta\n");
                }
            }
            else
                System.out.println("\nIngrese un valor numerico\n");
        }
        while (opcion < 1 || opcion > 6);

        return codigo;
    }

    private static List<Registro> obtenerHistorialConversiones() {
        String json = JsonFile.leerJson(file);
        Type listType = new TypeToken <List<DatosRegistro>>(){}.getType();

        List<DatosRegistro> registros = conversor.obtenerLista(json, listType);
        return registros.stream()
                .map(d -> new Registro(d.time(), d.conversion()))
                .collect(Collectors.toList());
    }

    private static void guardarArchivoJson() {
        try {
            JsonFile.guardarJson(historialConversiones, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void muestraHistorial() {
        historialConversiones.stream()
                .forEach(System.out::println);
    }

    private static boolean isNumber(String string) {

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) < '0' || string.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}
