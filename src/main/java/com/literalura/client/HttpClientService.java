package com.literalura.client;

import com.literalura.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class HttpClientService {

    // Definir el logger
    private static final Logger log = LoggerFactory.getLogger(HttpClientService.class);

    private final WebClient webClient;

    public HttpClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Obtiene los libros desde la API de Gutendex.
     */
    public Mono<GutendexResponse> obtenerLibrosFromGutendex() {
        return webClient.get()
                .uri("/books")
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    log.error("Error de cliente al obtener los libros");
                    return Mono.error(new ApiException("Client error while fetching books"));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    log.error("Error de servidor al obtener los libros");
                    // Usar ResponseStatusException para errores del servidor
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error while fetching books"));
                })
                .bodyToMono(GutendexResponse.class)
                .timeout(Duration.ofSeconds(10))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))  // Retry con backoff
                .doOnTerminate(() -> log.info("Request completed or failed"));
    }
}
