package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarroRepositoryImpl implements CarroRepository {
    private List<Carro> carros = new ArrayList<>();

    @Override
    public void crearCarro(Carro carro) throws CarroException {
        Optional<Carro> existe = carros.stream()
                .filter(c -> c.getId() == carro.getId())
                .findFirst();
        if (existe.isPresent()) {
            throw new CarroException("El carro con ID " + carro.getId() + " ya existe.");
        }
        carros.add(carro);
    }

    @Override
    public Carro obtenerCarroPorId(int id) throws CarroException {
        return carros.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CarroException("Carro con ID " + id + " no encontrado."));
    }

    @Override
    public List<Carro> obtenerTodosLosCarros() {
        return new ArrayList<>(carros);
    }

    @Override
    public void actualizarCarro(Carro carro) throws CarroException {
        Carro existente = obtenerCarroPorId(carro.getId());
        existente.setMarca(carro.getMarca());
        existente.setModelo(carro.getModelo());
        existente.setAño(carro.getAño());
    }

    @Override
    public void eliminarCarro(int id) throws CarroException {
        Carro carro = obtenerCarroPorId(id);
        carros.remove(carro);
    }
}
