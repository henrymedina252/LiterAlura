package com.literalura.controller;

import com.literalura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

public class GutendexController {

    private final GutendexService gutendexService;

    @Autowired
    public GutendexController(GutendexService gutendexService) {
        this.gutendexService = gutendexService;
    }

    @GetMapping("/libros")
    public Mono<ResponseEntity<?>> obtenerLibros() {
        return gutendexService.obtenerLibros()
                .onErrorResume(this::manejarError); // Manejo de errores
    }

    // Método para manejar errores
    private Mono<ResponseEntity<?>> manejarError(Throwable throwable) {
        return Mono.just(ResponseEntity.status(500).body(throwable.getMessage()));
    }
}
