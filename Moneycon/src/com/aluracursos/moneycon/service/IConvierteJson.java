package com.aluracursos.moneycon.service;

import java.lang.reflect.Type;
import java.util.List;

public interface IConvierteJson {
    <T> T obtenerDatos(String json, Class<T> Class);
    <T> List<T> obtenerLista(String json, Type typeOfT);
}
