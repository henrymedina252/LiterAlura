package com.literalura.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.model.Book;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpClient {

    private static final java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Usamos Jackson para mapear JSON a objetos Java

    // Realizar la solicitud y analizar la respuesta JSON
    public static void realizarSolicitud(String consulta) {
        try {
            // Construir la URL para la consulta
            String url = construirUrl(consulta);

            // Crear la solicitud HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")  // Encabezado para solicitar JSON
                    .GET()
                    .build();

            // Enviar la solicitud y recibir la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Comprobar el código de estado de la respuesta
            if (response.statusCode() == 200) {
                // Mapear la respuesta JSON a un objeto GutendexResponse
                GutendexResponse gutendexResponse = objectMapper.readValue(response.body(), GutendexResponse.class);

                // Mostrar los resultados (libros)
                List<Book> books = gutendexResponse.getResults();
                if (books.isEmpty()) {
                    System.out.println("No se encontraron libros.");
                } else {
                    for (Book book : books) {
                        System.out.println(book);  // Imprime la información de cada libro
                    }
                }
            } else {
                System.out.println("Error en la solicitud, código de estado: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();  // Manejo de excepciones
        }
    }

    // Construir la URL de la consulta (por autor, título, idioma)
    private static String construirUrl(String consulta) {
        String baseUrl = "https://gutendex.com/api/v2/books?";
        return baseUrl + "search=" + consulta.replace(" ", "+");
    }

    public static void main(String[] args) {
        // Ejemplo de búsqueda por autor
        realizarSolicitud("author:Jane Austen");

        // Ejemplo de búsqueda por título
        realizarSolicitud("title:War and Peace");
    }
}
