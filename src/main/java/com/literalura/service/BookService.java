package com.literalura.service;

import com.literalura.model.Author;
import com.literalura.model.Book;
import com.literalura.repository.BookRepository;
import com.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;



    // Método para obtener los 10 libros más descargados
    public List<Book> obtenerTop10LibrosMasDescargados() {
        return bookRepository.findTop10ByOrderByDownloadsDesc();
    }

    // Método para contar libros por idioma
    public long contarLibrosPorIdioma(String idioma) {
        return bookRepository.countByLanguage(idioma);
    }

    // Obtener estadísticas de descargas de los libros
    public DoubleSummaryStatistics obtenerEstadisticasDeDescargas() {
        List<Book> libros = bookRepository.findAll();
        return libros.stream()
                .mapToDouble(Book::getDownloads)
                .summaryStatistics();
    }

    public Book guardarLibro(Book book) {
        Author author = book.getAuthor();

        // Verificar si el autor tiene un ID
        if (author.getId() == null) {
            // Si no tiene un ID, lo guardamos
            author = authorRepository.save(author);
        }

        // Asignamos el autor al libro y lo guardamos
        book.setAuthor(author);
        return bookRepository.save(book);  // Guardamos el libro con el autor asignado
    }
    // Obtener todos los libros
    public List<Book> obtenerTodosLosLibros() {
        return bookRepository.findAll();
    }
    // Buscar libros por título
    public Mono<List<Book>> buscarLibrosPorTitulo(String titulo) {
        return Mono.just(bookRepository.findByTitleContainingIgnoreCase(titulo));
    }
    // Buscar libros por autor
    public Mono<List<Book>> buscarLibrosPorAutor(String autor) {
        return Mono.just(bookRepository.findByAuthorContainingIgnoreCase(autor));
    }
    // Buscar libros por idioma
    public List<Book> buscarLibrosPorIdioma(String idioma) {
        return bookRepository.findByLanguage(idioma);
    }

    // Obtener todos los autores
    public List<Author> obtenerTodosLosAutores() {
        return authorRepository.findAll();
    }

    // Listar autores vivos en un año
    public List<Author> obtenerAutoresVivosEnAno(int ano) {
        return authorRepository.findAll().stream()
                .filter(author -> author.getBirthYear() <= ano && (false || author.getDeathYear() >= ano))
                .toList();
    }

    public Mono<Book> buscarLibroPorTituloEnApi(String titulo) {
        // Lógica para hacer la llamada a la API y devolver el libro
        return Mono.empty();  // Ejemplo vacío, deberías implementar la llamada real
    }

    // Buscar autor por nombre
    public List<Author> buscarAutorPorNombre(String nombre) {
        return authorRepository.findByNameContainingIgnoreCase(nombre);
    }


    public Mono<Void> insertarLibro(Book libro) {
        return Mono.fromRunnable(() -> bookRepository.save(libro));  // Guarda el libro y retorna un Mono<Void>
    }

    // Listar autores nacidos después de un determinado año
    public List<Author> obtenerAutoresNacidosDespuesDe(int year) {
        return authorRepository.findByBirthYearGreaterThan(year);
    }
}
