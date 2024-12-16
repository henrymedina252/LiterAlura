package com.literalura.service;

import com.literalura.model.Author;
import com.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    private int ano;

    public List<Author> obtenerAutoresNacidosDespuesDe(int year) {
        return authorRepository.findByBirthYearGreaterThan(year);
    }

    // Método para buscar autor por nombre
    public List<Author> buscarAutorPorNombre(String nombre) {
        return authorRepository.findByNameContainingIgnoreCase(nombre);

    // Método para obtener autores vivos en un determinado año
        // Suponemos que la fecha de muerte puede ser null (vivos)

        // Otros métodos...


    }

    public List<Author> obtenerTodosLosAutores() {
        return authorRepository.findAll();
    }

    public List<Author> obtenerAutoresVivosEnAno(int ano) {
        return null;
    }
}