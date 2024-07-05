package com.aluracursos.moneycon.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime != null) {
            jsonWriter.value(localDateTime.format(formatter));
        } else {
            jsonWriter.nullValue();
        }
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        if (jsonReader != null) {
            return LocalDateTime.parse(jsonReader.nextString(), formatter);
        }
        return null;
    }
}
