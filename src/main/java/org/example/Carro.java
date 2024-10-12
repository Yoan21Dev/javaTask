package org.example;

// Carro.java
public class Carro {
    private int id;
    private String marca;
    private String modelo;
    private int año;

    public Carro(int id, String marca, String modelo, int año) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    @Override
    public String toString() {
        return "Carro [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", año=" + año + "]";
    }
}
