package com.literalura.controller;

import com.literalura.model.Book;
import com.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.List;

@Component
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    // Método para consultar libros por título (buscando en la base de datos)
    public Mono<List<Book>> consultarLibrosPorTitulo(String consulta) {
        logger.info("Consultando libros por título: {}", consulta);
        return bookService.buscarLibrosPorTitulo(consulta)  // El método en el service debe devolver un Mono<List<Book>>
                .doOnSuccess(libros -> {
                    if (libros == null || libros.isEmpty()) {
                        logger.warn("No se encontraron libros con el título: {}", consulta);
                    } else {
                        logger.info("Libros encontrados: {}", libros.size());
                        libros.forEach(libro -> logger.info(libro.toString()));
                    }
                })
                .doOnError(e -> logger.error("Error al consultar libros por título", e));
    }

    // Método para consultar libros por autor (buscando en la base de datos)
    public Mono<List<Book>> consultarLibrosPorAutor(String consulta) {
        logger.info("Consultando libros por autor: {}", consulta);
        return bookService.buscarLibrosPorAutor(consulta)  // Asegúrate que el servicio tenga este método
                .doOnSuccess(libros -> {
                    if (libros == null || libros.isEmpty()) {
                        logger.warn("No se encontraron libros para el autor: {}", consulta);
                    } else {
                        logger.info("Libros encontrados: {}", libros.size());
                        libros.forEach(libro -> logger.info(libro.toString()));
                    }
                })
                .doOnError(e -> logger.error("Error al consultar libros por autor", e));
    }

    // Método para consultar un libro por título utilizando la API de Gutendex
    public Mono<Book> consultarLibrosPorTituloApi(String consulta) {
        logger.info("Consultando libros en la API de Gutendex por título: {}", consulta);
        return bookService.buscarLibroPorTituloEnApi(consulta)  // Este método debe estar en el servicio
                .doOnSuccess(libro -> {
                    if (libro == null) {
                        logger.warn("No se encontraron libros para: {}", consulta);
                    } else {
                        logger.info("Resultado encontrado en la API: {}", libro);
                    }
                })
                .doOnError(e -> logger.error("Error al consultar libros en la API de Gutendex", e));
    }

    // Método para insertar un libro en la base de datos
    public Mono<Void> insertarLibro(String titulo, String autor) {
        logger.info("Insertando libro: {} por {}", titulo, autor);
        if (titulo == null || titulo.trim().isEmpty() || autor == null || autor.trim().isEmpty()) {
            logger.error("El título o el autor no pueden ser nulos o vacíos");
            return Mono.error(new IllegalArgumentException("El título o el autor no pueden ser nulos o vacíos"));
        }

        // Asignamos valores predeterminados para el libro
        String idioma = "Desconocido";
        int descargas = 0;

        // Crear el libro con un autor ficticio (esto depende de tu lógica de negocio)
        Book libro = new Book(titulo, autor, idioma, descargas);

        return bookService.insertarLibro(libro)  // Este método debe estar en el servicio
                .doOnSuccess(aVoid -> logger.info("Libro insertado exitosamente: {}", libro))
                .doOnError(e -> logger.error("Error al insertar el libro", e));
    }
}
