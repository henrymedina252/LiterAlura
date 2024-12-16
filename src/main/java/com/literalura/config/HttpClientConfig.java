package com.literalura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;  // Usar ReactorHttpClient de Netty

import java.time.Duration;

@Configuration
public class HttpClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        // Crear un HttpClient de Reactor Netty
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(tcpClient -> tcpClient.option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)  // Configura el tiempo de conexión en milisegundos
                        .doOnConnected(conn -> conn.addHandlerLast(new io.netty.handler.timeout.ReadTimeoutHandler(15))));  // Configura el tiempo de espera de lectura (en segundos)

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))  // Conector reactivo
                .baseUrl("http://example.com");  // Base URL de ejemplo
    }
}
