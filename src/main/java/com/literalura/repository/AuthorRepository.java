package com.literalura.repository;

import com.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Aquí se pueden agregar consultas personalizadas si es necesario

     List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqualOrDeathYearIsNull(int birthYear, int deathYear);

    // Método para buscar un autor por nombre
    List<Author> findByNameContainingIgnoreCase(String name);

    // Método para obtener autores nacidos después de un año específico
    List<Author> findByBirthYearGreaterThan(int birthYear);
}