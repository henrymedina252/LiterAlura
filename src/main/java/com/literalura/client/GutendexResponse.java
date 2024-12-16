package com.literalura.client;

import com.literalura.model.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GutendexResponse {

    @JsonProperty("results") // Asegúrate de mapear correctamente el campo de la respuesta JSON
    private List<Book> results;  // 'results' es la lista de libros que devuelve la API

    // Si la API también devuelve un campo 'count' que indica el número total de libros, mapea ese campo
    @JsonProperty("count")
    private int count;

    // Getter para acceder a los resultados
    public List<Book> getResults() {
        return results;
    }

    // Setter para resultados
    public void setResults(List<Book> results) {
        this.results = results;
    }

    // Getter para el count de los libros
    public int getCount() {
        return count;
    }

    // Setter para el count
    public void setCount(int count) {
        this.count = count;
    }

    // Método toString (opcional, para depuración)
    @Override
    public String toString() {
        return "GutendexResponse{" +
                "count=" + count +
                ", results=" + results +
                '}';
    }
}
