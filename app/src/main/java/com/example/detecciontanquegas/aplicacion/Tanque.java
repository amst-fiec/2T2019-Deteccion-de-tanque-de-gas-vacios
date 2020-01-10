package com.example.detecciontanquegas.aplicacion;

import java.util.ArrayList;

public class Tanque {
    private String estado;
    private int contador;
    private String id;
    private int contadorpasado;

    public Tanque(){

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return "Tanque{" +
                "estado='" + estado + '\'' +
                ", contador=" + contador +
                '}';
    }
}
