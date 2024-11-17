package org.example;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

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
                    generarReporteCarros();
                    break;
                case 7:
                    leerReporteDesdeArchivo();
                    break;
                case 8:
                    salir = true;
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, elige una opción entre 1 y 8.");
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
        System.out.println("6. Reporte de Carros"); // Nueva opción para el reporte
        System.out.println("7. Leer Reporte desde Archivo");
        System.out.println("8. Salir");
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

    private static void generarReporteCarros() {
        System.out.println("=== Generar Reporte de Carros ===");

        System.out.print("Ingresa la marca de los carros que deseas filtrar: ");
        String marcaFiltro = scanner.nextLine();

        System.out.print("Ingresa el año mínimo de los carros a incluir en el reporte: ");
        int añoMinimo = leerEntero();

        System.out.print("Ingresa la cantidad máxima de carros a mostrar en el reporte: ");
        int limite = leerEntero();

        List<Carro> reporte = repo.generarReporte(marcaFiltro, añoMinimo, limite);
        if (reporte.isEmpty()) {
            System.out.println("No se encontraron carros que cumplan con el filtro especificado.");
        } else {
            // Guardar reporte en archivo .txt
            Path rutaArchivo = Paths.get("reporte_carros.txt");
            try {
                String contenido = reporte.stream()
                        .map(Carro::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                Files.write(rutaArchivo, contenido.getBytes(StandardCharsets.UTF_8));
                System.out.println("Reporte generado y guardado en " + rutaArchivo.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error al guardar el reporte en el archivo: " + e.getMessage());
            }
        }
    }
    private static void leerReporteDesdeArchivo() {
        System.out.println("=== Leer Reporte desde Archivo ===");
        Path rutaArchivo = Paths.get("reporte_carros.txt");

        if (Files.exists(rutaArchivo)) {
            try {
                List<String> lineas = Files.readAllLines(rutaArchivo, StandardCharsets.UTF_8);
                if (lineas.isEmpty()) {
                    System.out.println("El archivo de reporte está vacío.");
                } else {
                    System.out.println("Contenido del reporte:");
                    lineas.forEach(System.out::println);
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo de reporte: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró el archivo de reporte. Genera un reporte primero.");
        }
    }
}
