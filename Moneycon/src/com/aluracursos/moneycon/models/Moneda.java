package com.aluracursos.moneycon.models;

public class Moneda {
    private String codigoMoneda;
    private Double cantidad;

    public Moneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
}
