package com.literalura.model;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String language;
    private int downloads;

    // Relación con la entidad Author (muchos libros pueden tener un mismo autor)
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    // Constructor
    public Book(String title, String language, int downloads, Author author) {
        this.title = title;
        this.language = language;
        this.downloads = downloads;
        this.author = author;
    }

    public Book(String titulo, String autor, String idioma, int descargas) {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Título: " + title + "\nAutor: " + author.toString() + "\nIdioma: " + language + "\nDescargas: " + downloads;
    }
}
