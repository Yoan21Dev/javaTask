package org.example;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static CarroRepository repo = new CarroRepositoryImpl();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            imprimirMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    crearCarro();
                    break;
                case 2:
                    listarTodosLosCarros();
                    break;
                case 3:
                    obtenerCarroPorId();
                    break;
                case 4:
                    actualizarCarro();
                    break;
                case 5:
                    eliminarCarro();
                    break;
                case 6:
                    salir = true;
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, elige una opción entre 1 y 6.");
            }

            System.out.println(); // Línea en blanco para mejor legibilidad
        }

        scanner.close();
    }

    private static void imprimirMenu() {
        System.out.println("=== Menú CRUD de Carros ===");
        System.out.println("1. Crear Carro");
        System.out.println("2. Listar Todos los Carros");
        System.out.println("3. Obtener Carro por ID");
        System.out.println("4. Actualizar Carro");
        System.out.println("5. Eliminar Carro");
        System.out.println("6. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private static int leerOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            return opcion;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Consumir la entrada inválida
            return -1; // Opción inválida
        }
    }

    private static void crearCarro() {
        try {
            System.out.println("=== Crear Nuevo Carro ===");
            System.out.print("Ingresa el ID del carro: ");
            int id = leerEntero();

            System.out.print("Ingresa la marca del carro: ");
            String marca = scanner.nextLine();

            System.out.print("Ingresa el modelo del carro: ");
            String modelo = scanner.nextLine();

            System.out.print("Ingresa el año del carro: ");
            int año = leerEntero();

            Carro carro = new Carro(id, marca, modelo, año);
            repo.crearCarro(carro);
            System.out.println("Carro creado exitosamente.");
        } catch (CarroException e) {
            System.err.println("Error al crear el carro: " + e.getMessage());
        }
    }

    private static void listarTodosLosCarros() {
        System.out.println("=== Lista de Carros ===");
        List<Carro> carros = repo.obtenerTodosLosCarros();
        if (carros.isEmpty()) {
            System.out.println("No hay carros registrados.");
        } else {
            carros.forEach(System.out::println);
        }
    }

    private static void obtenerCarroPorId() {
        try {
            System.out.println("=== Obtener Carro por ID ===");
            System.out.print("Ingresa el ID del carro: ");
            int id = leerEntero();
            Carro carro = repo.obtenerCarroPorId(id);
            System.out.println("Carro encontrado:");
            System.out.println(carro);
        } catch (CarroException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void actualizarCarro() {
        try {
            System.out.println("=== Actualizar Carro ===");
            System.out.print("Ingresa el ID del carro a actualizar: ");
            int id = leerEntero();
            Carro carroExistente = repo.obtenerCarroPorId(id);

            System.out.print("Ingresa la nueva marca (actual: " + carroExistente.getMarca() + "): ");
            String marca = scanner.nextLine();
            if (!marca.trim().isEmpty()) {
                carroExistente.setMarca(marca);
            }

            System.out.print("Ingresa el nuevo modelo (actual: " + carroExistente.getModelo() + "): ");
            String modelo = scanner.nextLine();
            if (!modelo.trim().isEmpty()) {
                carroExistente.setModelo(modelo);
            }

            System.out.print("Ingresa el nuevo año (actual: " + carroExistente.getAño() + "): ");
            String añoInput = scanner.nextLine();
            if (!añoInput.trim().isEmpty()) {
                int año = Integer.parseInt(añoInput);
                carroExistente.setAño(año);
            }

            repo.actualizarCarro(carroExistente);
            System.out.println("Carro actualizado exitosamente.");
        } catch (CarroException e) {
            System.err.println("Error al actualizar el carro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Año inválido. Debe ser un número entero.");
        }
    }

    private static void eliminarCarro() {
        try {
            System.out.println("=== Eliminar Carro ===");
            System.out.print("Ingresa el ID del carro a eliminar: ");
            int id = leerEntero();
            repo.eliminarCarro(id);
            System.out.println("Carro con ID " + id + " eliminado exitosamente.");
        } catch (CarroException e) {
            System.err.println("Error al eliminar el carro: " + e.getMessage());
        }
    }

    private static int leerEntero() {
        while (true) {
            try {
                int numero = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                return numero;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consumir la entrada inválida
                System.out.print("Entrada inválida. Por favor, ingresa un número entero: ");
            }
        }
    }
}
