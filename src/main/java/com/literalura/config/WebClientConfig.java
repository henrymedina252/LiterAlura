package com.literalura.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import java.time.Duration;

@Configuration
public class WebClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebClientConfig.class);

    @Value("${http.client.connectTimeout:5}") // Configuración del tiempo de espera de conexión en segundos
    private long connectTimeout;

    @Value("${http.client.responseTimeout:10}") // Configuración del tiempo de respuesta en segundos
    private long responseTimeout;

    @Bean
    public WebClient webClient() {
        // Crear el HttpClient de Reactor Netty
        HttpClient httpClient = HttpClient.create()
                // Configura el tiempo de espera de conexión (en milisegundos)
                .tcpConfiguration(tcpClient -> tcpClient.option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) (connectTimeout * 1000)))
                // Configura el tiempo de respuesta (lectura) usando el tiempo de respuesta en segundos convertido a milisegundos
                .doOnConnected(conn -> conn.addHandlerLast(new io.netty.handler.timeout.ReadTimeoutHandler((int) (responseTimeout * 1000))))
                .doOnRequest((req, con) -> logger.info("Request: {}", req.uri()))
                .doOnResponse((res, con) -> logger.info("Response: {}", res.status()));

        // Retorna un WebClient configurado con el HttpClient
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://gutendex.com")
                .build();
    }
}
