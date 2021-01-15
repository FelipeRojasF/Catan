package com.catan.application.api.controllers.v2.resource;

public class CardEspResource {

    private String id;
    private int numero;
    private String descripcion;

    public CardEspResource(String id, int numero, String descripcion) {
        this.id = id;
        this.numero = numero;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
