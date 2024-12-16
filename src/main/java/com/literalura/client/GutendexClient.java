package com.literalura.client;

import com.literalura.model.Book;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private static final String AUTHOR = "Jane+Austen";
    private static final String LANGUAGE = "en";
    private static final Logger logger = Logger.getLogger(GutendexClient.class.getName());
    private static final HttpClient client = HttpClient.newHttpClient();

    private static String buildUrl() {
        return String.format("%s?author=%s&language=%s", BASE_URL, AUTHOR, LANGUAGE);
    }

    public static void main(String[] args) {
        // Crear la solicitud HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(buildUrl()))
                .build();

        try {
            // Enviar la solicitud y recibir la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar que la respuesta fue exitosa (status 200)
            if (response.statusCode() == 200) {
                // Procesar la respuesta exitosa
                String responseBody = response.body();

                // Imprimir la respuesta JSON solo si el nivel de log es FINE
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("Respuesta JSON recibida: " + responseBody);
                }

                // Crear una instancia de ObjectMapper para la deserialización
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                // Deserializar la respuesta JSON en un objeto Java
                GutendexResponse gutendexResponse = objectMapper.readValue(responseBody, GutendexResponse.class);

                // Imprimir la cantidad de libros encontrados y sus detalles
                logger.info("Total de libros encontrados: " + gutendexResponse.getCount());
                gutendexResponse.getResults().forEach(book -> logger.info(book.toString()));
            } else {
                // En caso de que la respuesta no sea exitosa
                logger.log(Level.WARNING, "Error: {0} - {1}", new Object[]{response.statusCode(), response.body()});
            }
        } catch (Exception e) {
            // Manejo de errores en caso de excepciones
            logger.log(Level.SEVERE, "Error en la conexión con la API de Gutendex", e);
        }
    }
}
