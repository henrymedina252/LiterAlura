package com.literalura;

import com.literalura.model.Book;
import com.literalura.model.Author;
import com.literalura.service.BookService;
import com.literalura.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
import java.util.DoubleSummaryStatistics;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("====================================");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Ver todos los libros");
            System.out.println("3. Ver todos los autores");
            System.out.println("4. Ver autores vivos en un año");
            System.out.println("5. Guardar libro");
            System.out.println("6. Exhibir cantidad de libros por idioma");
            System.out.println("7. Top 10 libros más descargados");
            System.out.println("8. Buscar autor por nombre");
            System.out.println("9. Listar autores nacidos después de un determinado año");
            System.out.println("10. Mostrar estadísticas de descargas de los libros");
            System.out.println("11. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Introduce el título del libro: ");
                    String titulo = scanner.nextLine();
                    List<Book> libros = bookService.buscarLibrosPorTitulo(titulo).block();  // Usamos block() para obtener el valor
                    if (libros != null && !libros.isEmpty()) {
                        libros.forEach(System.out::println);
                    } else {
                        System.out.println("No se encontraron libros con ese título.");
                    }
                    break;
                case 2:
                    List<Book> todosLibros = bookService.obtenerTodosLosLibros();  // Usamos block() para obtener la lista
                    if (todosLibros != null && !todosLibros.isEmpty()) {
                        todosLibros.forEach(System.out::println);
                    } else {
                        System.out.println("No se encontraron libros.");
                    }
                    break;


                case 3:
                    List<Author> autores = authorService.obtenerTodosLosAutores();
                    autores.forEach(System.out::println);
                    break;

                case 4:
                    // Ver autores vivos en un año
                    System.out.print("Introduce el año: ");
                    int ano = obtenerAnoValido(scanner);
                    List<Author> autoresVivos = authorService.obtenerAutoresVivosEnAno(ano);
                    if (autoresVivos.isEmpty()) {
                        System.out.println("No se encontraron autores vivos en el año " + ano);
                    } else {
                        System.out.println("Autores vivos en el año " + ano + ":");
                        for (Author autor : autoresVivos) {
                            System.out.println(autor.getName() + " (" + autor.getBirthYear() + " - " +
                                    (autor.getDeathYear() != null ? autor.getDeathYear() : "Vivo") + ")");
                        }
                    }
                    break;

                case 5:
                    System.out.print("Introduce el título del libro: ");
                    String nuevoTitulo = scanner.nextLine();
                    System.out.print("Introduce el idioma del libro: ");
                    String idioma = scanner.nextLine();
                    System.out.print("Introduce el número de descargas: ");
                    int descargas = scanner.nextInt();
                    scanner.nextLine();  // Consumir salto de línea

                    // Crear el autor
                    System.out.print("Introduce el nombre del autor: ");
                    String nombreAutor = scanner.nextLine();
                    System.out.print("Introduce el año de nacimiento del autor: ");
                    int nacimiento = scanner.nextInt();
                    System.out.print("Introduce el año de fallecimiento del autor (0 si está vivo): ");
                    int fallecimiento = scanner.nextInt();
                    scanner.nextLine();  // Consumir salto de línea

                    // Crear objeto Author y Book
                    Author nuevoAutor = new Author(nombreAutor, nacimiento, fallecimiento);  // Cambiar 'autor' a 'nuevoAutor'
                    Book libro = new Book(nuevoTitulo, idioma, descargas, nuevoAutor);

                    // Guardar el libro y autor
                    bookService.guardarLibro(libro);
                    System.out.println("Libro guardado correctamente.");
                    break;

                case 6:
                    System.out.print("Introduce el idioma (ejemplo: 'Español' o 'Inglés'): ");
                    String idiomaConsulta = scanner.nextLine();
                    long cantidadLibros = bookService.contarLibrosPorIdioma(idiomaConsulta);
                    System.out.println("Cantidad de libros en " + idiomaConsulta + ": " + cantidadLibros);
                    break;

                case 7:
                    // Mostrar los 10 libros más descargados
                    List<Book> topLibros = bookService.obtenerTop10LibrosMasDescargados();
                    if (topLibros.isEmpty()) {
                        System.out.println("No se encontraron libros.");
                    } else {
                        System.out.println("Top 10 libros más descargados:");
                        for (Book libroTop : topLibros) {
                            System.out.println(libroTop.getTitle() + " - Descargas: " + libroTop.getDownloads());
                        }
                    }
                    break;

                case 8:
                    // Buscar autor por nombre
                    System.out.print("Introduce el nombre del autor: ");
                    String autorNombre = scanner.nextLine();
                    List<Author> autoresPorNombre = authorService.buscarAutorPorNombre(autorNombre);
                    if (autoresPorNombre.isEmpty()) {
                        System.out.println("No se encontraron autores con ese nombre.");
                    } else {
                        System.out.println("Autores encontrados:");
                        for (Author autorEncontrado : autoresPorNombre) {
                            System.out.println(autorEncontrado.getName() + " (" + autorEncontrado.getBirthYear() + " - " +
                                    (autorEncontrado.getDeathYear() != null ? autorEncontrado.getDeathYear() : "Vivo") + ")");
                        }
                    }
                    break;

                case 9:
                    // Listar autores nacidos después de un determinado año
                    System.out.print("Introduce el año de nacimiento: ");
                    int year = scanner.nextInt();
                    List<Author> autoresNacidosDespues = authorService.obtenerAutoresNacidosDespuesDe(year);
                    if (autoresNacidosDespues.isEmpty()) {
                        System.out.println("No se encontraron autores nacidos después del año " + year);
                    } else {
                        System.out.println("Autores nacidos después del año " + year + ":");
                        for (Author autor : autoresNacidosDespues) {
                            System.out.println(autor.getName() + " (" + autor.getBirthYear() + " - " +
                                    (autor.getDeathYear() != null ? autor.getDeathYear() : "Vivo") + ")");
                        }
                    }
                    break;

                case 10:
                    // Mostrar estadísticas de descargas de los libros
                    DoubleSummaryStatistics estadisticas = bookService.obtenerEstadisticasDeDescargas();
                    System.out.println("Estadísticas de descargas de los libros:");
                    System.out.println("Total de descargas: " + estadisticas.getSum());
                    System.out.println("Promedio de descargas: " + estadisticas.getAverage());
                    System.out.println("Número máximo de descargas: " + estadisticas.getMax());
                    System.out.println("Número mínimo de descargas: " + estadisticas.getMin());
                    break;

                case 11:
                    System.out.println("¡Adiós!");
                    return;  // Salir de la aplicación

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Método para obtener un año válido del usuario
    private int obtenerAnoValido(Scanner scanner) {
        int ano = 0;
        while (true) {
            System.out.print("Introduce el año (por ejemplo, 1980): ");
            if (scanner.hasNextInt()) {
                ano = scanner.nextInt();
                if (ano > 0) {
                    return ano;
                } else {
                    System.out.println("Por favor, ingrese un año válido.");
                }
            } else {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next(); // Limpiar la entrada no válida
            }
        }
    }
}
