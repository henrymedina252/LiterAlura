package com.literalura.service;

import com.literalura.client.GutendexResponse;
import com.literalura.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GutendexService {

    private final WebClient webClient;

    @Value("${gutendex.api.url}")
    private String gutendexUrl;

    @Autowired
    public GutendexService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ResponseEntity<?>> obtenerLibros() {
        return webClient.get()
                .uri(gutendexUrl)
                .retrieve()
                .bodyToMono(GutendexResponse.class)  // Deserializa la respuesta en GutendexResponse
                .flatMap(response -> {
                    if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                        // Si hay libros, devolver un Mono<ResponseEntity<List<Book>>>
                        return Mono.just(ResponseEntity.ok(response.getResults()));  // Aquí estamos devolviendo los libros
                    } else {
                        // Si no hay libros, devolver un Mono<ResponseEntity<String>> con mensaje
                        return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontraron libros."));
                    }
                })
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los libros"))); // Error genérico
    }
}
