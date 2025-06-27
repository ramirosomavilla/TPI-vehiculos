package serviciopruebas.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import serviciopruebas.dtos.AgencyConfigDTO;

@Component
public class ConfigClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${agency.config.service.url}")
    private String configServiceUrl;

    public AgencyConfigDTO obtenerConfiguracionAgencia() {
        try {
            System.out.println("Obteniendo configuración de agencia desde: " + configServiceUrl);
            AgencyConfigDTO res = restTemplate.getForObject(configServiceUrl, AgencyConfigDTO.class);
            System.out.println("Configuración de agencia obtenida: " + res);
            if (res == null) {
                System.out.println("La respuesta fue null");
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo configuración de agencia: " + e.getMessage());
        }
    }
}
