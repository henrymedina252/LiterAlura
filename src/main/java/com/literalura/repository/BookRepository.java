package com.literalura.repository;

import com.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Método para contar libros por idioma
    long countByLanguage(String language);

    // Método para buscar libros por idioma
    List<Book> findByLanguage(String language);

    // Método para obtener los 10 libros más descargados
    List<Book> findTop10ByOrderByDownloadsDesc();

    List<Book> findByTitleContainingIgnoreCase(String titulo);

    List<Book> findByAuthorContainingIgnoreCase(String autor);

    // Otros métodos si es necesario
}
