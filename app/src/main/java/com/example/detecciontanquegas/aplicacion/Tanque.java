package com.example.detecciontanquegas.aplicacion;

public class Tanque {
    private String estado;
    private int contador;

    public Tanque(){

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Tanque{" +
                "estado='" + estado + '\'' +
                ", contador=" + contador +
                '}';
    }
}
