package serviciopruebas.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;



@Component
public class PruebaClient {


    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${pruebas.service.url}")
    private String pruebasServiceUrl;

    // TODO: add error handling
    public boolean vehiculoEnPrueba(int idVehiculo) {
        try {
            Boolean expired = webClientBuilder.build()
                .get()
                .uri(pruebasServiceUrl + "/api/v1/pruebas/vehiculos/" + idVehiculo + "/en-curso")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
            return expired;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error consultando el servicio de usuarios: " + e.getStatusCode());
        }
    }

    public Integer obtenerClienteDeVehiculoEnPrueba(int vehiculoId) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri(pruebasServiceUrl + "/api/v1/pruebas/vehiculos/" + vehiculoId + "/interesado")
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null;
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error obteniendo el interesado de la prueba: " + e.getStatusCode());
        }
    }
}


