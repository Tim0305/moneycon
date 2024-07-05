package com.aluracursos.moneycon.service;

import com.aluracursos.moneycon.adapters.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class GsonProvider {
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
    }
}
