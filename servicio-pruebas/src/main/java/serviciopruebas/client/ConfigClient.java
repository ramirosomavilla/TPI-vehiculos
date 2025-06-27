package serviciopruebas.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import serviciopruebas.dtos.AgencyConfigDTO;

@Component
public class ConfigClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${agency.config.service.url}")
    private String configServiceUrl;

    public AgencyConfigDTO obtenerConfiguracionAgencia() {
        try {
            System.out.println("Obteniendo configuración de agencia desde: " + configServiceUrl);
            var res = webClientBuilder.build()
                    .get()
                    .uri(configServiceUrl)
                    .retrieve()
                    .bodyToMono(AgencyConfigDTO.class)
                    .block();

            System.out.println("Configuración de agencia obtenida: " + res);
            if (res == null) {
                System.out.println("La respuesta fue null");
            }
            return res;
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error obteniendo configuración de agencia: " + e.getStatusCode());
        }
    }

}
