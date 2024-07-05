package com.aluracursos.moneycon.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Registro {
    private LocalDateTime time;
    private String conversion;
    private static final DateTimeFormatter dtm = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Registro(LocalDateTime time, String conversion) {
        this.time = time;
        this.conversion = conversion;
    }

    public LocalDateTime gettime() {
        return time;
    }

    public void settime(LocalDateTime time) {
        this.time = time;
    }

    public String getConversion() {
        return conversion;
    }

    public void setConversion(String conversion) {
        this.conversion = conversion;
    }

    @Override
    public String toString() {
        return time.format(dtm) + " -> " + conversion;
    }
}
