package cl.esperanza.pago.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient facturacionWebClient(@Value("${facturacion.service.url:http://localhost:8083/api/v1/facturacion}") String facturacionServiceUrl) {
        return WebClient.builder().baseUrl(facturacionServiceUrl).build();
    }
}
