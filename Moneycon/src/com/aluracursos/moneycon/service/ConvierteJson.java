package com.aluracursos.moneycon.service;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class ConvierteJson implements IConvierteJson {

    @Override
    public <T> T obtenerDatos(String json, Class<T> className) {
        try {
            Gson gson = GsonProvider.createGson();
            return gson.fromJson(json, className);
        } catch (Exception e) {
            throw new RuntimeException("Error. No se pudo realizar la conversion");
        }
    }

    @Override
    public <T> List<T> obtenerLista(String json, Type typeOfT) {
        try {
            Gson gson = GsonProvider.createGson();
            return gson.fromJson(json, typeOfT);
        } catch (Exception e) {
            throw new RuntimeException("Error. No se pudo realizar la conversion", e);
        }
    }
}
