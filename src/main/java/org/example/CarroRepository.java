package org.example;

import java.util.List;

public interface CarroRepository {
    void crearCarro(Carro carro) throws CarroException;
    Carro obtenerCarroPorId(int id) throws CarroException;
    List<Carro> obtenerTodosLosCarros();
    void actualizarCarro(Carro carro) throws CarroException;
    void eliminarCarro(int id) throws CarroException;

    // Método de reporte con Streams
    List<Carro> generarReporte(String marcaFiltro, int añoMinimo, int limite);
}