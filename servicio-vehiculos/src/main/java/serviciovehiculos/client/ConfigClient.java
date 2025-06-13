package serviciovehiculos.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import serviciovehiculos.config.AgencyConfig;

@Component
public class ConfigClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${agency.config.service.url}")
    private String configServiceUrl;

    public AgencyConfig obtenerConfiguracionAgencia() {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri(configServiceUrl)
                    .retrieve()
                    .bodyToMono(AgencyConfig.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error obteniendo configuración de agencia: " + e.getStatusCode());
        }
    }

}
