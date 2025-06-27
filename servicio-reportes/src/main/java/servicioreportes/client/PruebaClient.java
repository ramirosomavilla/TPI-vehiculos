package servicioreportes.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import servicioreportes.dtos.PruebaDTO;

import java.util.List;

@Component
public class PruebaClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${pruebas.service.url}")
    private String configServiceUrl;

    @Value("${pruebas.service.token}")
    private String token;

    public List<PruebaDTO> obtenerPruebasPorVehiculo(Integer vehiculoId) {
        return webClientBuilder.build()
                .get()
                .uri(configServiceUrl + "/vehiculos/" + vehiculoId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(PruebaDTO.class)
                .collectList()
                .block();
    }
}
