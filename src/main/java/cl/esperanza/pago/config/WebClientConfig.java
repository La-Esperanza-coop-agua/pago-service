package cl.esperanza.pago.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient facturacionWebClient() {
        // Apuntamos directo al puerto 8083 de Facturación
        return WebClient.builder()
                .baseUrl("http://localhost:8083/api/v1/facturacion")
                .build();
    }
}