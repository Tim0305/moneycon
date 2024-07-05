package com.aluracursos.moneycon.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;

public class JsonFile {
    public static <T> void guardarJson(List<T> className, File file) throws IOException {
        Gson gson = GsonProvider.createGson();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(gson.toJson(className));
        fileWriter.close();
    }

    public static String leerJson(File file) {
        FileReader fileReader = null;
        BufferedReader buffer = null;
        StringBuilder json = new StringBuilder();

        try {
            fileReader = new FileReader(file);
            buffer = new BufferedReader(fileReader);
            String linea;
            while ((linea = buffer.readLine()) != null){
                json.append(linea).append("\n");
            }
        } catch (FileNotFoundException e){
            throw new RuntimeException("Archivo no encontrado");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fileReader != null)
                    fileReader.close();
                if (buffer != null)
                    buffer.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        return json.toString();
    }
}
